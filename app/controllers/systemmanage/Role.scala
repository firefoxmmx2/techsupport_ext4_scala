package controllers.systemmanage

import com.codahale.jerkson.Json
import models.systemmanage
import models.systemmanage.RoleQueryCondition
import play.api.Logger
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import util.ControllerUtils

import scala.util.{Failure, Success, Try}

/**
 * 角色
 */
object Role extends Controller with ControllerUtils{
  private val log = Logger.logger
  val roleForm = Form(
    mapping(
      "rolename" -> text,
      "roleDescription" -> optional(text),
      "roleType" -> default(text, "0"),
      "jzlbdm" -> optional(text),
      "jzlbmc" -> optional(text),
      "departid" -> default(longNumber, 0l),
      "id" -> optional(longNumber)
    )(systemmanage.Role.apply)(systemmanage.Role.unapply)
  )

  /**
   * 角色添加
   * @param id
   * @return
   */
  def add(id: Long) = Action {
    implicit request =>
      roleForm.bindFromRequest.fold(
        form =>
          BadRequest(form.errorsAsJson),
        role => {
          val response = Try {
            val inserted = roleService.insert(role)
            responseData(message = "角色添加成功", extraParams = Some(Map("inserted" -> inserted)))
          } match {
            case Failure(e: Exception) =>
              responseData(message = "角色添加发生错误", result = -1, e = Some(e))
            case Success(p) =>
              p
          }
          Ok(Json.generate(response))
        }
      )
  }

  /**
   * 角色信息删除
   * @param id
   * @return
   */
  def remove(id: Long) = Action {
    implicit request =>
      val response = Try {
        roleService.deleteById(id)
        responseData(message = "角色删除成功")
      } match {
        case Failure(e: Exception) =>
          responseData(result = -1, message = "角色删除发生错误", e = Some(e))
        case Success(p) =>
          p
      }
      Ok(Json.generate(response))
  }

  /**
   * 角色信息修改
   * @param id
   * @return
   */
  def update(id: Long) = Action {
    implicit request =>
      roleForm.bindFromRequest.fold(
        form =>
          BadRequest(form.errorsAsJson),
        role => {
          val response = Try {
            roleService.update(role)
            responseData(message = "角色修改成功")
          } match {
            case Failure(e) =>
              responseData(result = -1, message = "角色修改发生错误")
            case Success(p) =>
              p
          }
          Ok(Json.generate(response))
        }

      )
  }

  /**
   * 获取单一角色信息
   * @param id
   * @return
   */
  def get(id: Long) = Action {
    implicit request =>
      val response = Try {
        val data = roleService.getById(id)
        responseData(message = "获取单一角色信息成功", extraParams = Some(Map("data" -> data)))
      } match {
        case Failure(e: Exception) =>
          responseData(result = -1, message = "获取单一角色信息发生错误", e = Some(e))
        case Success(p) =>
          p
      }
      Ok(Json.generate(response))
  }

