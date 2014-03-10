package dao

import play.api.Play.current

/**
 * Created by hooxin on 14-2-14.
 */
object DB {

  def getDatabase(dataSource: String="default") = {
     play.api.Play.application.configuration.getString("db." + dataSource + ".driver") match {
//      case Some("org.postgresql.Driver") =>
//        import scala.slick.driver.PostgresDriver.simple._
//        Database.forDataSource(play.api.db.DB.getDataSource(dataSource))
      case Some("oracle.jdbc.driver.OracleDriver") =>
        import com.typesafe.slick.driver.oracle.OracleDriver.simple._
        Database.forDataSource(play.api.db.DB.getDataSource(dataSource))
//      case Some("org.h2.Driver") =>
//        import scala.slick.driver.H2Driver.simple._
//        Database.forDataSource(play.api.db.DB.getDataSource(dataSource))
      case _ => sys.error("数据库驱动不被支持")
    }
  }
}
