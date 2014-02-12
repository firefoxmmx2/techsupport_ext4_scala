package models

import scala.slick.lifted._
import com.typesafe.slick.driver.oracle.OracleDriver.simple._
import com.typesafe.slick.driver.oracle.OracleDriver._
import play.api.Play.current

/**
 * 机构
 * @param departid 机构id 唯一
 * @param departcode 机构代码 唯一
 * @param departname 机构名称
 * @param departlevel 机构级别
 * @param departfullcode 机构全码
 * @param parentDepartid 父机构id
 * @param nodeOrder 排序号
 * @param isLeaf 是否为叶子节点 默认Y
 * @param departsimplepin 机构
 * @param departallpin 机构简拼 搜索用
 * @param departbrevitycode 机构全拼 搜索用
 */

case class Department(departid: Long, departcode: String, departname: String,
                      departlevel: Int, departfullcode: String,
                      parentDepartid: Long,
                      nodeOrder: Int = 0, isLeaf: String = "Y",
                      departsimplepin: String = None, departallpin: String = None,
                      departbrevitycode: String = departcode)

class Departments(tag: Tag) extends Table[Department](tag, "t_department") {
  def departid = column[Long]("departid", O.PrimaryKey)

  def departcode = column[String]("departcode")

  def departlevel = column[Int]("departlevel")

  def departfullcode = column[String]("departfullcode")

  def parentDepartid = column[Long]("parentdepartid")

  def nodeOrder = column[Int]("nodeorder")

  def isLeaf = column[String]("isleaf")

  def departsimplepin = column[String]("departsimplepin")

  def departallpin = column[String]("departallpin")

  def departbrevitycode = column[String]("departbrevitycode")

  def * = (departid, departcode, departlevel, departfullcode, parentDepartid,
    nodeOrder, isLeaf, departsimplepin, departallpin, departbrevitycode) <>(Department.tupled, Department.unapply)
}

/**
 * 用户
 * @param userid 用户id
 * @param departid 机构id
 * @param useraccount 用户帐号
 * @param username 用户名称
 * @param password 密码 MD5加密
 * @param idnum 身份证号码
 * @param mobilePhone 手机号码
 * @param userorder 排序号
 * @param isVaild 是否有效 默认Y
 * @param userType 用户类别
 * @param jzlbdm
 * @param jzlbmc
 * @param email 邮箱
 */
case class User(userid: Long, departid: Long, useraccount: String, username: String,
                password: String, idnum: String, mobilePhone: String = None, userorder: Int = 1,
                isVaild: String = "Y", userType: String = None, jzlbdm: String = None, jzlbmc: String = None,
                email: String = None)

class Users(tag: Tag) extends Table[User](tag, "t_user") {
  def userid = column[Long]("userid", O.PrimaryKey)

  def departid = column[Long]("departid")

  def useraccount = column[String]("useraccount")

  def username = column[String]("username")

  def password = column[String]("password")

  def idnum = column[String]("idnum")

  def mobilePhone = column[String]("mobilePhone")

  def userorder = column[Int]("userorder")

  def isVaild = column[String]("isvaild")

  def userType = column[String]("usertype")

  def jzlbdm = column[String]("jzlbdm")

  def jzlbmc = column[String]("jzlbmc")

  def email = column[String]("email")

  def * = (userid, departid, useraccount, username, password, idnum, mobilePhone, userorder,
    isVaild, userType, jzlbdm, jzlbmc, email) <>(User.tupled, User.unapply)
}

/**
 * 角色
 * @param roleid 角色id
 * @param departid 机构id
 * @param rolename 角色名称
 * @param roleDescription 角色描述
 * @param roleType 角色类型
 * @param jzlbdm
 * @param jzlbmc
 */
case class Role(roleid: Long, departid: Long, rolename: String, roleDescription: String = None,
                roleType: Int = None, jzlbdm: String = None, jzlbmc: String = None)

class Roles(tag: Tag) extends Table[Role](tag, "t_role") {
  def roleid = column[Long]("roleid", O.PrimaryKey)

  def departid = column[Long]("departid")

  def rolename = column[String]("rolename")

  def roleDescription = column[String]("roledescription")

  def roleType = column[String]("roletype")

  def jzlbdm = column[String]("jzlbdm")

  def jzlbmc = column[String]("jzlbmc")

  def * = (roleid, departid, rolename, roleDescription, roleType,
    jzlbdm, jzlbmc) <>(Role.tupled, Role.unapply)
}

/**
 * 系统
 * @param systemcode 系统代码
 * @param systemname 系统名称
 * @param systemdefine 系统定义
 * @param picturepath 系统图标路径
 * @param parentsystemcode 父系统代码
 * @param nodeorder 节点排序号
 * @param isleaf 是否为叶子节点 默认Y
 * @param fullcode 系统全码 如techsupport.systemmanage.
 */
case class System(systemcode: String, systemname: String, systemdefine: String, picturepath: String,
                  parentsystemcode: String, nodeorder: Int, isleaf: String, fullcode: String)

class Systems(tag: Tag) extends Table[System](tag, "t_system") {
  def systemcode = column[String]("systemcode", O.PrimaryKey)

  def systemname = column[String]("systemname")

  def systemdefine = column[String]("systemdefine")

  def picturepath = column[String]("picturepath")

  def parentsystemcode = column[String]("parentsystemcode")

  def nodeorder = column[String]("nodeorder")

  def isleaf = column[String]("isleaf")

  def fullcode = column[String]("fullcode")

  def * = (systemcode, systemname, systemdefine, picturepath,
    nodeorder, isleaf, fullcode) <>(System.tupled, System.unapply)
}

/**
 * 菜单
 * @param menucode 菜单代码 唯一
 * @param menuname 菜单名称
 * @param funcentry 菜单地址
 * @param menulevel 菜单
 * @param parentmenucode 父菜单代码
 * @param menufullcode 菜单全码
 * @param nodeorder 排序号
 * @param isleaf 是否为叶子节点 默认Y
 * @param systemcode 系统代码 关联 System.systemcode
 */
case class Menu(menucode: String, menuname: String, funcentry: String, menulevel: Int,
                parentmenucode: String, menufullcode: String, nodeorder: Int,
                isleaf: String, systemcode: String)

class Menus(tag: Tag) extends Table[Menu](tag, "t_menu") {
  def menucode = column[String]("menucode", O.PrimaryKey)

  def menuname = column[String]("menuname")

  def funcentry = column[String]("funcentry")

  def menulevel = column[Int]("menulevel")

  def parentmenucode = column[String]("parentmenucode")

  def menufullcode = column[String]("menufullcode")

  def nodeorder = column[Int]("nodeorder")

  def isleaf = column[String]("isleaf")

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
 * 系统管理模块
 */
object SystemManage {

  //  val departments = table[Department]("t_department")
  //  val users = table[User]("t_user")
  //  val roles = table[Role]("t_role")

  val departments = TableQuery[Departments]
  val users = TableQuery[Users]
  val roles = TableQuery[Roles]
}


