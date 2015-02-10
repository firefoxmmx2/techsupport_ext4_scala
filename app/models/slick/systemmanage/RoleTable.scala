package models.slick.systemmanage

/**
 * Created by hooxin on 15-2-10.
 */
import com.typesafe.slick.driver.oracle.OracleDriver.simple._
import models.systemmanage.Role
import scala.slick.lifted._

class RoleTable(tag:Tag) extends Table[Role](tag,"r"){
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