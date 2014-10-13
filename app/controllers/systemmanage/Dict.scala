package controllers.systemmanage

import org.joda.time.DateTime
import play.api.mvc._
import play.api.data.Forms._
import play.api.data.Form
import models.DictQueryCondition
import util.ComponentRegister
import util.ComponentRegister.dictService
import com.codahale.jerkson.Json
import play.api.Logger

/**
 * Created by hooxin on 14-10-9.
 */
/**
 * 字典控制器
 */
object Dict extends Controller {
  val log = Logger.logger
  //查询form
  val dictQueryForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "dictcode" -> optional(text),
      "dictname" -> optional(text),
      "superDictcode" -> optional(text),
      "createTime" -> optional(jodaDate)
    )(DictQueryCondition.apply)(DictQueryCondition.unapply)
  )

  /**
   * 获取字典列表
   * @return
   */
  def list = Action {
    implicit request =>
      val pageno = request.getQueryString("page").getOrElse("1").toInt
      val pagesize = request.getQueryString("limit").getOrElse("10").toInt
      val sort = request.getQueryString("sort").getOrElse("sidOrder")
      val dir = request.getQueryString("dir").getOrElse("asc").toLowerCase
      dictQueryForm.bindFromRequest.fold(
        hasErrors =
          form =>
            BadRequest(form.errorsAsJson),
        success =
          dictQuery => {
            try {
              val page = dictService.page(pageno, pagesize, dictQuery, sort, dir)
              Ok(Json.generate(Map("success" -> true,
                "data" -> page.data,
                "total" -> page.total,
                "page" -> page.pageno,
                "limit" -> page.pagesize,
                "result" -> 0
              ))).as(JSON)
            }
            catch {
              case e: Exception =>
                log.error("获取字典列表发生错误")
                log.error(e.getMessage, e.fillInStackTrace())
                Ok(Json.generate(Map("success" -> false,
                  "result" -> -1,
                  "message" -> "获取字典列表发生错误"))).as(JSON)
            }
          }
      )
  }

  val dictForm = Form(
    mapping(
      "dictcode" -> text(1, 20),
      "dictname" -> text(1, 50),
      "superDictcode" -> default(text, "0"),
      "sibOrder" -> default(number, 0),
      "isleaf" -> default(text, "Y"),
      "maintFlag" -> default(number, 0),
      "dictType" -> text,
      "dictSimplePin" -> optional(text),
      "dictAllPin" -> optional(text),
      "dictItemTableName" -> optional(text),
      "dictVersion" -> optional(text),
      "createTime" -> default(optional(jodaDate("yyyy-MM-dd HH:mm:ss")),Some(new DateTime())),
      "id" -> optional(longNumber)
    )(models.Dict.apply)(models.Dict.unapply)
  )

  /**
   * 新增字典
   * @return
   */
  def add(id:Long) = Action {
    implicit request =>
      dictForm.bindFromRequest.fold(
        hasErrors =
          form =>{
            log.debug("="*13+form.get+"="*13)
            BadRequest(form.errorsAsJson)
          },
        success =
          dict => {
            try {
              val inserted = dictService.insert(dict)
              Ok(Json.generate(Map("success" -> true,
                "result" -> 0,
                "data" -> inserted))).as(JSON)
            }
            catch {
              case e: Exception =>
                log.error("新增字典发生错误")
                log.error(e.getMessage, e.fillInStackTrace())
                Ok(Json.generate(Map("success" -> false,
                  "result" -> -1,
                  "message" -> "新增字典发生错误"))).as(JSON)
            }
          }
      )
  }

  /**
   * 修改字典
   * @param id
   */
  def update(id: Long) = Action {
    implicit request =>
      dictForm.bindFromRequest.fold(
        hasErrors =
          form =>
            BadRequest(form.errorsAsJson),
        success =
          dict => {
            try {
              dictService.update(dict)
              Ok(Json.generate(Map("success" -> true,
                "result" -> 0,
                "message" -> "修改字典成功"))).as(JSON)
            }
            catch {
              case e: Exception =>
                log.error("修改字典发生错误")
                log.error(e.getMessage, e.fillInStackTrace())
                Ok(Json.generate(Map("success" -> false,
                  "result" -> -1,
                  "messsage" -> "修改字典发生错误"))).as(JSON)
            }
          }
      )
  }

  /**
   * 删除字典
   * @param id
   * @return
   */
  def remove(id: Long) = Action {
    try {
      dictService.deleteById(id)
      Ok(Json.generate(Map("success" -> true,
        "result" -> 0,
        "message" -> "删除字典成功"))).as(JSON)
    }
    catch {
      case e: Exception =>
        log.error("删除字典发生错误")
        log.error(e.getMessage, e.fillInStackTrace())
        Ok(Json.generate(Map("success" -> false,
          "result" -> -1,
          "message" -> "删除字典发生错误"))).as(JSON)
    }
  }

  /**
   * 获取指定ID的字典
   * @param id
   * @return
   */
  def get(id: Long) = Action {
    try {
      val data = dictService.getById(id)
      Ok(Json.generate(Map("success" -> true,
        "result" -> 0,
        "data" -> data,
        "message" -> "获取指定ID的字典成功"))).as(JSON)
    }
    catch {
      case e: Exception =>
        log.error("获取指定ID的字典发生错误")
        log.error(e.getMessage, e.fillInStackTrace())
        Ok(Json.generate(Map("success" -> false,
          "result" -> -1,
          "message" -> "获取指定ID的字典发生错误"))).as(JSON)
    }
  }

  /**
   * 获取最大的序号(不分级)
   * @return
   */
  def maxDictOrder = Action {
    implicit request =>
      try {
        val result = dictService.maxDictOrder
        Ok(Json.generate(Map("success" -> true,
          "result" -> 0,
          "data" -> result,
          "message" -> "获取最大序号成功"))).as(JSON)
      }
      catch {
        case e: Exception =>
          log.error("获取最大的序号(不分级)")
          log.error(e.getMessage, e.fillInStackTrace())
          Ok(Json.generate(Map("success" -> false,
            "result" -> -1,
            "message" -> "获取最大的序号(不分级)"))).as(JSON)
      }
  }

  /**
   * 验证字典代码可用性
   * @param id 字典代码
   * @return
   */
  def checkDictCodeAvaliable(id: String) = Action {
    implicit request =>
      try {
        val result = dictService.checkDictcodeAvaliable(id)
        Ok(Json.generate(Map("success" -> true,
          "result" -> 0,
          "isAvaliable" -> result,
          "message" -> "字典代码可用性验证访问成功"))).as(JSON)
      }
      catch {
        case e: Exception =>
          log.error("验证字典代码可用性发生错误")
          log.error(e.getMessage, e.fillInStackTrace())
          Ok(Json.generate(Map("success" -> false,
            "result" -> -1,
            "message" -> "验证字典代码可用性发生错误"))).as(JSON)
      }
  }
}
