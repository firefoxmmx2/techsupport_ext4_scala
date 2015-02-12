package models.slick.systemmanage

import models.systemmanage.DictItem

import scala.slick.lifted._
import com.typesafe.slick.driver.oracle.OracleDriver.simple._

/**
 * Created by hooxin on 15-1-26.
 */
class DictItemTable(tag: Tag) extends Table[DictItem](tag,"t_dictitem"){
  def dictcode = column[String]("dictcode")
  def displayName = column[String]("displayName")
  def factValue = column[String]("factValue")
  def appendValue = column[String]("appendValue")
  def superItemId = column[Long]("superItemId")
  def sibOrder = column[Int]("sibOrder")
  def isleaf = column[String]("isleaf")
  def displayFlag = column[Int]("displayFlag")
  def isValid = column[Int]("isValid")
  def itemSimplePin = column[String]("itemSimplePin")
  def itemAllPin = column[String]("itemAllPin")
  def id = column[Long]("id",O.PrimaryKey)

  def * = (
    dictcode,
      displayName,
      factValue,
      appendValue.?,
      superItemId.?,
      sibOrder,
      isleaf,
      displayFlag,
      isValid,
      itemSimplePin.?,
      itemAllPin.?,
      id.?
    ) <> (DictItem.tupled,DictItem.unapply)
}
