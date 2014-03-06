package models


import com.typesafe.slick.driver.oracle.OracleDriver.simple._


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
 * @param departsimplepin 机构简拼 搜索用
 * @param departallpin 机构全拼 搜索用
 * @param departbrevitycode 机构省级代码
 */

case class Department(departid: Long, departcode: String, departname: String,
                      departlevel: Int, departfullcode: String,
                      parentDepartid: Long,
                      nodeOrder: Int = 0, isLeaf: String = "Y",
                      departsimplepin: Option[String], departallpin: Option[String],
                      departbrevitycode: Option[String])

class Departments(tag: Tag) extends Table[Department](tag, "T_DEPARTMENT") {
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

  val sequence = Sequence[Long]("departid")
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
                password: String, idnum: String, mobilePhone: Option[String], userorder: Int = 1,
                isVaild: String = "Y", userType: Option[String], jzlbdm: Option[String], jzlbmc: Option[String],
                email: Option[String])

class Users(tag: Tag) extends Table[User](tag, "T_USER") {
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
 * 角色
 * @param roleid 角色id
 * @param departid 机构id
 * @param rolename 角色名称
 * @param roleDescription 角色描述
 * @param roleType 角色类型
 * @param jzlbdm
 * @param jzlbmc
 */
case class Role(roleid: Long, departid: Long, rolename: String, roleDescription: Option[String],
                roleType: Option[String], jzlbdm: Option[String], jzlbmc: Option[String])

class Roles(tag: Tag) extends Table[Role](tag, "T_ROLE") {
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

class Systems(tag: Tag) extends Table[System](tag, "T_SYSTEM") {
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

class Menus(tag: Tag) extends Table[Menu](tag, "T_MENU") {
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

