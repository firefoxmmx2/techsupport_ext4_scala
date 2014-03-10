package dao.systemmanage

import models._
import scala.util.Try
import org.apache.commons.lang3.StringUtils
import com.typesafe.slick.driver.oracle.OracleDriver.profile.Implicit._
import scala.slick.lifted.{TableQuery, CanBeQueryCondition, Query}
import com.typesafe.slick.driver.oracle.OracleDriver.simple.Session
import dao.BaseDao
import util.Page

/**
 * Created by hooxin on 14-2-14.
 */

object DepartmentDaoImpl extends DepartmentDaoTrait{
  val departments = TableQuery[DepartmentTable]

  def insert(d: Department)(implicit session:Session):Try[Department] = Try {
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

  def deleteById(departid: Long)(implicit session:Session) = Try {
    require(departid == None, "被删除机构的机构id不能为空")
    require(departid == 0, "被删除机构的机构id必须大于0")

    val q = departments.filter(_.departid === departid)
  }

  def page(pageno: Int, pagesize: Int, params: Map[String, Object], dir: String, sort: String)(implicit session:Session):Page[Department] = {
    val total = count(params)
    val currentPage = new Page(pageno,pagesize)

    val datas = pageWhere(params).query.sortBy(_.desc).drop(currentPage.start).take(currentPage.limit).list()
    new Page[Department](pageno,pagesize,total,datas)
  }

  def list(departid: Option[Long] = None, departname: Option[String] = None, departcode: Option[String] = None,
           departfullcode: Option[String] = None, departlevel: Option[Int] = None)(implicit session:Session) = Try {
    MaybeFilter(departments)
      .filter(departid)(v => d => d.departid === v)
      .filter(departname)(v => d => d.departname === v)
      .filter(departcode)(v => d => d.departcode === v)
      .filter(departfullcode)(v => d => d.departfullcode === v)
      .filter(departlevel)(v => d => d.departlevel === v).query.list()

  }

  def list(params: Map[String, Object])(implicit session:Session): scala.List[Department] = {
    List()
  }

  def count(params: Map[String, Object])(implicit session:Session): Int = {
    pageWhere(params).query.countDistinct.run
  }

  def getById(id: Long)(implicit session:Session): Department = {null}

  def delete(m: Department)(implicit session:Session): Unit = {}

  def pageWhere(params:Map[String,Object]):MaybeFilter[DepartmentTable, DepartmentTable#TableElementType] = {
    MaybeFilter(departments)
      .filter(params.get("departid"))(v => d => d.departid === v.asInstanceOf[Long])
      .filter(params.get("departname"))(v => d => d.departname === v.asInstanceOf[String])
      .filter(params.get("departcode"))(v => d => d.departcode === v.asInstanceOf[String])
      .filter(params.get("departfullcode"))(v => d => d.departfullcode === v.asInstanceOf[String])
      .filter(params.get("departlevel"))(v => d => d.departlevel === v.asInstanceOf[Int])
  }
}
