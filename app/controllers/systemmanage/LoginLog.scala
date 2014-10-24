package controllers.systemmanage

import play.api.Logger
import play.api.mvc._
import util.ComponentRegister
import play.api.data.Form
import play.api.data.Forms._
import com.codahale.jerkson.Json

/**
 * 登录日志
 */
object LoginLog extends Controller with ComponentRegister {
  private val log = Logger.logger

  val loginLogForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "loginuserid" -> longNumber,
      "useraccount" -> text,
      "username" -> text,
      "loginunitcode" -> text,
      "loginunitname" -> text,
      "loginip" -> text,
      "loginmac" -> optional(text),
      "logintiime" -> jodaDate("yyyy-MM-dd HH:mm:ss"),
      "quittime" -> optional(jodaDate("yyyy-MM-dd HH:mm:ss"))
    )(models.LoginLog.apply)(models.LoginLog.unapply)
  )

  val loginLogQueryForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "loginuserid" -> optional(longNumber),
      "useraccount" -> optional(text),
      "username" -> optional(text),
      "loginunitcode" -> optional(text),
      "loginunitname" -> optional(text),
      "loginip" -> optional(text),
      "loginmac" -> optional(text),
      "logintiimeStart" -> optional(jodaDate("yyyy-MM-dd HH:mm:ss")),
      "logintiimeEnd" -> optional(jodaDate("yyyy-MM-dd HH:mm:ss")),
      "quittimeStart" -> optional(jodaDate("yyyy-MM-dd HH:mm:ss")),
      "quittimeEnd" -> optional(jodaDate("yyyy-MM-dd HH:mm:ss"))
    )(models.LoginLogQueryCondition.apply)(models.LoginLogQueryCondition.unapply)
  )

  /**
   * 添加登录日志
   * @param id
   * @return
   */
  def add(id: Long) = Action {
    implicit request =>
      loginLogForm.bindFromRequest.fold(
        hasErrors =
          form =>
            BadRequest(form.errorsAsJson),
        success =
          loginLog => {
            try {
              val inserted = loginLogService.insert(loginLog)
              Ok(Json.generate(Map(
                "result" -> 0,
                "success" -> true,
                "data" -> inserted,
                "message" -> "添加登录日志成功"
              ))).as(JSON)
            }
            catch {
              case e: Exception =>
                log.error("添加登录日志发生错误")
                log.error(e.getMessage)
                log.debug(e.getMessage, e.fillInStackTrace())
                Ok(Json.generate(Map(
                  "result" -> -1,
                  "success" -> false,
                  "message" -> "添加登录日志发生错误"
                ))).as(JSON)
            }
          }
      )
  }

  /**
   * 修改登录日志
   * @param id
   * @return
   */
  def update(id: Long) = Action {
    implicit request =>
      loginLogForm.bindFromRequest.fold(
        hasErrors =
          form =>
            BadRequest(form.errorsAsJson),
        success =
          loginLog => {
            try {
              loginLogService.update(loginLog)
              Ok(Json.generate(Map(
                "result" -> 0,
                "success" -> true,
                "message" -> "修改登录日志成功"
              ))).as(JSON)
            }
            catch {
              case e: Exception =>
                log.error("修改登录日志发生错误")
                log.error(e.getMessage)
                log.debug(e.getMessage, e.fillInStackTrace())
                Ok(Json.generate(Map(
                  "result" -> -1,
                  "success" -> false,
                  "message" -> "修改登录日志发生错误"
                ))).as(JSON)
            }
          }
      )
  }

  /**
   * 删除日至
   * @param id
   * @return
   */
  def remove(id: Long) = Action {
    implicit request =>
      try {
        loginLogService.deleteById(id)
        Ok(Json.generate(Map(
          "result" -> 0,
          "success" -> true,
          "message" -> "登录日志删除成功"
        ))).as(JSON)
      }
      catch {
        case e: Exception =>
          log.error("登录日志删除发生错误")
          log.error(e.getMessage)
          log.debug(e.getMessage, e.fillInStackTrace())
          Ok(Json.generate(Map(
            "result" -> -1,
            "success" -> false,
            "message" -> "登录日志删除发生错误"
          ))).as(JSON)
      }
  }

  /**
   * 获取日志详情
   * @param id
   * @return
   */
  def get(id: Long) = Action {
    implicit request =>
      try {
        val data = loginLogService.getById(id)
        Ok(Json.generate(Map(
          "result" -> 0,
          "success" -> true,
          "message" -> "获取日志详情成功"
        ))).as(JSON)
      }
      catch {
        case e: Exception =>
          log.error("获取日志详情发生错误")
          log.error(e.getMessage)
          log.debug(e.getMessage, e.fillInStackTrace())
          Ok(Json.generate(Map(
            "result" -> -1,
            "success" -> false,
            "message" -> "获取日志详情发生错误"
          ))).as(JSON)
      }
  }

  /**
   * 获取日志列表
   * @return
   */
  def list = Action {
    implicit request =>
      val page = request.getQueryString("page").getOrElse("1").toInt
      val limit = request.getQueryString("limit").getOrElse("25").toInt
      val sort = request.getQueryString("sort").getOrElse("logintiime")
      val dir = request.getQueryString("dir").getOrElse("asc").toLowerCase
      loginLogQueryForm.bindFromRequest.fold(
        hasErrors =
          form =>
            BadRequest(form.errorsAsJson),
        success =
          loginLogQueryCondition => {
            try {
              val pager = loginLogService.page(page, limit, loginLogQueryCondition, sort, dir)
              Ok(Json.generate(Map(
                "result" -> 0,
                "success" -> true,
                "data" -> pager.data,
                "message" -> "获取日志列表成功",
                "total" -> pager.total
              ))).as(JSON)
            }
            catch {
              case e: Exception =>
                log.error("获取日志列表发生错误")
                log.error(e.getMessage)
                log.debug(e.getMessage, e.fillInStackTrace())
                Ok(Json.generate(Map(
                  "success" -> false,
                  "result" -> -1,
                  "message" -> "获取日志列表发生错误"
                )))
            }
          }
      )
  }

}
