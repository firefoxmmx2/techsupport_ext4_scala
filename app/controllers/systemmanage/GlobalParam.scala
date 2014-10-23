package controllers.systemmanage

import com.codahale.jerkson.Json
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import util.ComponentRegister

/**
 * 全局参数
 */
object GlobalParam extends Controller with ComponentRegister {
  private val log = Logger.logger
  val globalParamForm = Form(
    mapping(
      "id" -> text,
      "globalparname" -> text,
      "globalparvalue" -> text
    )(models.GlobalParam.apply)(models.GlobalParam.unapply)
  )

  val globalParamQueryForm = Form(
    mapping(
      "id" -> optional(text),
      "globalparname" -> optional(text),
      "globalparvalue" -> optional(text)
    )(models.GlobalParamQueryCondition.apply)(models.GlobalParamQueryCondition.unapply)
  )

  /**
   * 全局参数列表查询
   * @return
   */
  def list = Action {
    implicit request =>
      val pageno = request.getQueryString("page").getOrElse("1").toInt
      val pagesize = request.getQueryString("limit").getOrElse("20").toInt
      val sort = request.getQueryString("sort").getOrElse("id")
      val dir = request.getQueryString("dir").getOrElse("asc").toLowerCase
      globalParamQueryForm.bindFromRequest.fold(
        hasErrors =
          form =>
            BadRequest(form.errorsAsJson),
        success =
          queryCondition => {
            try {
              val page = globalParamService.page(pageno, pagesize, queryCondition, sort, dir)

              Ok(Json.generate(Map(
                "success" -> true,
                "result" -> 0,
                "data" -> page.data,
                "total" -> page.total,
                "page" -> page.pageno,
                "limit" -> page.limit
              ))).as(JSON)
            }
            catch {
              case e: Exception =>
                log.error("全局参数列表查询发生错误")
                log.error(e.getMessage, e.fillInStackTrace())
                Ok(Json.generate(Map(
                  "success" -> false,
                  "result" -> -1,
                  "message" -> "全局参数列表查询发生错误"
                ))).as(JSON)
            }
          }
      )
  }

  /**
   * 新增全局参数
   * @return
   */
  def add = Action {
    implicit request =>
      globalParamForm.bindFromRequest.fold(
        hasErrors =
          form =>
            BadRequest(form.errorsAsJson),
        success =
          globalParam => {
            try {
              val inserted = globalParamService.insert(globalParam)
              Ok(Json.generate(Map(
                "success" -> true,
                "result" -> 0,
                "data" -> inserted
              ))).as(JSON)
            }
            catch {
              case e: Exception =>
                log.error("新增全局参数发生错误")
                log.error(e.getMessage, e.fillInStackTrace())
                Ok(Json.generate(Map(
                  "success" -> false,
                  "result" -> -1,
                  "message" -> "新增全局参数发生错误"
                ))).as(JSON)
            }
          }
      )
  }

  /**
   * 全局参数修改
   * @return
   */
  def update(id: String) = Action {
    implicit request =>
      globalParamForm.bindFromRequest.fold(
        hasErrors =
          form =>
            BadRequest(form.errorsAsJson),
        success =
          globalParam => {
            try {
              globalParamService.update(globalParam)
              Ok(Json.generate(Map(
                "success" -> true,
                "result" -> 0
              ))).as(JSON)
            }
            catch {
              case e: Exception =>
                log.error("全局参数修改发生错误")
                log.error(e.getMessage, e.fillInStackTrace())
                Ok(Json.generate(Map(
                  "success" -> false,
                  "result" -> -1,
                  "message" -> "全局参数修改发生错误"
                ))).as(JSON)
            }
          }
      )
  }

  /**
   * 全局参数删除
   * @param id
   * @return
   */
  def remove(id: String) = Action {
    implicit request =>
      try {
        globalParamService.deleteById(id)
        Ok(Json.generate(Map("success" -> true,
          "result" -> 0))).as(JSON)
      }
      catch {
        case e: Exception =>
          log.error("全局参数删除发生错误")
          log.error(e.getMessage, e.fillInStackTrace())
          Ok(Json.generate(Map(
            "success" -> false,
            "result" -> -1,
            "message" -> "全局参数删除发生错误"
          ))).as(JSON)
      }
  }

  /**
   * 全局参数获取单个
   * @param id
   * @return
   */
  def get(id: String) = Action {
    implicit request =>
      try {
        val globalParam = globalParamService.getById(id)
        Ok(Json.generate(Map("success" -> true,
          "result" -> 0,
          "data" -> globalParam))).as(JSON)
      }
      catch {
        case e: Exception =>
          log.error("全局参数获取单个发生错误")
          log.error(e.getMessage, e.fillInStackTrace())
          Ok(Json.generate(Map(
            "success" -> false,
            "result" -> -1,
            "message" -> "全局参数获取单个发生错误"
          ))).as(JSON)
      }
  }

  /**
   * 全局参数代码可用性验证
   * @param id
   * @return
   */
  def checkGlobalParamAvaliable(id: String) = Action {
    implicit request =>
      try {
        val result = globalParamService.checkGlobalParamAvaliable(id)
        Ok(Json.generate(Map(
          "success" -> true,
          "result" -> 0,
          "isAvaliable" -> result
        ))).as(JSON)
      }
      catch {
        case e: Exception =>
          log.error("全局参数代码可用性验证发生错误")
          log.error(e.getMessage, e.fillInStackTrace())
          Ok(Json.generate(Map(
            "success" -> false,
            "result" -> -1,
            "message" -> "全局参数代码可用性验证发生错误"
          ))).as(JSON)
      }
  }
}
