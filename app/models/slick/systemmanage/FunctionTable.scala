package models.slick.systemmanage

import com.typesafe.slick.driver.oracle.OracleDriver.simple._
import models.systemmanage.Function

/**
 * Created by hooxin on 15-2-11.
 */
class FunctionTable(tag: Tag) extends Table[Function](tag,"t_function"){
  def id = column[String]("funccode",O.PrimaryKey)
  def systemcode = column[String]("systemcode")
  def funcname = column[String]("funcname")
  def funcdefine = column[String]("funcdefine")
  def functype = column[Long]("functype")

  def * = (
      id.?,
      systemcode,
      funcname.?,
      funcdefine.?,
      functype
    ) <> (Function.tupled,Function.unapply)
}
