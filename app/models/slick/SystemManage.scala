package models.slick

import com.typesafe.slick.driver.oracle.OracleDriver.simple._
import scala.slick.lifted._
import models._

/**
 * Created by hooxin on 14-3-16.
 */

object SystemManage {

}

/**
 * 机构表
 * @param tag 别名
 */
class DepartmentTable(tag: Tag) extends Table[Department](tag, "T_DEPARTMENT") {
  def departid = column[Long]("DEPARTID", O.PrimaryKey)

  def departcode = column[String]("DEPARTCODE")

  def departname = column[String]("DEPARTNAME")

  def departlevel = column[Int]("DEPARTLEVEL")

  def departfullcode = column[String]("DEPARTFULLCODE")

  def parentDepartid = column[Long]("PARENTDEPARTID")

  def nodeOrder = column[Int]("NODEORDER")

  def isLeaf = column[String]("ISLEAF")

  def departsimplepin = column[Option[String]]("DEPARTSIMPLEPIN", O.Nullable)

  def departallpin = column[Option[String]]("DEPARTALLPIN", O.Nullable)

  def departbrevitycode = column[Option[String]]("DEPARTBREVITYCODE", O.Nullable)

  def * = (departid, departcode, departname, departlevel, departfullcode, parentDepartid,
    nodeOrder, isLeaf, departsimplepin, departallpin, departbrevitycode) <>(Department.tupled, Department.unapply)

  val sequence = Sequence[Long]("DEPARTID")

}

/**
 * 菜单表
 * @param tag 别名
 */
class MenuTable(tag: Tag) extends Table[Menu](tag, "T_MENU") {
  def menucode = column[String]("MENUCODE", O.PrimaryKey)

  def menuname = column[String]("MENUNAME")

  def funcentry = column[String]("FUNCENTRY")

  def menulevel = column[Int]("MENULEVEL")

  def parentmenucode = column[String]("PARENTMENUCODE")

  def menufullcode = column[String]("MENUFULLCODE")

  def nodeorder = column[Int]("NODEORDER")

  def isleaf = column[String]("ISLEAF")

  def systemcode = column[String]("systemcode", O.NotNull)

  def * = (menucode,
    menuname,
    funcentry,
    menulevel,
    parentmenucode,
    menufullcode,
    nodeorder,
    isleaf,
    systemcode) <>(Menu.tupled, Menu.unapply)
}

/**
 * 角色表
 * @param tag 别名
 */
class RoleTable(tag: Tag) extends Table[Role](tag, "T_ROLE") {
  val sequence = Sequence[Long]("ROLEID")

  def roleid = column[Long]("ROLEID", O.PrimaryKey)

  def departid = column[Long]("DEPARTID")

  def rolename = column[String]("ROLENAME")

  def roleDescription = column[Option[String]]("ROLEDESCRIPTION")

  def roleType = column[Option[String]]("ROLETYPE")

  def jzlbdm = column[Option[String]]("JZLBDM")

  def jzlbmc = column[Option[String]]("JZLBMC")

  def * = (roleid, departid, rolename, roleDescription, roleType,
    jzlbdm, jzlbmc) <>(Role.tupled, Role.unapply)
}

/**
 * 系统表
 * @param tag 别名
 */
class SystemTable(tag: Tag) extends Table[System](tag, "T_SYSTEM") {
  def systemcode = column[String]("SYSTEMCODE", O.PrimaryKey)

  def systemname = column[String]("SYSTEMNAME")

  def systemdefine = column[String]("SYSTEMDEFINE")

  def picturepath = column[String]("PICTUREPATH")

  def parentsystemcode = column[String]("PARENTSYSTEMCODE")

  def nodeorder = column[Int]("NODEORDER")

  def isleaf = column[String]("ISLEAF")

  def fullcode = column[String]("FULLCODE")

  def * = (systemcode, systemname, systemdefine, picturepath, parentsystemcode,
    nodeorder, isleaf, fullcode) <>(System.tupled, System.unapply)
}

/**
 * 用户表
 * @param tag 别名
 */
class UserTable(tag: Tag) extends Table[User](tag, "T_USER") {
  val sequence = Sequence[Long]("USERID")

  def userid = column[Long]("USERID", O.PrimaryKey)

  def departid = column[Long]("DEPARTID")

  def useraccount = column[String]("USERACCOUNT")

  def username = column[String]("USERNAME")

  def password = column[String]("PASSWORD")

  def idnum = column[String]("IDNUM")

  def mobilePhone = column[Option[String]]("MOBILEPHONE")

  def userorder = column[Int]("USERORDER")

  def isVaild = column[String]("ISVAILD")

  def userType = column[Option[String]]("USERTYPE")

  def jzlbdm = column[Option[String]]("JZLBDM")

  def jzlbmc = column[Option[String]]("JZLBMC")

  def email = column[Option[String]]("EMAIL")

  def * = (userid, departid, useraccount, username, password, idnum, mobilePhone, userorder,
    isVaild, userType, jzlbdm, jzlbmc, email) <>(User.tupled, User.unapply)

}

/**
 * 用户角色
 * @param tag 别名
 */
class UserRole(tag: Tag) extends Table[(Long, Long)](tag, "T_USER_ROLE") {
  def roleid = column[Long]("ROLEID")

  def userid = column[Long]("USERID")

  def * = (roleid, userid)
}

/**
 * 角色菜单
 * @param tag 别名
 */
class RoleMenu(tag: Tag) extends Table[(Long, String)](tag, "T_ROLE_MENU") {
  def roleid = column[Long]("ROLEID")

  def menucode = column[String]("MENUCODE")

  def * = (roleid, menucode)
}




