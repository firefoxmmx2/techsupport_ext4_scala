package controllers.systemmanage

import play.api.mvc._
import util.ComponentRegister._
import play.api.data._
import play.api.data.Forms._
import models.MenuQueryCondition
import play.api.Play.current
import play.api.cache.Cache
import com.codahale.jerkson.Json
import play.api.Logger

/**
 * 菜单管理
 */
object Menu extends Controller {
  val log = Logger.logger

  /**
   * 添加菜单
   * @return
   */
  def add = Action {
    implicit request =>
      menuForm.bindFromRequest.fold(hasErrors =
        form =>
          BadRequest(form.errorsAsJson),
        success =
          menu => {
            try {
              val inserted = menuService.insert(menu)
              Ok(Json.generate(Map("success" -> true,
                "result" -> 0,
                "data" -> inserted,
                "message" -> "添加成功"))).as(JSON)
            }
            catch {
              case e: Exception =>
                log.error(e.getMessage, e.fillInStackTrace())
                Ok(Json.generate(Map("success" -> false,
                  "result" -> -1,
                  "message" -> "菜单添加发生错误"))).as(JSON)
            }
          }
      )
  }

  /**
   * 删除菜单
   * @param id
   * @return
   */
  def remove(id: String) = Action {
    try {
      menuService.deleteById(id)
      Ok(Json.generate(Map("result" -> 0,
        "success" -> true))).as(JSON)
    }
    catch {
      case e: Exception =>
        log.error(e.getMessage, e.fillInStackTrace())
        Ok(Json.generate(Map("result" -> -1,
          "success" -> false,
          "message" -> "删除菜单发生错误"))).as(JSON)
    }
  }

  /**
   * 修改菜单
   * @param id
   * @return
   */
  def update(id: String) = Action {
    implicit request =>
      menuForm.bindFromRequest.fold(
        hasErrors =
          form =>
            BadRequest(form.errorsAsJson),
        success =
          menu => {
            try {
              menuService.update(menu)
              Ok(Json.generate(Map("result" -> 0,
                "success" -> true))).as(JSON)
            }
            catch {
              case e: Exception =>
                log.error(e.getMessage, e.fillInStackTrace())
                Ok(Json.generate(Map("result" -> -1,
                  "success" -> false,
                  "message" -> "修改菜单法身错误"))).as(JSON)
            }
          }
      )
  }

  /**
   * 获取单个菜单
   * @param id
   * @return
   */
  def get(id: String) = Action {
    implicit request =>
      try {
        val menu = menuService.getById(id)
        if (menu == null) {
          Ok(Json.generate(Map("result" -> 1,
            "success" -> false,
            "message" -> s"该字典代码为 $id 的菜单不存在"))).as(JSON)
        }
        else {
          Ok(Json.generate(Map("result" -> 0,
            "success" -> true,
            "data" -> menu,
            "message" -> "获取菜单成功"))).as(JSON)
        }
      }
      catch {
        case e: Exception =>
          log.error(e.getMessage, e.fillInStackTrace())
          Ok(Json.generate(Map("success" -> false,
            "result" -> -1,
            "message" -> "获取菜单发生错误"))).as(JSON)
      }
  }

  /**
   * 获取菜单列表
   * @return
   */
  def list = Action {
    implicit request =>
      val pageno: Int = request.getQueryString("page").getOrElse("1").toInt
      val limit: Int = request.getQueryString("limit").getOrElse("20").toInt
      val sort = request.getQueryString("sort").getOrElse("nodeorder").toLowerCase
      val dir = request.getQueryString("dir").getOrElse("asc").toLowerCase
      menuQueryForm.bindFromRequest.fold(
        hasErrors =
          form =>
            BadRequest(form.errorsAsJson),
        success =
          menuQueryCondition => {
            try {
              val page = menuService.page(pageno, limit, menuQueryCondition, sort, dir)
              Ok(Json.generate(Map("success" -> true,
                "result" -> 0,
                "data" -> page.data,
                "total" -> page.total,
                "page" -> page.pageno,
                "limit" -> page.pagesize))).as(JSON)
            }
            catch {
              case e: Exception =>
                log.error(e.getMessage, e.fillInStackTrace())
                Ok(Json.generate(Map("success" -> false,
                  "result" -> -1,
                  "message" -> "获取菜单列表发生错误"))).as(JSON)
            }
          }
      )
  }

  val menuForm = Form(mapping("id" -> text,
    "menuname" -> text,
    "funcentry" -> text,
    "menulevel" -> number,
    "parentmenucode" -> text,
    "menufullcode" -> text,
    "nodeorder" -> number,
    "isleaf" -> text,
    "systemcode" -> text)(models.Menu.apply)(models.Menu.unapply))

