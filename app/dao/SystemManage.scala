package dao

import models._
import scala.util.Try
import org.apache.commons.lang3.StringUtils
import com.typesafe.slick.driver.oracle.OracleDriver.profile.Implicit._

/**
 * Created by hooxin on 14-2-14.
 */

object Departments {

  def insert(d: Department) = Try {
    require(StringUtils.length(d.departcode) <= 12, "机构代码长度最大为12")
    require(StringUtils.length(d.departname) <= 20, "机构名称长度最大为20")
    require(StringUtils.length(d.departfullcode) <= 100, "机构全码最大长度为100")

    db withTransaction {
      implicit session =>
//              val nd = d.copy(departid = departments.baseTableRow.sequence.next.toString().toLong)
//        val nd = d.copy(departid = for (s <- departments.baseTableRow.sequence) yield departments.baseTableRow.sequence.next)
        departments += d
        d
    }
  }

  def update(d: Department) = Try {
    db.withTransaction {
      implicit session =>
        val q = departments.filter(_.departid === d.departid)
        q.update(d)
    }

  }

  def deleteById(departid: Long) = Try {
    require(departid == None, "被删除机构的机构id不能为空")
    require(departid == 0, "被删除机构的机构id必须大于0")

    db.withTransaction {
      implicit session =>
//        departments.filter(_.departid === departid).delete
    }
  }

  def page(start: Int, limit: Int, params: Map[String, Object], dir: String, sort: String) = Try {

  }

  def list(params: Map[String, Object]) = Try {
//    var q = if (params.get("departid") != None) departments.withFilter(_.departid === params.get("departid"))
//    q = if (params.get("departname") != None) departments.withFilter(_.departname === params.get("departname"))
//    q = if (params.get("departcode") != None) departments.withFilter(_.departcode === params.get("departcode"))
//    q = if (params.get("departlevel") != None) departments.withFilter(_.departlevel === params.get("departlevel"))
//    q = if (params.get("departfullcode") != None) departments.withFilter(_.departfullcode like (params.get("departfullcode")))
  }
}
