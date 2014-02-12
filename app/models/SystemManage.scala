package models

import scala.slick.lifted._
import com.typesafe.slick.driver.oracle.OracleDriver.simple._
import com.typesafe.slick.driver.oracle.OracleDriver._
import play.api.Play.current

/**
 * 机构
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

  //  def * = (departid,departcode,departlevel,departfullcode,parentDepartid,
  //    nodeOrder,isLeaf,departsimplepin,departallpin,departbrevitycode)
  //    <> (Department.tupled, Department.unapply)
  def * = (departid, departcode, departlevel, departfullcode, parentDepartid,
    nodeOrder, isLeaf, departsimplepin, departallpin, departbrevitycode) <>(Department.tupled, Department.unapply)
}

/**
 * 用户
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
         isVaild, userType, jzlbdm, jzlbmc, email) <> (User.tupled, User.unapply)
}

/**
 * 角色
 */
case class Role(roleid: Long, departid: Long, rolename: String, roleDescription: String = None,
                roleType: Int = None, jzlbdm: String = None, jzlbmc: String = None)
class Roles(tag:Tag) extends Table[Role](tag,"t_role") {
  def roleid = column[Long]("roleid",O.PrimaryKey)
  def departid= column[Long]("departid")
  def rolename = column[String]("rolename")
  def roleDescription = column[String]("roledescription")
  def roleType = column[String]("roletype")
  def jzlbdm = column[String]("jzlbdm")
  def jzlbmc = column[String]("jzlbmc")

  def * = (roleid,departid,rolename,roleDescription,roleType,
    jzlbdm,jzlbmc) <> (Role.tupled,Role.unapply)
}

/**
 * 系统管理模块
 */
object SystemManage {

  //  val departments = table[Department]("t_department")
  //  val users = table[User]("t_user")
  //  val roles = table[Role]("t_role")

  val departments = TableQuery[Departments]

}


