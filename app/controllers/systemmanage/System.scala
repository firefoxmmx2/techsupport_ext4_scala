package controllers.systemmanage

import com.codahale.jerkson.Json
import models.systemmanage
import models.systemmanage.SystemQueryCondition
import play.api.Logger
import play.api.Play.current
import play.api.cache.Cache
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import util.ComponentRegister

/**
 * 系统管理
 */
object System extends Controller with ComponentRegister {
  private val log = Logger.logger
  val systemForm = Form(
    mapping(
      "id" -> text,
      "systemname" -> text,
      "systemdefine" -> text,
      "picturepath" -> text,
      "parentsystemcode" -> text,
      "nodeorder" -> number,
      "isleaf" -> text,
      "fullcode" -> text
    )(systemmanage.System.apply)(systemmanage.System.unapply)
  )

  /**
   * 系统新增
   * @return
   */
  def add = Action {
    implicit request =>
      systemForm.bindFromRequest().fold(
        hasError => BadRequest,
        system => {
          try {
            val insertedSystem = systemService.insert(system)
            Ok(Json.generate(Map("result" -> 0,
              "success" -> true,
              "message" -> "添加完毕",
              "inserted" -> insertedSystem)))
          }
          catch {
            case e: Exception =>
              log.error("系统新增发生错误")
              log.error(e.toString, e.fillInStackTrace())
              Ok(Json.generate(Map("result" -> -1,
                "success" -> false,
                "message" -> "系统新增发生错误")))
          }

        }
      )
  }

  /**
   * 系统删除
   * @param id
   * @return
   */
  def remove(id: String) = Action {
    implicit request =>
      try {
        systemService.deleteById(id)
        Ok(Json.generate(Map("result" -> 0,
          "success" -> true,
          "message" -> "删除完毕")))
      }
      catch {
        case e: Exception =>
          log.error("系统删除发生错误")
          log.error(e.toString, e.fillInStackTrace())
          Ok(Json.generate(Map("result" -> -1,
            "success" -> false,
            "message" -> "系统删除发生错误")))
      }
  }

  /**
   * 修改系统
   * @param id
   * @return
   */
  def update(id: String) = Action {
    implicit request =>
      systemForm.bindFromRequest.fold(
        hasErrors =
          form =>
            BadRequest(form.errorsAsJson),
        success =
          system => {
            try {
              systemService.update(system)
              Ok(Json.generate(Map("success" -> true,
                "result" -> 0,
                "message" -> "更新完毕"))).as(JSON)
            }
            catch {
              case e: Exception =>
                log.error("系统修改发生错误")
                log.error(e.getMessage, e.fillInStackTrace())
                Ok(Json.generate(Map("success" -> false,
                  "result" -> -1,
                  "message" -> "系统修改发生错误"))).as(JSON)
            }
          }
      )
  }

  def get(id: String) = Action {
    implicit request =>
      try {
        val system = systemService.getById(id)
        Ok(Json.generate(Map("result" -> 0,
          "message" -> "",
          "success" -> true,
          "data" -> system)))
      }
      catch {
        case e: Exception =>
          log.error(e.toString, e.fillInStackTrace())
          Ok(Json.generate(Map("result" -> -1,
            "message" -> "获取系统发生错误",
            "success" -> false)))
      }
  }

  /**
   * 获取系统列表
   * @return
   */
  def list = Action {
    implicit request =>
      val pageno = request.getQueryString("page").getOrElse("1").toInt
      val pagesize = request.getQueryString("limit").getOrElse("20").toInt
      val sort = request.getQueryString("sort").getOrElse("nodeorder")
      val dir = request.getQueryString("dir").getOrElse("asc").toLowerCase

      systemQueryForm.bindFromRequest.fold(
        hasErrors =
          form =>
            BadRequest(form.errorsAsJson),
        success =
          systemQuery => {
            try {
              val page = systemService.page(pageno, pagesize, systemQuery, sort, dir)
              Ok(Json.generate(Map("success" -> true,
                "result" -> 0,
                "data" -> page.data,
                "message" -> ""))).as(JSON)
            }
            catch {
              case e: Exception =>
                log.error("获取系统列表发生错误")
                log.error(e.getMessage, e.fillInStackTrace())
                Ok(Json.generate(Map("success" -> false,
                  "result" -> -1,
                  "message" -> "获取系统列表发生错误"))).as(JSON)
            }
          }
      )
  }

  val systemQueryForm = Form(
    mapping(
      "id" -> optional(text),
      "systemname" -> optional(text),
      "systemdefine" -> optional(text),
      "parentsystemcode" -> optional(text),
      "isleaf" -> optional(text),
      "fullcode" -> optional(text)
    )(SystemQueryCondition.apply)(SystemQueryCondition.unapply)
  )

  def userSystemNode = Action {
    implicit request =>
      systemQueryForm.bindFromRequest().fold(
        hasErrors => {
          BadRequest
        },
        query => {
          val userInfo = Cache.get(request.session.get("authCode").getOrElse("")).getOrElse(None)
          if (userInfo == null || userInfo == None)
            Ok(Json.generate(Map("result" -> -2,
              "success" -> false,
              "message" -> "未登录或者登录已过期")))
          else {
            val userInfo_ = userInfo.asInstanceOf[Map[String, Any]]
            println("=" * 13 + "[" + userInfo_.get("userid") + "]" + "=" * 13)
            val userid = userInfo_.get("userid").asInstanceOf[Option[Long]] match {
              case Some(x) => x
            }
            val systems = systemService.getUserSystems(userid)
            Ok(Json.generate(
              Map("result" -> 0,
                "success" -> true,
                "message" -> "",
                "systems" -> systems)
            ))
          }
        }
      )
  }

  /**
   * 最大系统序列
   * @return
   */
  def maxSystemOrder = Action {
    implicit request =>
      try {
        val result = systemService.maxSystemOrder
        Ok(Json.generate(Map("success" -> true,
          "result" -> 0,
          "data" -> result))).as(JSON)
      }
      catch {
        case e: Exception =>
          log.error("获取最大系统序列发生错误")
          log.error(e.getMessage, e.fillInStackTrace())
          Ok(Json.generate(Map("success" -> false,
            "result" -> -1,
            "message" -> "获取最大系统序列发生错误"))).as(JSON)
      }
  }

  /**
   * 系统代码可用性验证
   * @param systemcode
   * @return
   */
  def checkSystemcodeAvaliable(systemcode: String) = Action {
    implicit request =>
      try {
        val result = systemService.checkSystemcodeAvaliable(systemcode)
        Ok(Json.generate(Map("success" -> true,
          "result" -> 0,
          "isAvaliable" -> result))).as(JSON)
      }
      catch {
        case e: Exception =>
          log.error("系统代码可用性验证发生错误")
          log.error(e.getMessage, e.fillInStackTrace())
          Ok(Json.generate(Map("success" -> false,
            "result" -> -1,
            "message" -> "系统代码可用性验证发生错误"))).as(JSON)
      }

  }
}
