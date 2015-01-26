package models.slick.systemmanage

import models.systemmanage.Dict
import org.joda.time.DateTime

import scala.slick.lifted._
import com.typesafe.slick.driver.oracle.OracleDriver.simple._

/**
 * Created by hooxin on 15-1-26.
 */
class DictTable(tag:Tag) extends Table[Dict](tag,"d"){
  def dictcode = column[String]("dictcode")
  def dictname = column[String]("dictname")
  def superDictcode = column[String]("superDictcode")
  def sibOrder = column[Int]("sibOrder")
  def isleaf = column[String]("isleaf")
  def maintFlag = column[Int]("maintFlag")
  def dictType = column[String]("dictType")
  def dictSimplePin = column[String]("dictSimplePin")
  def dictAllPin = column[String]("dictAllPin")
  def dictItemTableName = column[String]("dictItemTableName")
  def dictVersion = column[String]("dictVersion")
  def createTime = column[Option[DateTime]]("createTime")
  def id = column[Long]("dictid",O.PrimaryKey)

  def * = (
    dictcode,
      dictname,
      superDictcode,
      sibOrder,
      isleaf,
      maintFlag,
      dictType,
      dictSimplePin.?,
      dictAllPin.?,
      dictItemTableName.?,
      dictVersion.?,
      createTime,
      id.?
    ) <> (Dict.tupled,Dict.unapply)
}
