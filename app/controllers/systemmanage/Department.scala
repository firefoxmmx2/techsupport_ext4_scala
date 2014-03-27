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

  val updateForm = Form(
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
      "departbrevitycode" -> optional(text(minLength = 1)),
      "id" -> optional(longNumber(min = 1))
    )(models.Department.apply)(models.Department.unapply)
  )

  def add = Action {
    implicit request =>
      addForm.bindFromRequest.fold(error => {
        BadRequest
      },
        data => {
          val inserted = departmentService.insert(data)
          Ok(Map("result" -> 0, "message" -> "添加成功", "inserted" -> inserted)).as(JSON)
        })

  }

  def remove(id: Long) = Action {
    implicit request =>
      departmentService.deleteById(id)
      Ok(Map("result" -> 0, "message" -> "删除成功")).as(JSON)
  }

  def update = Action {
    implicit request =>
      updateForm.bindFromRequest.fold(error => {
        BadRequest
      },
        data => {
          departmentService.update(data)
          Ok(Map("result" -> 0, "message" -> "修改成功")).as(JSON)
        })
  }

  def get(id: Long) = Action {
    implicit request =>
      val department = departmentService.getById(id)
      Ok(Map("result" -> 0,
        "message" -> "",
        "data" -> department)).as(JSON)
  }

  def list(pageno: Int = 1) = Action {
    Ok(Map("result" -> 0,
      "message" -> "",
      "list" -> List())).as(JSON)
  }
}
