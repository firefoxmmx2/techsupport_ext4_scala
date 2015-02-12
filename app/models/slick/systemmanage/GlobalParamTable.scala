package models.slick.systemmanage

import models.systemmanage.GlobalParam

import scala.slick.lifted.Tag
import com.typesafe.slick.driver.oracle.OracleDriver.simple._

/**
 * Created by hooxin on 15-2-12.
 */
class GlobalParamTable(tag: Tag) extends Table[GlobalParam](tag, "t_globalpar") {
  def id = column[String]("globalparcode", O.PrimaryKey)

  def globalparname = column[String]("globalparname")

  def globalparvalue = column[String]("globalparvalue")

  def * = (
    id,
    globalparname,
    globalparvalue
    ) <>(GlobalParam.tupled, GlobalParam.unapply)
}
