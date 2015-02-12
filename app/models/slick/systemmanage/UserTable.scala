package models.slick.systemmanage

import models.systemmanage.{UserRole, User}
import com.typesafe.slick.driver.oracle.OracleDriver.simple._
import scala.slick.lifted._

/**
 * Created by hooxin on 15-1-26.
 */
class UserTable(tag: Tag) extends Table[User](tag, "t_user") {
  def departid = column[Long]("departid")

  def useraccount = column[String]("useraccount")

  def username = column[String]("username")

  def password = column[String]("password")

  def idnum = column[String]("idnum", O.Nullable)

  def mobilePhone = column[String]("mobilePhone", O.Nullable)

  def userorder = column[Int]("userorder")

  def isValid = column[String]("isValid")

  def userType = column[String]("userType", O.Nullable)

  def jzlbdm = column[String]("jzlbdm", O.Nullable)

  def jzlbmc = column[String]("jzlbmc", O.Nullable)

  def email = column[String]("email", O.Nullable)

  def id = column[Long]("userid", O.PrimaryKey)

  def * = (
    departid,
    useraccount,
    username,
    password,
    idnum.?,
    mobilePhone.?,
    userorder,
    isValid,
    userType.?,
    jzlbdm.?,
    jzlbmc.?,
    email.?,
    id.?) <>(User.tupled, User.unapply)
}

class UserRoleTable(tag: Tag) extends Table[UserRole](tag, "t_user_role") {
  def roleid = column[Long]("roleid")

  def userid = column[Long]("userid")

  def * = (
    roleid,
    userid
    ) <>(UserRole.tupled, UserRole.unapply)
}