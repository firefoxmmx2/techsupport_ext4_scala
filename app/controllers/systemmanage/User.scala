package controllers.systemmanage

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.Logger
import util.ComponentRegister._
import models.UserQueryCondition
import com.codahale.jerkson.Json

/**
 * Created by hooxin on 14-2-10.
 */
object User extends Controller {
  var pageno = 1
  var pagesize = 20;

  val log = Logger.logger
  val userForm = Form(
    mapping(
      "departid" -> longNumber,
      "useraccount" -> text,
      "password" -> text,
      "username" -> text,
      "idnum" -> text,
      "mobilePhone" -> optional(text),
      "userorder" -> number(),
      "isVaild" -> text(),
      "userType" -> optional(text()),
      "jzlbdm" -> optional(text()),
      "jzlbmc" -> optional(text()),
      "email" -> optional(email),
      "id" -> optional(longNumber())
    )(models.User.apply)(models.User.unapply)
  )

  def add = Action {
    implicit request =>
      userForm.bindFromRequest().fold(
        hasErrors => {
          BadRequest
        },
        u => {
          try {
            val inserted = userService.insert(u)
            Ok(Json.generate(Map("result" -> 0,
              "success" -> true,
              "message" -> "添加成功",
              "inserted" -> inserted))).as(JSON)
          } catch {
            case e =>
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
        case e =>
          log.error(e.toString, e.fillInStackTrace())
          Ok(Json.generate(Map("result" -> -1,
            "success" -> false,
            "message" -> "删除错误"))).as(JSON)
      }

  }

  val userQueryForm = Form(
    mapping(
      "id" -> optional(longNumber()),
      "departid" -> optional(longNumber()),
      "useraccount" -> optional(text()),
      "password" -> optional(text),
      "idnum" -> optional(text()),
      "mobilePhone" -> optional(text()),
      "userorder" -> optional(number()),
      "isValid" -> optional(text()),
      "userType" -> optional(text()),
      "email" -> optional(email)
    )(UserQueryCondition.apply)(UserQueryCondition.unapply)
  )

  def list = Action {
    implicit request =>
      val pageno: Int = request.getQueryString("page").getOrElse("1").toInt
      val limit = request.getQueryString("limit").getOrElse("20").toInt

      log.debug("=" * 13 + request.getQueryString("useraccount") + "=" * 13)
      log.debug("=" * 13 + " pageno = " + pageno + " ,limit = " + limit + "=" * 13)
      userQueryForm.bindFromRequest().fold(
        hasErrors => {
          BadRequest
        },
        uq => {
          try {
            log.debug("=" * 13 + " uq = " + uq + "=" * 13)
            val page = userService.page(pageno, limit, uq, "", "")
            Ok(Json.generate(Map("result" -> 0,
              "success" -> true,
              "message" -> "",
              "datas" -> page.data,
              "total" -> page.total,
              "start" -> pageno,
              "limit" -> limit))).as(JSON)
          }
          catch {
            case e =>
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
      case e =>
        log.error(e.toString, e.fillInStackTrace())
        Ok(Json.generate(Map("result" -> -1,
          "success" -> false,
          "message" -> "获取用户发生错误"))).as(JSON)
    }
  }

  def update(id: Long) = Action {
    implicit request =>
      userForm.bindFromRequest().fold(
        hasError => {
          BadRequest
        },
        user => {
          try {
            userService.update(user)
            Ok(Json.generate(Map("result" -> 0,
              "success" -> true,
              "message" -> "修改成功")))
          }
          catch {
            case e =>
              log.error(e.toString, e.fillInStackTrace())
              Ok(Json.generate(Map("result" -> -1,
                "success" -> false,
                "message" -> "修改发生错误")))
          }
        }
      )
  }
}
