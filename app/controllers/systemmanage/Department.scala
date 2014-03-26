package controllers.systemmanage

import play.api.mvc._
import util.ComponentRegister._
import play.api.data._
import play.api.data.Forms._

object Department extends Controller {
  val addForm = Form(
    mapping(
      "departcode" -> nonEmptyText,
      "departname" -> nonEmptyText,
      "departlevel" -> number,
      "departfullcode" -> nonEmptyText,
      "parentDepartid" -> longNumber,
      "nodeOrder" -> number,
      "isLeaf" -> nonEmptyText,
      "departsimplepin" -> optional(text),
      "departallpin" -> optional(text),
      "departbrevitycode" -> optional(text),
      "id" -> optional(longNumber)
    )(models.Department.apply)(models.Department.unapply)
  )

  def add = Action {
    implicit request =>
      addForm.bindFromRequest.
      Ok("").as(JSON)
  }

  def remove = TODO

  def update = TODO

  def get(id: Long) = TODO

  def list = TODO
}
