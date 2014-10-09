package controllers.systemmanage

import com.codahale.jerkson.Json
import models.{DictItem, DictItemQueryCondition}
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import util.ComponentRegister
import util.ComponentRegister.{dictItemService, departmentService}

/**
 * 字典项
 */
object DictItem extends Controller {
  val log = Logger.logger

  val dictItemQueryForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "dictcode" -> optional(text),
      "displayName" -> optional(text),
      "factValue" -> optional(text),
      "appendValue" -> optional(text),
      "superItemId" -> optional(longNumber),
      "displayFlag" -> optional(boolean)
    )
      (DictItemQueryCondition.apply)(DictItemQueryCondition.unapply)
  )

  /**
   * 列表查询
   * @return
   */
  def list = Action {
    implicit request =>
      log.debug("=" * 13 + " 开始字典项列表查询... " + "=" * 13)
      val pageno = request.getQueryString("page").getOrElse("1").toInt
      val limit = request.getQueryString("limit").getOrElse("20").toInt
      val sort = request.getQueryString("sort").getOrElse("id").toLowerCase
      val dir = request.getQueryString("dir").getOrElse("asc").toLowerCase
      log.debug("=" * 13 + s" pageno = $pageno,limit = $limit " + "=" * 13)
      // todo 字典项列表查询内容
      dictItemQueryForm.bindFromRequest.fold(hasErrors = {
        form =>
          BadRequest
      }, success = {
        form =>
          try {
            val pager = dictItemService.page(pageno, limit, form, sort, dir)
            Ok(Json.generate(Map("result" -> 0,
              "success" -> true,
              "message" -> "字典项查询成功",
              "data" -> pager.data,
              "total" -> pager.total,
              "start" -> pager.pageno,
              "limit" -> pager.limit))).as(JSON)
          }
          catch {
            case e: Exception =>
              log.error(e.getMessage, e.fillInStackTrace().toString)
              Ok(Json.generate(Map("result" -> -1,
                "success" -> false,
                "message" -> "字典项列表查询失败",
                "data" -> List(),
                "total" -> 0,
                "start" -> pageno,
                "limit" -> limit
              ))).as(JSON)
          }
          finally {
            log.debug("=" * 13 + " 结束字典项列表查询..." + "=" * 13)
          }
      })
  }

  val dictItemAddForm = Form(
    mapping(
      "dictcode" -> text,
      "displayName" -> text,
      "factValue" -> text,
      "appendValue" -> optional(text),
      "superItemId" -> optional(longNumber),
      "sibOrder" -> default(number, 0),
      "isleaf" -> default(text, "Y"),
      "displayFlag" -> default(number, 0),
      "isValid" -> default(number, 0),
      "itemSimplePin" -> optional(text),
      "itemAllPin" -> optional(text),
      "id" -> optional(longNumber)
    )(models.DictItem.apply)(models.DictItem.unapply)
  )

  /**
   * 添加字典项
   * @return
   */
  def add = Action {
    implicit request =>
      dictItemAddForm.bindFromRequest().fold(hasErrors = {
        form =>
          BadRequest
      }, success = {
        dictItem =>
          try {
            val insertedDictItem = dictItemService.insert(dictItem)
            Ok(Json.generate(Map("result" -> 0,
              "success" -> true,
              "message" -> "添加成功",
              "inserted" -> insertedDictItem))).as(JSON)
          }
          catch {
            case e: Exception =>
              log.error(e.getMessage, e.fillInStackTrace().toString)
              Ok(Json.generate(Map("result" -> -1,
                "success" -> false,
                "message" -> "添加失败"))).as(JSON)
          }
      })
  }

  /**
   * 修改字典项
   * @param id
   * @return
   */
  def update(id: Long) = Action {
    implicit request =>
      dictItemAddForm.bindFromRequest().fold(hasErrors = {
        form =>
          BadRequest
      }, success = {
        dictItem =>
          try {
            dictItemService.update(dictItem)
            Ok(Json.generate(Map("result" -> 0,
              "success" -> true,
              "message" -> "更新成功"))).as(JSON)
          }
          catch {
            case e: Exception =>
              log.error(e.getMessage, e.fillInStackTrace().toString)
              Ok(Json.generate(Map("result" -> -1,
                "success" -> false,
                "message" -> "更新发生错误"))).as(JSON)
          }
      })
  }

  /**
   * 删除
   * @param id
   * @return
   */
  def remove(id: Long) = Action {
    implicit request =>
      try {
        dictItemService.deleteById(id)
        Ok(Json.generate(Map("result" -> 0,
          "success" -> true,
          "message" -> "删除成功"))).as(JSON)
      }
      catch {
        case e: Exception =>
          log.error("字典项删除发生错误")
          log.error(e.getMessage(), e.fillInStackTrace().toString)
          Ok(Json.generate(Map("result" -> -1,
            "success" -> false,
            "message" -> "删除失败")))
      }
  }

  /**
   * 获取单个字典项
   * @param id
   * @return
   */
  def get(id: Long) = Action {
    try {
      val dictItem = dictItemService.getById(id)
      Ok(Json.generate(Map("result" -> 0,
        "success" -> true,
        "message" -> "获取单个字典项成功",
        "data" -> dictItem))).as(JSON)
    }
    catch {
      case e: Exception =>
        log.error("获取单个字典项发生错误")
        log.error(e.getMessage, e.fillInStackTrace().toString)
        Ok(Json.generate(Map("result" -> -1,
          "success" -> false,
          "message" -> "获取单个字典项发生错误"))).as(JSON)
    }
  }

  /**
   * 获取字典异步树节点
   * @return
   */
  def treenode = Action {
    implicit request =>
      dictItemQueryForm.bindFromRequest.fold(
        hasErrors =
          form =>
            BadRequest(form.errorsAsJson),
        success =
          dq => {
            try {
              val list = dictItemService.list(dq).map(di => {
                val leaf = di.isleaf match {
                  case "Y" => true
                  case _ => false
                }
                Map(
                  "dictcode" -> di.dictcode,
                  "displayName" -> di.displayName,
                  "factValue" -> di.factValue,
                  "appendValue" -> di.appendValue,
                  "superItemId" -> di.superItemId,
                  "sibOrder" -> di.sibOrder,
                  "isleaf" -> di.isleaf,
                  "displayFlag" -> di.displayFlag,
                  "isValid" -> di.isValid,
                  "itemSimplePin" -> di.itemSimplePin,
                  "itemAllPin" -> di.itemAllPin,
                  "id" -> di.id,
                  "leaf" -> leaf,
                  "parentId" -> di.superItemId,
                  "text" -> di.displayName
                )
              })
              Ok(Json.generate(Map("success" -> true,
                "data" -> list,
                "result" -> 0))).as(JSON)
            }
            catch {
              case e: Exception =>
                log.error("获取字典项树形结构发生错误")
                log.error(e.getMessage, e.fillInStackTrace())
                Ok(Json.generate(Map("success" -> false,
                  "result" -> -1,
                  "message" -> "获取字典项树形结构发生错误"))).as(JSON)
            }
          }
      )
  }
}
