package controllers.systemmanage

import com.codahale.jerkson.Json
import models.systemmanage
import models.systemmanage.FunctionQueryCondition
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}

/**
 * 功能
 */
object Function extends Controller {
  private val log = Logger.logger
  val functionForm = Form(
    mapping(
      "id" -> optional(text),
      "systemcode" -> text,
      "funcname" -> optional(text),
      "funcdefine" -> optional(text),
      "functype" -> longNumber
    )(systemmanage.Function.apply)(systemmanage.Function.unapply)
  )

  /**
   * 功能添加
   * @param id
   * @return
   */
  def add(id: String) = Action {
    implicit request =>
      functionForm.bindFromRequest.fold(
        form =>
          BadRequest(form.errorsAsJson),
        function => {
          try {
            val inserted = functionService.insert(function)
            Ok(Json.generate(Map(
              "success" -> true,
              "result" -> 0,
              "message" -> "功能添加成功",
              "data" -> inserted
            ))).as(JSON)
          }
          catch {
            case e: Exception =>
              log.error("功能添加发生错误")
              log.error(e.getMessage)
              log.debug(e.getMessage, e.fillInStackTrace())
              Ok(Json.generate(Map("success" -> false,
                "result" -> -1,
                "message" -> "功能添加发生错误"))).as(JSON)
          }
        }
      )
  }

  /**
   * 功能修改
   * @param id
   * @return
   */
  def update(id: String) = Action {
    implicit request =>
      functionForm.bindFromRequest.fold(
        form =>
          BadRequest(form.errorsAsJson),
        function => {
          try {
            functionService.update(function)
            Ok(Json.generate(Map("success" -> true,
              "result" -> 0,
              "message" -> "功能修改成功"))).as(JSON)
          }
          catch {
            case e: Exception =>
              log.error("功能修改发生错误")
              log.error(e.getMessage)
              log.debug(e.getMessage, e.fillInStackTrace())
              Ok(Json.generate(Map("success" -> false,
                "result" -> -1,
                "message" -> "功能修改发生错误"))).as(JSON)
          }
        }
      )
  }

  /**
   * 功能删除
   * @param id
   * @return
   */
  def remove(id: String) = Action {
    try {
      functionService.deleteById(id)
      Ok(Json.generate(Map(
        "success" -> true,
        "message" -> "功能删除成功",
        "result" -> 0
      ))).as(JSON)
    }
    catch {
      case e: Exception =>
        log.error("功能删除发生错误")
        log.error(e.getMessage)
        log.debug(e.getMessage, e.fillInStackTrace())
        Ok(Json.generate(Map(
          "success" -> false,
          "result" -> -1,
          "message" -> "功能删除发生错误"
        ))).as(JSON)
    }
  }

  /**
   * 通过ID获取单个功能详情
   * @param id
   * @return
   */
  def getById(id: String) = Action {
    try {
      val function = functionService.getById(id)
      Ok(Json.generate(Map("success" -> true,
        "result" -> 0,
        "message" -> "通过ID获取单个功能详情成功",
        "data" -> function))).as(JSON)
    }
    catch {
      case e: Exception =>
        log.error("通过ID获取单个功能详情发生错误")
        log.error(e.getMessage)
        log.debug(e.getMessage, e.fillInStackTrace())
        Ok(Json.generate(Map("success" -> false,
          "result" -> -1,
          "message" -> "通过ID获取单个功能详情发生错误"))).as(JSON)
    }
  }

  val functionQueryForm = Form(
    mapping(
      "id" -> optional(text),
      "systemcode" -> optional(text),
      "funcname" -> optional(text),
      "funcdefine" -> optional(text),
      "functype" -> optional(longNumber)
    )(FunctionQueryCondition.apply)(FunctionQueryCondition.unapply)
  )

  /**
   * 获取功能列表
   * @return
   */
  def list = Action {
    implicit request =>
      val pageno = request.getQueryString("page").getOrElse("1").toInt
      val limit = request.getQueryString("limit").getOrElse("15").toInt
      val sort = request.getQueryString("sort").getOrElse("id")
      val dir = request.getQueryString("dir").getOrElse("asc").toLowerCase

      functionQueryForm.bindFromRequest.fold(
        form =>
          BadRequest(form.errorsAsJson),
        functionQuery => {
          try {
            val page = functionService.page(pageno, limit, functionQuery, sort, dir)
            Ok(Json.generate(Map("success" -> true,
              "result" -> 0,
              "data" -> page.data,
              "total" -> page.total,
              "page" -> pageno,
              "limit" -> limit,
              "message" -> "获取功能列表成功"))).as(JSON)
          }
          catch {
            case e: Exception =>
              log.error("获取功能列表发生错误")
              log.error(e.getMessage)
              log.debug(e.getMessage, e.fillInStackTrace())
              Ok(Json.generate(Map("success" -> false,
                "result" -> -1,
                "message" -> "获取功能列表发生错误"))).as(JSON)
          }
        }
      )
  }

  /**
   * 验证功能代码是否可用
   * @param id
   * @return
   */
  def checkIDAvailable(id: String) = Action {
    try {
      val result = functionService.checkFunccodeAvailable(id)
      Ok(Json.generate(Map("success" -> true,
        "result" -> 0,
        "isAvailable" -> result,
        "message" -> "验证功能代码是否可用成功"))).as(JSON)
    }
    catch {
      case e: Exception =>
        log.error("验证功能代码是否可用发生错误")
        log.error(e.getMessage)
        log.debug(e.getMessage, e.fillInStackTrace())
        Ok(Json.generate(Map("success" -> false,
          "result" -> -1,
          "message" -> "验证功能代码是否可用发生错误"))).as(JSON)
    }
  }
}
