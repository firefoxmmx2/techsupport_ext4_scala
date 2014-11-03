package controllers.systemmanage

import com.codahale.jerkson.Json
import play.api.Logger
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import scala.util.{Success, Failure, Try}

/**
 * 角色
 */
object Role extends Controller {
  private val log = Logger.logger
  val roleForm = Form(
    mapping(
      "rolename" -> text,
      "roleDescription" -> optional(text),
      "roleType" -> default(text, "0"),
      "jzlbdm" -> optional(text),
      "jzlbmc" -> optional(text),
      "departid" -> default(longNumber, 0l),
      "id" -> optional(longNumber)
    )(models.Role.apply)(models.Role.unapply)
  )

  /**
   * 角色添加
   * @param id
   * @return
   */
  def add(id: Long) = Action {
    implicit request =>
      roleForm.bindFromRequest.fold(
        form =>
          BadRequest(form.errorsAsJson),
        role => {
          try {
            val inserted = roleService.insert(role)
            Ok(Json.generate(Map(
              "success" -> true,
              "result" -> 0,
              "message" -> "添加角色成功"
            )))
          }
          catch {
            case e: Exception =>
              log.error("角色添加发生错误")
              log.error(e.getMessage)
              log.debug(e.getMessage, e.fillInStackTrace())
              Ok(Json.generate(Map(
                "success" -> false,
                "result" -> -1,
                "message" -> "角色添加发生错误"
              )))
          }
        }
      )
  }

  def remove(id: Long) = Action {
    implicit request =>
      try {
        roleService.deleteById(id)
        Ok(Json.generate(Map(
          "success" -> true,
          "result" -> 0,
          "message" -> "角色删除成功"
        )))
      }
      catch {
        case e: Exception =>
          log.error("角色删除发生错误")
          log.error(e.getMessage)
          log.debug(e.getMessage, e.fillInStackTrace())
          Ok(Json.generate(Map(
            "success" -> false,
            "result" -> -1,
            "message" -> "角色删除发生错误"
          )))
      }
  }

  def update(id: Long) = Action {
    implicit request =>
      roleForm.bindFromRequest.fold(
        form =>
          BadRequest(form.errorsAsJson),
        role => {
          val responseData = Try {
            roleService.update(role)
          } match {
            case Failure(e) =>
              log.error("角色修改发生错误")
              log.error(e.getMessage)
              log.debug(e.getMessage, e.fillInStackTrace())
              Map(
                "success" -> false,
                "result" -> -1,
                "message" -> "角色修改发生错误"
              )
            case Success(p) =>
              Map(
                "success" -> true,
                "result" -> 0,
                "message" -> "角色修改成功")
          }
          Ok(Json.generate(responseData))
        }

      )
  }

  def get(id: Long) = Action {
    implicit request=>
      val responseData=Try{
        val data=roleService.getById(id)
        Map("success"->true,"result"->0,"data"->data)
      }  match {
        case Failure(e) =>

        case None =>
      }
      Ok(Json.generate(responseData))
  }

  def list = TODO

  def checkRolenameAvailable(rolename: String) = Try {}
}
