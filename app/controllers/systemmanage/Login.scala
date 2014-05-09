package controllers.systemmanage

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.cache.Cache
import util.ComponentRegister._
import com.codahale.jerkson.Json
import play.api.Play.current
import java.util.UUID

/**
 * Created by hooxin on 14-3-30.
 */
object Login extends Controller {
  val log = play.api.Logger.logger

  case class Login(useraccount: String, password: String)

  val loginForm = Form(
    mapping(
      "useraccount" -> nonEmptyText,
      "password" -> nonEmptyText
    )(Login.apply)(Login.unapply)
  )

  /**
   * 登录
   * @return
   */
  def login = Action {
    implicit request =>
      loginForm.bindFromRequest().fold(
        hasErrors => BadRequest,
        login => {
          log.debug("调试登录函数")

          val user = userService.getByUseraccountPassword(login.useraccount, login.password)
          log.debug("="*13+"user = "+user+"="*13)

          if (user == null)
            Ok(Json.generate(Map("result" -> -3,
              "message" -> "帐号或者密码错误")))
          else {
            log.debug("存在指定用户")
            val authCode = UUID.randomUUID().toString
            val userInfoMap = Map("userid" -> user.id.get,
              "username" -> user.username)
            Cache.set(authCode, userInfoMap)
            Ok(Json.generate(Map("result" -> 0,
              "message" -> "登录成功",
              "authCode" -> authCode,
              "userInfo" -> userInfoMap)))
              .withSession("authCode" -> authCode)
          }
        }
      )
  }

  /**
   * 登出
   * @return
   */
  def logout = Action {
    implicit request =>
      Cache.remove(request.session.get("authCode").getOrElse(""))
      Ok(Json.generate(Map("result" -> 0,
        "message" -> "登出成功"))).withNewSession
  }

  /**
   *
   * @return
   */
  def heartCheck = Action {
    implicit request =>
      request.session.get("authCode") match {
        case Some(code) =>
          if (Cache.get(code) != None)
            Ok(Json.generate(Map("result" -> 0,
              "message" -> "",
              "authCode" -> code,
              "userInfo" -> Cache.get(code)
            )))
          else
            Ok(Json.generate(Map("result" -> -2, "message" -> "未登录")))
        case _ => Ok(Json.generate(Map("result" -> -2, "message" -> "未登录")))
      }

  }
}
