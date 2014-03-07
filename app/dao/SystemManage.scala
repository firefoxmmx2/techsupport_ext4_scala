package dao

import models._
import scala.util.Try
import org.apache.commons.lang3.StringUtils
import com.typesafe.slick.driver.oracle.OracleDriver.profile.Implicit._
import scala.slick.lifted.{TableQuery, CanBeQueryCondition, Query}

/**
 * Created by hooxin on 14-2-14.
 */

object Departments {
  val departments = TableQuery[DepartmentTable]

  def insert(d: Department)(implicit session:Session) = Try {
    require(StringUtils.length(d.departcode) <= 12, "机构代码长度最大为12")
    require(StringUtils.length(d.departname) <= 20, "机构名称长度最大为20")
    require(StringUtils.length(d.departfullcode) <= 100, "机构全码最大长度为100")

    val nd = d.copy(departid = departments.baseTableRow.sequence.next.toString().toLong)
    //        val nd = d.copy(departid = for (s <- departments.baseTableRow.sequence) yield departments.baseTableRow.sequence.next)
    departments += d
    nd
  }

  def update(d: Department)(implicit session:Session) = Try {
    val q = departments.filter(_.departid === d.departid)
    q.update(d)

  }

  def deleteById(departid: Long) = Try {
    require(departid == None, "被删除机构的机构id不能为空")
    require(departid == 0, "被删除机构的机构id必须大于0")

    val q = departments.filter(_.departid === departid)
  }

  def page(start: Int, limit: Int, params: Map[String, Object], dir: String, sort: String) = Try {

  }

  def list(departid: Option[Long] = None, departname: Option[String] = None, departcode: Option[String] = None,
           departfullcode: Option[String] = None, departlevel: Option[Int] = None) = Try {
    MaybeFilter(departments)
      .filter(departid)(v => d => d.departid === v)
      .filter(departname)(v => d => d.departname === v)
      .filter(departcode)(v => d => d.departcode === v)
      .filter(departfullcode)(v => d => d.departfullcode === v)
      .filter(departlevel)(v => d => d.departlevel === v).query.list()

  }


}
