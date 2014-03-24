import org.squeryl.adapters.{H2Adapter, OracleAdapter}
import org.squeryl.internals.DatabaseAdapter
import org.squeryl.{Session, SessionFactory}
import play.api.{Application, GlobalSettings}
import play.api.db.DB
import scala.Option

object Global extends GlobalSettings {
  override def onStart(app: Application) {
    SessionFactory.concreteFactory = app.configuration.getString("db.default.driver") match {
      case Some("org.h2.driver.H2Driver") => Some(() => getSession(new H2Adapter, app))
      case Some("oracle.jdbc.driver.OracleDriver") => Some(() => getSession(new OracleAdapter, app))
      case _ => sys.error("不支持指定的数据库")
    }
  }

  def getSession(adapter: DatabaseAdapter, app: Application) = Session.create(DB.getConnection()(app), adapter)
}
