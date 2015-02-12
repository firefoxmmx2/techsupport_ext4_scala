package models.slick.systemmanage

import com.typesafe.slick.driver.oracle.OracleDriver.simple._
import models.systemmanage.Menu

/**
 * Created by hooxin on 15-2-11.
 */
class MenuTable(tag:Tag) extends Table[Menu](tag,"t_menu"){
  def id = column[String]("menucode",O.PrimaryKey)
  def menuname = column[String]("menuname")
  def funcentry = column[String]("funcentry")
  def menulevel = column[Int]("menulevel")
  def parentmenucode = column[String]("parentmenucode")
  def menufullcode = column[String]("menufullcode")
  def nodeorder = column[Int]("nodeorder")
  def isleaf = column[String]("isleaf")
  def systemcode = column[String]("systemcode")

  def * = (
      id,
      menuname,
      funcentry,
      menulevel,
      parentmenucode,
      menufullcode,
      nodeorder,
      isleaf,
      systemcode
    ) <> (Menu.tupled,Menu.unapply)
}
