package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  /**
   * 主页
   * @return
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  /**
   * 登录页面
   * @return
   */
  def login = Action {
    Ok(views.html.login())
  }


}