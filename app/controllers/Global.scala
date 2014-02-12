package controllers

import play.api.{Application, GlobalSettings}
import play.api.db.DB

object Global extends GlobalSettings{
  override def onStart(app:Application) {
    lazy val database=app.configuration.getString("db.default.driver") match {
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
}
