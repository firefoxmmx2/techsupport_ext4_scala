package dao

import play.api.db.DB

/**
 * Created by hooxin on 14-2-14.
 */
object Db {
  lazy val database = play.api.Play.application.configuration.getString("db.default.driver") match {
    case Some("org.h2.Driver") =>
      import scala.slick.driver.H2Driver.simple._
      Database.forDataSource(DB.getDataSource())
    case Some("org.postgresql.Driver") =>
      import scala.slick.driver.PostgresDriver.simple._
      Database.forDataSource(DB.getDataSource())
    case Some("oracle.jdbc.driver.OracleDriver") =>
      import com.typesafe.slick.driver.oracle.OracleDriver.simple._
      Database.forDataSource(DB.getDataSource())
    case _ => sys.error("数据库驱动不被支持")
  }


}
