package dao.systemmanage

import models._
import scala.util.Try
import org.apache.commons.lang3.StringUtils
import com.typesafe.slick.driver.oracle.OracleDriver.profile.Implicit._
import scala.slick.lifted.{ TableQuery, CanBeQueryCondition, Query }
import com.typesafe.slick.driver.oracle.OracleDriver.simple.{Session}
import dao.BaseDao
import util.Page

/**
 * Created by hooxin on 14-2-14.
 */

object DepartmentDaoImpl extends DepartmentDaoTrait {
  val departments = TableQuery[DepartmentTable]

  def insert(d: Department)(implicit session: Session) = {
    val nd = d.copy(departid = departments.baseTableRow.sequence.next.run)
    departments += d
    nd
  }

  def update(d: Department)(implicit session: Session) = {
    val q = departments.filter(_.departid === d.departid)
    q.update(d)
  }

  def deleteById(departid: Long)(implicit session: Session) = {
    if(departid == None)
      throw new RuntimeException("departid 不能为空")
    import com.typesafe.slick.driver.oracle.OracleDriver.simple._
    val q = departments.filter(_.departid === departid).delete
  }

  def page(pageno: Int, pagesize: Int, params: Map[String, Object], dir: String, sort: String)(implicit session: Session): Page[Department] = {
    val total = count(params)
    val currentPage = new Page(pageno, pagesize)

    val datas = pageWhere(params).query.sortBy(_.departid.desc).drop(currentPage.start).take(currentPage.limit).list()
    new Page[Department](pageno, pagesize, total, datas)
  }

  def list(departid: Option[Long] = None, departname: Option[String] = None, departcode: Option[String] = None,
           departfullcode: Option[String] = None, departlevel: Option[Int] = None)(implicit session: Session) = {
    MaybeFilter(departments)
      .filter(departid)(v => d => d.departid === v)
      .filter(departname)(v => d => d.departname === v)
      .filter(departcode)(v => d => d.departcode === v)
      .filter(departfullcode)(v => d => d.departfullcode === v)
      .filter(departlevel)(v => d => d.departlevel === v).query.list()

  }

  def list(params: Map[String, Object])(implicit session: Session): scala.List[Department] = {
    List()
  }

  def count(params: Map[String, Object])(implicit session: Session): Int = {
    pageWhere(params).query.countDistinct.run
  }

  def getById(id: Long)(implicit session: Session): Department = { null }

  def delete(m: Department)(implicit session: Session): Unit = {}

  def pageWhere(params: Map[String, Object]): MaybeFilter[DepartmentTable, DepartmentTable#TableElementType] = {
    MaybeFilter(departments)
      .filter(params.get("departid"))(v => d => d.departid === v.asInstanceOf[Long])
      .filter(params.get("departname"))(v => d => d.departname === v.asInstanceOf[String])
      .filter(params.get("departcode"))(v => d => d.departcode === v.asInstanceOf[String])
      .filter(params.get("departfullcode"))(v => d => d.departfullcode === v.asInstanceOf[String])
      .filter(params.get("departlevel"))(v => d => d.departlevel === v.asInstanceOf[Int])
  }
}