  val roleQueryForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "rolename" -> optional(text),
      "roleDescript" -> optional(text),
      "roleType" -> optional(text),
      "departid" -> optional(longNumber)
    )(RoleQueryCondition.apply)(RoleQueryCondition.unapply)
  )

  /**
   * 获取角色列表
   * @return
   */
  def list = Action {
    implicit request =>
      val pageno = request.getQueryString("page").getOrElse("1").toInt
      val limit = request.getQueryString("limit").getOrElse("20").toInt
      val sort = request.getQueryString("sort").getOrElse("id")
      val dir = request.getQueryString("dir").getOrElse("asc").toLowerCase

      roleQueryForm.bindFromRequest.fold(
        form =>
          BadRequest(form.errorsAsJson),
        roleQuery => {
          val response = Try {
            val page = roleService.page(pageno, limit, roleQuery, sort, dir)
            responseData(message = "获取角色列表成功", extraParams = Some(Map("total" -> page.total, "data" -> page.data)))
          } match {
            case Failure(e: Exception) =>
              responseData(result = -1, message = "获取角色列表发生错误", e = Some(e))
            case Success(p) =>
              p
          }
          Ok(Json.generate(response))
        }
      )
  }

  /**
   * 验证角色名称可用性
   * @param rolename
   * @return
   */
  def checkRolenameAvailable(rolename: String) = Action {
    implicit request =>
      val response = Try {
        val isAvailable = roleService.checkRolenameAvailable(rolename)
        responseData(extraParams = Some(Map("isAvailable" -> isAvailable)))
      } match {
        case Failure(e: Exception) =>
          responseData(result = -1, message = "验证角色名称可用性", e = Some(e))
        case Success(p) =>
          p
      }
      Ok(Json.generate(response))
  }

  case class RelateFunction(
                             roleIds: List[Long],
                             removedFunctionIds: Option[List[String]] = None,
                             addedFunctionIds: Option[List[String]] = None
                             ) {
    override def toString = "[roleIds=" + roleIds + ",removedFunctionIds=" +
      removedFunctionIds + ",addedFunctionIds=" + addedFunctionIds + "]"
  }

  val relateFunctionForm = Form(
    mapping(
      "roleIds" -> Forms.list(longNumber),
      "removedFunctionIds" -> optional(Forms.list(text)),
      "addedFunctionsIds" -> optional(Forms.list(text))
    )(RelateFunction.apply)(RelateFunction.unapply)
  )

  /**
   *
   * 关联功能
   */
  def relateFunctions = Action {
    implicit request =>
      log.debug("=" * 13 + "关联功能开始" + "=" * 13)
      relateFunctionForm.bindFromRequest.fold(
        form =>
          BadRequest(form.errorsAsJson),
        relateFunction => {
          val response = Try {
            log.debug("=" * 13 + "relateFunction=" + relateFunction + "=" * 13)
            roleService.relateFunctions(relateFunction.roleIds,
              relateFunction.removedFunctionIds,
              relateFunction.addedFunctionIds)
            responseData(message = "关联功能成功")
          } match {
            case Failure(e: Exception) =>
              responseData(result = -1, message = "关联功能发生错误", e = Some(e))
            case Success(p) =>
              p
          }
          log.debug("=" * 13 + "关联功能结束" + "=" * 13)
          Ok(Json.generate(response))
        }
      )
  }

  case class RelateMenu(
                         roleIds: Seq[Long],
                         removedMenuIds: Option[Seq[String]],
                         addedMenuIds: Option[Seq[String]]
                         )

  val relateMenusForm = Form(
    mapping(
      "roleIds" -> seq(longNumber),
      "removedMenuIds" -> optional(seq(text)),
      "addedMenuIds" -> optional(seq(text))
    )(RelateMenu.apply)(RelateMenu.unapply)
  )

  /**
   * 关联菜单
   */
  def relateMenus = Action {
    implicit request =>
      log.debug("="*13+"关联菜单"+"="*13)
      relateMenusForm.bindFromRequest.fold(
        form =>
          BadRequest(form.errorsAsJson),
        relateMenu => {
          val response = Try {
            log.debug("="*13+relateMenu+"="*13)
            roleService.relateMenus(relateMenu.roleIds, relateMenu.removedMenuIds, relateMenu.addedMenuIds)
            responseData(message = "关联菜单成功")
          } match {
            case Failure(e: Exception) =>
              responseData(result = -1, message = "关联菜单发生错误", e = Some(e))
            case Success(p) =>
              p
          }
          Ok(Json.generate(response))
        }
      )
  }


  val relateForm = Form(
    single("roleIds" -> Forms.list(longNumber))
  )

  /**
   * 获取关联功能列表
   * @return
   */
  def getRelateFuncs = Action {
    implicit request =>
      log.debug("==================获取关联功能列表===================")
      relateForm.bindFromRequest.fold(
        form =>
          BadRequest(form.errorsAsJson),
        roleIds => {
          val response = Try {
            val lData = roleService.getRelateFunctions(roleIds)
            responseData(message = "获取关联功能列表成功", extraParams = Some(Map("data" -> lData)))
          } match {
            case Failure(e: Exception) =>
              responseData(result = -1, message = "获取关联功能列表发生错误", e = Some(e))
            case Success(p) =>
              p
          }
          Ok(Json.generate(response))
        }
      )
  }

  /**
   * 关联菜单列表
   * @return
   */
  def getRelateMenus = Action {
    implicit request =>
      relateForm.bindFromRequest().fold(
        form =>
          BadRequest(form.errorsAsJson),
        roleIds => {
          val response = Try {
            val lData = roleService.getRelateMenus(roleIds)
            responseData(message = "获取关联菜单列表成功", extraParams = Some(Map("data" -> lData)))
          } match {
            case Failure(e: Exception) =>
              responseData(result = -1, message = "获取关联菜单列表发生错误", e = Some(e))
            case Success(p) =>
              p
          }
          Ok(Json.generate(response))
        }
      )
  }


}
