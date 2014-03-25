package models

import org.squeryl._
import org.squeryl.dsl._
import java.sql.Timestamp
import org.joda.time._

/**
 * Created by hooxin on 14-3-23.
 */
object CommonTypeMode extends PrimitiveTypeMode {
  implicit val jodaTimeTEF = new NonPrimitiveJdbcMapper[Timestamp, DateTime, TTimestamp](timestampTEF, this) {
    def convertFromJdbc(t: Timestamp) = new DateTime(t)

    def convertToJdbc(t: DateTime) = new Timestamp(t.getMillis())
  }

  implicit val optionJotaTimeTEF = new TypedExpressionFactory[Option[DateTime],TOptionTimestamp]
    with DeOptionizer[Timestamp,DateTime,TTimestamp,Option[DateTime],TOptionTimestamp]{
    val deOptionizer=jodaTimeTEF
  }
  implicit def jodaTimeToElf(s:DateTime)=jodaTimeTEF.create(s)
  implicit def optionJodaTimeToTE(s:Option[DateTime])=optionJotaTimeTEF.create(s)
}