  //查询表单
  val menuQueryForm = Form(
    mapping("id" -> optional(text),
      "menuname" -> optional(text),
      "funcentry" -> optional(text),
      "menulevel" -> optional(number),
      "parentmenucode" -> optional(text),
      "menufullcode" -> optional(text),
      "isleaf" -> optional(text),
      "systemcode" -> optional(text)
    )(MenuQueryCondition.apply)(MenuQueryCondition.unapply))

  def userMenuNode = Action {
    implicit req =>
      menuQueryForm.bindFromRequest().fold(
        hasError => BadRequest,
        query => {
          val userInfo = Cache.get(req.session.get("authCode").getOrElse("")).getOrElse(None)
          if (userInfo == null || userInfo == None)
            Ok(Json.generate(Map("result" -> -2,
              "success" -> false,
              "message" -> "未登录或者登录已过期")))
          else {
            val userInfo_ = userInfo.asInstanceOf[Map[String, Any]]
            val userid = userInfo_.get("userid").asInstanceOf[Option[Long]].get
            Logger.debug("开始查询用户菜单列表")
            val menuList = menuService.getUserMenus(userid, query.parentmenucode.get, query.systemcode)
            Logger.debug("=" * 13 + "menuList = " + menuList + "=" * 13)
            Logger.debug("开始封装为json")
            val root = menuList.map(m => {
              val leaf = if (m.isleaf == "Y") true else false
              Map("id" -> m.id,
                "text" -> m.menuname,
                "funcentry" -> m.funcentry,
                "leaf" -> leaf,
                "parentId" -> m.parentmenucode,
                "systemcode" -> m.systemcode)
            })
            Logger.debug("封装结束")
            Ok(Json.generate(Map(
              "success" -> true,
              "result" -> 0,
              "message" -> "",
              "root" -> root)))
          }

        }
      )
  }

  /**
   * 菜单代码可用性验证
   * @param id 菜单代码
   * @return
   */
  def checkMenucodeAvaliable(id: String) = Action {
    implicit request =>
      try {
        val result = menuService.checkMenucodeAvaliable(id)
        Ok(Json.generate(Map("success" -> true,
          "result" -> 0,
          "isAvaliable" -> result))).as(JSON)
      }
      catch {
        case e: Exception =>
          log.error(e.getMessage, e.fillInStackTrace())
          Ok(Json.generate(Map("success" -> false,
            "result" -> -1,
            "message" -> "菜单代码可用性验证发生错误"))).as(JSON)
      }

  }

  /**
   * 菜单树节点
   * @return
   */
  def treeNodes = Action {
    implicit request =>
      menuQueryForm.bindFromRequest.fold(
        hasErrors =
          form =>
            BadRequest(form.errorsAsJson),
        success =
          menuQueryCondition => {
            try {
              val menus = menuService.list(menuQueryCondition)
              val treeNodes = menus.map {
                m =>
                  val leaf = if (m.isleaf == "Y") true else false
                  Map("id" -> m.id,
                    "text" -> m.menuname,
                    "parentId" -> m.parentmenucode,
                    "leaf" -> leaf,
                    "menuname" -> m.menuname,
                    "menufullcode" -> m.menufullcode,
                    "systemcode" -> m.systemcode,
                    "menulevel" -> m.menulevel)
              }
              Ok(Json.generate(Map("success" -> true,
                "result" -> 0,
                "message" -> "操作成功",
                "data" -> treeNodes)))
            }
            catch {
              case e: Exception =>
                log.error("获取菜单树节点发生错误")
                log.error(e.getMessage, e.fillInStackTrace())
                Ok(Json.generate(Map("success" -> false,
                  "result" -> -1,
                  "message" -> "获取菜单树节点发生错误")))
            }
          }
      )
  }

  /**
   * 获取最大序列号
   * @return
   */
  def maxMenuOrder(parentId: String) = Action {
    try {
      val result = menuService.maxMenuOrder(parentId)
      Ok(Json.generate(Map("result" -> 0,
        "success" -> true,
        "data" -> result))).as(JSON)
    }
    catch {
      case e: Exception =>
        log.error("获取菜单最大序列发生错误")
        log.error(e.getMessage, e.fillInStackTrace())
        Ok(Json.generate(Map("result" -> -1,
          "success" -> false,
          "message" -> "获取菜单最大序列发生错误"))).as(JSON)
    }
  }
}
