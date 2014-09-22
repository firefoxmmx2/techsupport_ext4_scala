package controllers.systemmanage

import play.api.mvc._
import util.ComponentRegister._
import play.api.data._
import play.api.data.Forms._
import play.api.Logger
import models.DepartmentQueryCondition
import com.codahale.jerkson.Json

object Department extends Controller {
  val log = Logger.logger
  val addForm = Form(
    mapping(
      "departcode" -> nonEmptyText,
      "departname" -> nonEmptyText,
      "departlevel" -> number,
      "departfullcode" -> nonEmptyText,
      "parentDepartid" -> optional(longNumber),
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
      "parentDepartid" -> optional(longNumber),
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
      addForm.bindFromRequest.fold(hasErrors= form => {
        BadRequest(form.errorsAsJson)
      }, department => {
        try {
          val inserted = departmentService.insert(department)
          Ok(Json.generate(Map("result" -> 0,
            "message" -> "添加成功",
            "success" -> true,
            "inserted" -> inserted))).as(JSON)
        } catch {
          case e =>
            log.error(e.toString, e.fillInStackTrace())
            Ok(Json.generate(Map("result" -> -1,
              "success" -> false,
              "message" -> "添加错误"))).as(JSON)
        }

      })

  }

  def remove(id: Long) = Action {
    implicit request =>
      try {
        departmentService.deleteById(id)
        Ok(Json.generate(Map("result" -> 0,
          "success" -> true,
          "message" -> "删除成功"))).as(JSON)
      } catch {
        case e =>
          log.error(e.toString, e.fillInStackTrace())
          val resultMap = Map("result" -> -1,
            "success" -> false,
            "message" -> "删除错误")
          Ok(Json.generate(resultMap)).as(JSON)
      }

  }

  def update(id: Long) = Action {
    implicit request =>
      updateForm.bindFromRequest().fold(hasErrors=form => {
        BadRequest(form.errorsAsJson)
      }, department => {
        try {
          departmentService.update(department)
          Ok(Json.generate(Map("result" -> 0,
            "success" -> true,
            "message" -> "修改成功"))).as(JSON)
        } catch {
          case e:Exception =>
            log.error(e.toString, e.fillInStackTrace())
            Ok(Json.generate(Map("result" -> -1,
              "success" -> false,
              "message" -> "修改失败"))).as(JSON)
        }
      })
  }

  /**
   * 获取单一机构
   * @param id
   * @return
   */
  def get(id: Long) = Action {
    try {
      log.debug("="*13+"获取单一机构..."+"="*13)
      log.debug("="*13+"id = "+id+"="*13)
      val d = departmentService.getById(id)
      Ok(Json.generate(Map("result" -> 0,
        "success" -> true,
        "message" -> "",
        "data" -> d))).as(JSON)
    } catch {
      case e:Exception =>
        log.error(e.toString, e.fillInStackTrace())
        Ok(Json.generate(Map("result" -> -1,
          "success" -> false,
          "message" -> "获取单一机构"))).as(JSON)
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

  def list = Action {
    implicit req =>
      var pageno: Int = req.getQueryString("page").getOrElse("1").toInt
      var limit: Int = req.getQueryString("limit").getOrElse("20").toInt
      var sort = req.getQueryString("sort").getOrElse("nodeOrder")
      var dir = req.getQueryString("dir").getOrElse("asc")
      listParamForm.bindFromRequest().fold(hasErrors=form =>
        BadRequest(form.errorsAsJson),
        dq => {
          try {
            val page = departmentService.page(pageno, limit, dq, sort, dir)
//            log.debug("="*13+"page.data[0].parentDepartment = "+page.data(0).parentDepartment+"="*13)
            Ok(Json.generate(Map("result" -> 0,
              "success" -> true,
              "message" -> "",
              "data" -> page.data,
              "total" -> page.total,
              "start" -> page.pageno,
              "limit" -> page.pagesize))).as(JSON)
          } catch {
            case e:Exception =>
              log.error(e.toString, e.fillInStackTrace())
              Ok(Json.generate(Map("result" -> -1,
                "success" -> false,
                "message" -> "机构列表查询错误",
                "data" -> List(),
                "total" -> 0,
                "start" -> pageno,
                "limit" -> limit
              ))).as(JSON)
          }

        }

      )
  }

  /**
   * 机构树节点
   * @return
   */
  def departmentTreeNode = Action {
    implicit request =>
      listParamForm.bindFromRequest().fold(hasErrors =>
        BadRequest,
        dq => {
          try {
            val departmentList = departmentService.list(dq)
            val nodeList = departmentList.map {
              x =>
                val leaf = if (x.isLeaf == "Y") true else false
                Map("id" -> x.id,
                  "text" -> x.departname,
                  "parentId" -> x.parentDepartid,
                  "leaf" -> leaf,
                  "departcode" -> x.departcode
                )
            }
            Ok(Json.generate(Map("result" -> 0,
              "success" -> true,
              "message" -> "",
              "data" -> nodeList))).as(JSON)
          } catch {
            case e:Exception =>
              log.error(e.toString, e.fillInStackTrace())
              Ok(Json.generate(Map("result" -> -1,
                "success" -> false,
                "message" -> "机构树查询错误",
                "data" -> ""
              ))).as(JSON)
          }
        })
  }
}