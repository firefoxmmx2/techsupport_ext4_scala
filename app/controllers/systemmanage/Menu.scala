package controllers.systemmanage

import play.api.mvc._
import util.ComponentRegister._
import play.api.data._
import play.api.data.Forms._
import models.MenuQueryCondition
import play.api.Play.current
import play.api.cache.Cache
import com.codahale.jerkson.Json
import play.api.Logger

/**
 * Created by hooxin on 14-2-10.
 */
object Menu extends Controller {
  def add = TODO

  def remove(id: Long) = TODO

  def update(id: Long) = TODO

  def get(id: Long) = TODO

  def list = TODO

  val menuForm = Form(mapping("id" -> text,
    "menuname" -> text,
    "funcentry" -> text,
    "menulevel" -> number,
    "parentmenucode" -> text,
    "menufullcode" -> text,
    "nodeorder" -> number,
    "isleaf" -> text,
    "systemcode" -> text)(models.Menu.apply)(models.Menu.unapply))

  //查询表单
  val menuQueryForm = Form(
    mapping("id" -> optional(text),
      "menuname" -> optional(text),
      "funcentry" -> optional(text),
      "menulevel" -> optional(number),
      "parentmenucode" -> optional(text),
      "menufullcode" -> optional(text),
      "isleaf" -> optional(text),
      "systemcode" -> optional(text)
    )(MenuQueryCondition.apply)(MenuQueryCondition.unapply))

  def userMenuNode = Action {
    implicit req =>
      menuQueryForm.bindFromRequest().fold(
        hasError => BadRequest,
        query => {
          val userInfo = Cache.get(req.session.get("authCode").getOrElse("")).getOrElse(None)
          if (userInfo == null || userInfo == None)
            Ok(Json.generate(Map("result" -> -2,
              "success" -> false,
              "message" -> "未登录或者登录已过期")))
          else {
            val userInfo_ = userInfo.asInstanceOf[Map[String, Any]]
            val userid = userInfo_.get("userid").asInstanceOf[Option[Long]].get
            Logger.debug("开始查询用户菜单列表")
            val menuList = menuService.getUserMenus(userid, query.parentmenucode.get, query.systemcode)
            Logger.debug("=" * 13 + "menuList = " + menuList + "=" * 13)
            Logger.debug("开始封装为json")
            val root = menuList.map(m => {
              val leaf = if (m.isleaf == "Y") true else false
              Map("id" -> m.id,
                "text" -> m.menuname,
                "funcentry" -> m.funcentry,
                "leaf" -> leaf,
                "parentId" -> m.parentmenucode,
                "systemcode" -> m.systemcode)
            })
            Logger.debug("封装结束")
            Ok(Json.generate(Map(
              "success" -> true,
              "result" -> 0,
              "message" -> "",
              "root" -> root)))
          }

        }
      )
  }

}
