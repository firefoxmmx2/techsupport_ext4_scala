package models.slick.systemmanage

/**
 * Created by hooxin on 15-2-10.
 */
import com.typesafe.slick.driver.oracle.OracleDriver.simple._
import models.systemmanage.{RoleFunc, RoleMenu, Role}

import scala.slick.lifted._

class RoleTable(tag:Tag) extends Table[Role](tag,"t_role"){
  def rolename = column[String]("rolename")
  def roleDescription = column[String]("roleDescription",O.Nullable)
  def roleType = column[String]("roleType")
  def jzlbdm = column[String]("jzlbdm",O.Nullable)
  def jzlbmc = column[String]("jzlbmc",O.Nullable)
  def departid = column[Long]("departid")
  def id = column[Long]("id",O.PrimaryKey)

  def * = (
      rolename,
      roleDescription.?,
      roleType,
      jzlbdm.?,
      jzlbmc.?,
      departid,
      id.?
    ) <> (Role.tupled,Role.unapply)
}

class RoleMenuTable(tag: Tag) extends Table[RoleMenu](tag,"t_role_menu") {
  def menucode = column[String]("menucode")
  def roleid = column[Long]("roleid")

  def * = (
    menucode,
      roleid
    ) <> (RoleMenu.tupled,RoleMenu.unapply)
}

class RoleFuncTable(tag: Tag) extends Table[RoleFunc](tag,"rf") {
  def roleId = column[Long]("roleId")
  def funccode = column[String]("funccode")
  def param = column[String]("param",O.Nullable)

  def * = (
    roleId,
    funccode,
    param.?
    ) <> (RoleFunc.tupled,RoleFunc.unapply)
}
