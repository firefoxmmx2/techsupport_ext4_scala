package controllers.systemmanage

import com.codahale.jerkson.Json
import models.systemmanage
import models.systemmanage.UserQueryCondition
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import util.ComponentRegister

/**
 * 用户管理
 */
object User extends Controller with ComponentRegister {
  var pageno = 1
  var pagesize = 20;

  private val log = Logger.logger
  val userForm = Form(
    mapping(
      "departid" -> longNumber,
      "useraccount" -> text,
      "username" -> text,
      "password" -> text,
      "idnum" -> optional(text),
      "mobilePhone" -> optional(text),
      "userorder" -> number,
      "isValid" -> text,
      "userType" -> optional(text),
      "jzlbdm" -> optional(text),
      "jzlbmc" -> optional(text),
      "email" -> optional(email),
      "id" -> optional(longNumber)
    )(systemmanage.User.apply)(systemmanage.User.unapply)
  )

  def add = Action {
    implicit request =>
      log.debug("=" * 13 + "request.body = " + request.body + "=" * 13)
      userForm.bindFromRequest().fold(
        hasErrors = form => {
          log.debug("=" * 13 + "nihao" + "=" * 13)
          BadRequest(form.errorsAsJson)
        },
        u => {
          try {
            val inserted = userService.insert(u)
            Ok(Json.generate(Map("result" -> 0,
              "success" -> true,
              "message" -> "添加成功",
              "inserted" -> inserted))).as(JSON)
          } catch {
            case e:Exception =>
              log.error(e.toString, e.fillInStackTrace())
              Ok(Json.generate(Map("result" -> -1,
                "success" -> false,
                "message" -> "添加错误"))).as(JSON)
          }

        }
      )
  }

  def remove(id: Long) = Action {
    implicit request =>
      try {
        userService.deleteById(id)
        Ok(Json.generate(Map("result" -> 0,
          "success" -> true,
          "message" -> "删除成功"))).as(JSON)
      } catch {
        case e:Exception =>
          log.error(e.toString, e.fillInStackTrace())
          Ok(Json.generate(Map("result" -> -1,
            "success" -> false,
            "message" -> "删除错误"))).as(JSON)
      }

  }

  val userQueryForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "departid" -> optional(longNumber),
      "useraccount" -> optional(text),
      "password" -> optional(text),
      "idnum" -> optional(text),
      "mobilePhone" -> optional(text),
      "userorder" -> optional(number),
      "isValid" -> optional(text),
      "userType" -> optional(text),
      "email" -> optional(email)
    )(UserQueryCondition.apply)(UserQueryCondition.unapply)
  )

  def list = Action {
    implicit request =>
      val pageno: Int = request.getQueryString("page").getOrElse("1").toInt
      val limit = request.getQueryString("limit").getOrElse("20").toInt
      var sort = request.getQueryString("sort").getOrElse("id").toLowerCase
      var dir = request.getQueryString("dir").getOrElse("asc").toLowerCase
      log.debug("=" * 13 + request.getQueryString("useraccount") + "=" * 13)
      log.debug("=" * 13 + " pageno = " + pageno + " ,limit = " + limit + "=" * 13)
      userQueryForm.bindFromRequest().fold(
        hasErrors => {
          BadRequest
        },
        uq => {
          try {
            log.debug("=" * 13 + " uq = " + uq + "=" * 13)
            val page = userService.page(pageno, limit, uq, sort, dir)
            Ok(Json.generate(Map("result" -> 0,
              "success" -> true,
              "message" -> "",
              "datas" -> page.data,
              "total" -> page.total,
              "start" -> pageno,
              "limit" -> limit))).as(JSON)
          }
          catch {
            case e:Exception =>
              log.error(e.toString, e.fillInStackTrace())
              Ok(Json.generate(Map("result" -> -1,
                "success" -> false,
                "message" -> "查询列表错误",
                "total" -> 0,
                "datas" -> List(),
                "start" -> pageno,
                "limit" -> limit))).as(JSON)
          }
        }
      )
  }

  def get(id: Long) = Action {
    try {
      val user = userService.getById(id)
      Ok(Json.generate(Map("result" -> 0,
        "success" -> true,
        "message" -> "",
        "user" -> user))).as(JSON)
    }
    catch {
      case e:Exception =>
        log.error(e.toString, e.fillInStackTrace())
        Ok(Json.generate(Map("result" -> -1,
          "success" -> false,
          "message" -> "获取用户发生错误"))).as(JSON)
    }
  }

  def update(id: Long) = Action {
    implicit request =>
      log.debug("=" * 13 + "request.body" + request.body + "=" * 13)
      userForm.bindFromRequest().fold(
        form => {
          BadRequest(form.errorsAsJson)
        },
        user => {
          try {
            userService.update(user)
            Ok(Json.generate(Map("result" -> 0,
              "success" -> true,
              "message" -> "修改成功")))
          }
          catch {
            case e:Exception =>
              log.error(e.toString, e.fillInStackTrace())
              Ok(Json.generate(Map("result" -> -1,
                "success" -> false,
                "message" -> "修改发生错误")))
          }
        }
      )
  }

  def maxUserOrder(departid: Long) = Action {
    implicit request =>
      log.debug("=" * 13 + "开始获取做大用户序列" + "=" * 13)
      log.debug("=" * 13 + s"departid = $departid" + "=" * 13)
      try {
        if (departid == 0)
          throw new RuntimeException("机构id为空")
        //获取最大的用户序号
        val maxUserOrder = userService.getMaxUserOrder(departid)
        Ok(Json.generate(Map("result" -> 0,
          "success" -> true,
          "message" -> "获取最大用户序号成功",
          "data" -> maxUserOrder))).as(JSON)
      }
      catch {
        case e: Exception =>
          log.error("获取最大用户序号发生错误")
          log.error(e.getMessage, e.fillInStackTrace().toString)
          Ok(Json.generate(Map("result" -> -1,
            "success" -> false,
            "message" -> "获取最大用户序号发生错误"))).as(JSON)
      }
  }

  val checkUserAccountAvailableForm = Form(
    single(
      "useraccount" -> text
    )
  )

  /**
   * 检查用户帐号可用
   * @return
   */
  def checkUseraccountAvailable = Action {
    implicit request =>
      log.debug("=" * 13 + "账户重复验证" + "=" * 13)
      checkUserAccountAvailableForm.bindFromRequest().fold(hasErrors =
        form => {
          BadRequest(form.errorsAsJson)
        },
        success =
          useraccount =>
            try {
              // 验证重复账户
              val isAvailable = userService.checkUseraccountAvailable(useraccount)
              if (isAvailable)
                Ok(Json.generate(Map("result" -> 0,
                  "success" -> true,
                  "message" -> "该账户可用"))).as(JSON)
              else
                Ok(Json.generate(Map("result" -> 1,
                  "success" -> true,
                  "message" -> "该账户不可用"))).as(JSON)
            }
            catch {
              case e: Exception =>
                log.error("检查用户帐号是否可用发生错误")
                log.error(e.getMessage, e.fillInStackTrace().toString)
                Ok(Json.generate(Map("result" -> -1,
                  "success" -> false,
                  "message" -> "检查用户帐号是否可用发生错误"))).as(JSON)
            }
      )

  }
}
