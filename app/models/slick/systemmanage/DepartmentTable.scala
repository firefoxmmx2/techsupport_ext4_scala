package models.slick.systemmanage

import com.typesafe.slick.driver.oracle.OracleDriver.simple._
import models.systemmanage.Department
import scala.slick.lifted._

/**
 * Created by hooxin on 15-1-26.
 */
class DepartmentTable(tag: Tag) extends Table[Department](tag, "dpt") {
  def departcode = column[String]("departcode")
  def departname = column[String]("departname")
  def departlevel = column[Int]("departlevel")
  def departfullcode = column[String]("departfullcode")
  def parentDepartid = column[Long]("parentDepartid", O.Nullable)
  def nodeOrder = column[Int]("nodeOrder")
  def isLeaf = column[String]("isLeaf")
  def departsimplepin = column[String]("departsimplepin", O.Nullable)
  def departallpin = column[String]("departallpin", O.Nullable)
  def departbrevitycode = column[String]("departbrevitycode", O.Nullable)
  def id = column[Long]("departid", O.PrimaryKey)
  def * = (
    departcode,
      departname,
      departlevel,
      departfullcode,
      parentDepartid.?,
      nodeOrder,
      isLeaf,
      departsimplepin.?,
      departallpin.?,
      departbrevitycode.?,
      id.?
    )<> (Department.tupled,Department.unapply)
}
