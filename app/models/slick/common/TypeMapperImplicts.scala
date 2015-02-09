package models.slick.common

import com.github.tototoshi.slick.GenericJodaSupport
import com.typesafe.slick.driver.oracle.OracleDriver
/**
 * Created by hooxin on 15-2-9.
 */
object TypeMapperImplicts {

}

/**
 * 使用tototoshi的joda GenericJodaSupport 处理Oracle
 */
object OracleJodaSupport extends GenericJodaSupport(OracleDriver)
