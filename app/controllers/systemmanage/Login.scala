package controllers.systemmanage

import java.util.UUID

import com.codahale.jerkson.Json
import org.joda.time.DateTime
import play.api.Play.current
import play.api.cache.Cache
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import util.ComponentRegister

/**
 * 登录
 */
object Login extends Controller with ComponentRegister {
  private val log = play.api.Logger(classOf[Login])

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
      loginForm.bindFromRequest.fold(
        hasErrors => BadRequest,
        login => {
          log.debug("调试登录函数")

          val user = userService.getByUseraccountPassword(login.useraccount, login.password)
          log.debug("=" * 13 + "user = " + user + "=" * 13)

          if (user == null)
            Ok(Json.generate(Map("result" -> -3,
              "success" -> false,
              "message" -> "帐号或者密码错误")))
          else {
            log.debug("存在指定用户")
            val department = departmentService.getById(user.departid)
            val departcode = department match {
              case Some(x) => x.departcode
              case None => ""
            }
            val departname = department match {
              case Some(x) => x.departname
              case _ => ""
            }
            val loginLog = loginLogService.insert(models.systemmanage.LoginLog(
              loginuserid = user.id.get,
              useraccount = user.useraccount,
              username = user.username,
              loginunitcode = departcode,
              loginunitname = departname,
              loginip = request.remoteAddress,
              loginmac = None,
              logintiime = new DateTime()
            ))
            val authCode = UUID.randomUUID().toString
            val userInfoMap = Map("userid" -> user.id.get,
              "username" -> user.username,
              "loginlogid" -> loginLog.id.get)
            Cache.set(authCode, userInfoMap)
            Ok(Json.generate(Map("result" -> 0,
              "success" -> true,
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
      request.session.get("authCode") match {
        case Some(s) =>
          Cache.get(s) match {
            case Some(x: Map[String, Any]) =>
              loginLogService.getById(x.getOrElseAs("loginlogid", 0)) match {
                case Some(loginLog) =>
                  loginLogService.update(loginLog.copy(quittime = Some(new DateTime())))
                case None =>
              }
            case None =>
          }
      }

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
              "success" -> true,
              "message" -> "",
              "authCode" -> code,
              "userInfo" -> Cache.get(code)
            )))
          else
            Ok(Json.generate(Map("result" -> -2, "message" -> "未登录", "success" -> false)))
        case _ => Ok(Json.generate(Map("result" -> -2, "message" -> "未登录", "success" -> false)))
      }

  }
}
