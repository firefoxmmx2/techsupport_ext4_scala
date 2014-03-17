package dao.systemmanage

import models._
import com.typesafe.slick.driver.oracle.OracleDriver.profile.Implicit._
import scala.slick.lifted.TableQuery
import com.typesafe.slick.driver.oracle.OracleDriver.simple.Session
import util.Page
import models.slick.{UserTable, DepartmentTable}

/**
 * Created by hooxin on 14-2-14.
 */
/**
 * 机构dao包装实现
 */
trait DepartmentDaoComponentImpl extends DepartmentDaoComponent {

  class DepartmentDaoImpl extends DepartmentDao {
    val departments = TableQuery[DepartmentTable]

    def insert(d: Department)(implicit session: Session) = {
      val nd = if (d.departid == 0L) d.copy(departid = departments.baseTableRow.sequence.next.run) else d
      departments += d
      nd
    }

    def update(d: Department)(implicit session: Session) = {
      val q = departments.filter(_.departid === d.departid)
      q.update(d)
    }

    def deleteById(departid: Long)(implicit session: Session) = {
      if (departid == None)
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

    def getById(id: Long)(implicit session: Session): Department = {
      null
    }

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

}

/**
 * 用户dao包装实现
 */
trait UserDaoComponentImpl extends UserDaoComponent {

  /**
   * 用户dao
   */
  class UserDaoImpl extends UserDao {
    val users = TableQuery[UserTable]
    import com.typesafe.slick.driver.oracle.OracleDriver.simple._

    def pageWhere(params: Map[String, Object]): MaybeFilter[UserTable, UserTable#TableElementType] = {
      MaybeFilter(users)
        .filter(params.get("userid"))(v => u => u.userid === v.asInstanceOf[Long])
        .filter(params.get("departid"))(v => u => u.departid === v.asInstanceOf[Long])
        .filter(params.get("useraccount"))(v => u => u.useraccount === v.asInstanceOf[String])
        .filter(params.get("username"))(v => u => u.username === v.asInstanceOf[String])
        .filter(params.get("password"))(v => u => u.password === v.asInstanceOf[String])
        .filter(params.get("username"))(v => u => u.useraccount === v.asInstanceOf[String])
        .filter(params.get("idnum"))(v => u => u.idnum === v.asInstanceOf[String])
        .filter(params.get("mobilePhone"))(v => u => u.mobilePhone === v.asInstanceOf[String])
        .filter(params.get("email"))(v => u => u.email === v.asInstanceOf[String])
        .filter(params.get("isVaild"))(v => u => u.isVaild === v.asInstanceOf[String])
        .filter(params.get("userType"))(v => u => u.userType.like(v.asInstanceOf[String]))
    }

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序
     * @param session 会话
     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: Map[String, Object], sort: String, dir: String)(implicit session: Session) = {
      val total = count(params)
      val currentPage = new Page(pageno, pagesize)

      val datas = pageWhere(params).query.sortBy(_.userid.desc).drop(currentPage.start).take(currentPage.limit).list()
      new Page[User](pageno, pagesize, total, datas)
    }

    /**
     * 非分页查询
     * @param params 查询条件
     * @param session 会话
     * @return 结果列表
     */
    def list(params: Map[String, Object])(implicit session: Session) = pageWhere(params).query.list()

    /**
     * 分页总数查询
     * @param params 分页查询条件
     * @param session 会话
     * @return 结果数
     */
    def count(params: Map[String, Object])(implicit session: Session) = pageWhere(params).query.countDistinct.run

    /**
     * 通过主键获取单个实体
     * @param id 主键
     * @param session 会话
     * @return 实体
     */
    def getById(id: Long)(implicit session: Session) = users.filter(_.userid === id).first()

    /**
     * 通过主键删除
     * @param id 主键
     * @param session 会话
     * @return
     */
    def deleteById(id: Long)(implicit session: Session) = users.filter(_.userid===id).delete

    /**
     * 删除
     * @param m 实体
     * @param session 会话
     * @return
     */
    def delete(m: User)(implicit session: Session) = users.filter(_.userid == m.userid).delete

    /**
     * 修改
     * @param m 实体
     * @param session 会话
     * @return
     */
    def update(m: User)(implicit session: Session) = users.filter(_.userid === m.userid).update(m)

    /**
     * 插入
     * @param m 实体
     * @param session 会话
     * @return 插入后的实体
     */
    def insert(m: User)(implicit session: Session) = {
      val insertingUser = m.copy(userid = users.baseTableRow.sequence.next.run)
      users.insert(insertingUser)
      insertingUser
    }
  }

}

