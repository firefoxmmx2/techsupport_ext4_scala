package controllers.systemmanage

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import util.ComponentRegister._
import com.codahale.jerkson.Json
import play.api.Logger
import models.SystemQueryCondition
import play.api.cache.Cache
import play.api.Play.current

/**
 * Created by hooxin on 14-2-10.
 */
object System extends Controller {
  val log = Logger.logger
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
    )(models.System.apply)(models.System.unapply)
  )

  def add = Action {
    implicit request =>
      systemForm.bindFromRequest().fold(
        hasError => BadRequest,
        system => {
          try {
            val insertedSystem = systemService.insert(system)
            Ok(Json.generate(Map("result" -> 0,
              "success" -> true,
              "message" -> "添加成功",
              "inserted" -> insertedSystem)))
          }
          catch {
            case e: Exception =>
              log.error(e.toString, e.fillInStackTrace())
              Ok(Json.generate(Map("result" -> -1,
                "success" -> false,
                "message" -> "添加错误")))
          }

        }
      )
  }

  def remove(id: String) = Action {
    implicit request =>
      try {
        systemService.deleteById(id)
        Ok(Json.generate(Map("result" -> 0,
          "success" -> true,
          "message" -> "删除成功")))
      }
      catch {
        case e: Exception =>
          log.error(e.toString, e.fillInStackTrace())
          Ok(Json.generate(Map("result" -> -1,
            "success" -> false,
            "message" -> "删除发生错误")))
      }
  }

  def update(id: String) = TODO

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

  def list = TODO

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
}
