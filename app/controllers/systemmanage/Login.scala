package controllers.systemmanage

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import util.ComponentRegister._
import com.codahale.jerkson.Json

/**
 * Created by hooxin on 14-3-30.
 */
object Login extends Controller {

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
          val user = userService.getByUseraccountPassword(login.useraccount, login.password)
          if (user == null)
            Ok(Json.generate(Map("result" -> -3,
              "message" -> "帐号或者密码错误")))
          else {
            Ok(Json.generate(Map("result" -> 0,
              "message" -> "登录成功",
              "userInfo" -> Map("user" -> user)))).withSession("userInfo" -> Json.generate(Map("user" -> user)))
          }
        }
      )
  }

  /**
   * 登出
   * @return
   */
  def logout = Action {
    implicit reqeust =>
      reqeust.session - "userInfo"
      Ok(Json.generate(Map("result" -> 0,
        "message" -> "登出成功")))
  }
}
