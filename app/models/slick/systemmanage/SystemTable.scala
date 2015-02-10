package models.slick.systemmanage

import com.typesafe.slick.driver.oracle.OracleDriver.simple._
import models.systemmanage.System
import scala.slick.lifted._

/**
 * Created by hooxin on 15-2-11.
 */
class SystemTable(tag:Tag) extends Table[System](tag,"s"){
  def id = column[String]("id")
  def systemname = column[String]("systemname")
  def systemdefine = column[String]("systemdefine")
  def picturepath = column[String]("picturepath")
  def parentsystemcode = column[String]("parentsystemcode")
  def nodeorder = column[Int]("nodeorder")
  def isleaf = column[String]("isleaf")
  def fullcode = column[String]("fullcode")

  def * = (
      id,
      systemname,
      systemdefine,
      picturepath,
      parentsystemcode,
      nodeorder,
      isleaf,
      fullcode
    ) <> (System.tupled,System.unapply)
}
