package controllers.systemmanage

import play.api.mvc._
import util.ComponentRegister._
import play.api.data._
import play.api.data.Forms._
import play.api.Logger
import models.DepartmentQueryCondition

object Department extends Controller {
  val log = Logger.logger
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
      "departbrevitycode" -> optional(text),
      "id" -> optional(longNumber(min = 1))
    )(models.Department.apply)(models.Department.unapply)
  )

  def add = Action {
    implicit request =>
      addForm.bindFromRequest.fold(errors => {
        BadRequest()
      }, department => {
        try {
          val inserted = departmentService.insert(department)
          Ok(Map("result" -> 0,
            "message" -> "添加成功",
            "inserted" -> inserted)).as(JSON)
        } catch {
          case e =>
            log.error(e.toString,e.fillInStackTrace())
            Ok(Map("result" -> -1,
              "message" -> "添加错误")).as(JSON)
        }

      })

  }

  def remove(id: Long) = Action {
    implicit request =>
      try {
        departmentService.deleteById(id)
        Ok(Map("result" -> 0,
          "message" -> "删除成功")).as(JSON)
      } catch {
        case e =>
          log.error(e.toString, e.fillInStackTrace())
          val resultMap = Map("result" -> -1,
            "message" -> "删除错误")
          Ok(resultMap).as(JSON)
      }

  }

  def update = Action {
    implicit request =>
      updateForm.bindFromRequest().fold(errors => {
        BadRequest
      }, department => {
        try {
          departmentService.update(department)
          Ok(Map("result" -> 0,
            "message" -> "修改成功")).as(JSON)
        } catch {
          case e =>
            log.error(e.toString, e.fillInStackTrace())
            Ok(Map("result" -> -1,
              "message" -> "修改失败")).as(JSON)
        }
      })
  }

  def get(id: Long) = Action {
    try {
      val d = departmentService.getById(id)
      Ok(Map("result" -> 0,
        "message" -> "",
        "department" -> d)).as(JSON)
    } catch {
      case e =>
        log.error(e.toString, e.fillInStackTrace())
        Ok(Map("result" -> -1,
          "message" -> "获取错误")).as(JSON)
    }
  }


  val listParamForm = Form(
    mapping(
      "id" -> optional(longNumber()),
      "departcode" -> optional(text),
      "departname" -> optional(text),
      "departlevel" -> optional(number()),
      "departfullcode" -> optional(text()),
      "isLeaf" -> optional(text),
      "departsimplepin" -> optional(text),
      "departallpin" -> optional(text),
      "parentDepartid" -> optional(longNumber)
    )(DepartmentQueryCondition.apply)
      (DepartmentQueryCondition.unapply)
  )

  def list(pageno: Int = 1, limit: Int = 20) = Action {
    implicit req =>
      listParamForm.bindFromRequest().fold(hasErrors =>
        BadRequest(),
        dq => {
          try {
            val page = departmentService.page(pageno, limit, dq, "", "")
            Ok(Map("result" -> 0,
              "message" -> "",
              "datas" -> page.datas,
              "total" -> page.total,
              "start" -> page.pageno,
              "limit" -> page.pagesize)).as(JSON)
          } catch {
            case e =>
              log.error(e.toString, e.fillInStackTrace())
              Ok(Map("result" -> -1,
                "message" -> "机构列表查询错误",
                "datas" -> List(),
                "total" -> 0,
                "start" -> pageno,
                "limit" -> limit
              )).as(JSON)
          }

        }

      )
  }
}
