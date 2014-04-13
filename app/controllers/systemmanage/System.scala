package controllers.systemmanage

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import util.ComponentRegister._
import com.codahale.jerkson.Json
import play.api.Logger

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
              "message" -> "添加成功",
              "inserted" -> insertedSystem)))
          }
          catch {
            case e: Exception =>
              log.error(e.toString, e.fillInStackTrace())
              Ok(Json.generate(Map("result" -> -1,
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
          "message" -> "删除成功")))
      }
      catch {
        case e: Exception =>
          log.error(e.toString, e.fillInStackTrace())
          Ok(Json.generate(Map("result" -> -1,
            "message" -> "删除发生错误")))
      }
  }

  def update = TODO

  def get(id: String) = Action {
    implicit request =>
      try {
        val system = systemService.getById(id)
        Ok(Json.generate(Map("result" -> 0,
          "message" -> "",
          "data" -> system)))
      }
      catch {
        case e: Exception =>
          log.error(e.toString, e.fillInStackTrace())
          Ok(Json.generate(Map("result" -> -1,
            "message" -> "获取系统发生错误")))
      }
  }

  def list = TODO


  def userSystemNode = Action {
    Ok()
  }
}
