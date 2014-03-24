package dao.systemmanage

import models._
import util.Page
import models.Role
import models.Department
import models.Menu
import models.User
import models.System

/**
 * Created by hooxin on 14-2-14.
 */
/**
 * 机构dao包装实现
 */
//trait DepartmentDaoComponentImpl extends DepartmentDaoComponent {
//
//  class DepartmentDaoImpl extends DepartmentDao {
//    val departments = TableQuery[DepartmentTable]
//
//    def insert(d: Department)(implicit session: Session) = {
//      val nd = if (d.departid == 0L) d.copy(departid = departments.baseTableRow.sequence.next.run) else d
//      departments += d
//      nd
//    }
//
//    def update(d: Department)(implicit session: Session) = {
//      val q = departments.filter(_.departid === d.departid)
//      q.update(d)
//    }
//
//    def deleteById(departid: Long)(implicit session: Session) = {
//      if (departid == None)
//        throw new RuntimeException("departid 不能为空")
//      import com.typesafe.slick.driver.oracle.OracleDriver.simple._
//      val q = departments.filter(_.departid === departid).delete
//    }
//
//    def page(pageno: Int, pagesize: Int, params: Map[String, Object], dir: String, sort: String)(implicit session: Session): Page[Department] = {
//      val total = count(params)
//      val currentPage = new Page(pageno, pagesize)
//
//      val datas = pageWhere(params).query.sortBy(_.departid.desc).drop(currentPage.start).take(currentPage.limit).list()
//      new Page[Department](pageno, pagesize, total, datas)
//    }
//
//    def list(departid: Option[Long] = None, departname: Option[String] = None, departcode: Option[String] = None,
//             departfullcode: Option[String] = None, departlevel: Option[Int] = None)(implicit session: Session) = {
//      MaybeFilter(departments)
//        .filter(departid)(v => d => d.departid === v)
//        .filter(departname)(v => d => d.departname === v)
//        .filter(departcode)(v => d => d.departcode === v)
//        .filter(departfullcode)(v => d => d.departfullcode === v)
//        .filter(departlevel)(v => d => d.departlevel === v).query.list()
//
//    }
//
//    def list(params: Map[String, Object])(implicit session: Session): scala.List[Department] = {
//      List()
//    }
//
//    def count(params: Map[String, Object])(implicit session: Session): Int = {
//      pageWhere(params).query.countDistinct.run
//    }
//
//    def getById(id: Long)(implicit session: Session): Department = {
//      null
//    }
//
//    def delete(m: Department)(implicit session: Session): Unit = {}
//
//    def pageWhere(params: Map[String, Object]): MaybeFilter[DepartmentTable, DepartmentTable#TableElementType] = {
//      MaybeFilter(departments)
//        .filter(params.get("departid"))(v => d => d.departid === v.asInstanceOf[Long])
//        .filter(params.get("departname"))(v => d => d.departname === v.asInstanceOf[String])
//        .filter(params.get("departcode"))(v => d => d.departcode === v.asInstanceOf[String])
//        .filter(params.get("departfullcode"))(v => d => d.departfullcode === v.asInstanceOf[String])
//        .filter(params.get("departlevel"))(v => d => d.departlevel === v.asInstanceOf[Int])
//    }
//  }
//
//}
//
///**
// * 用户dao包装实现
// */
//trait UserDaoComponentImpl extends UserDaoComponent {
//
//  /**
//   * 用户dao
//   */
//  class UserDaoImpl extends UserDao {
//    val users = TableQuery[UserTable]
//
//    import com.typesafe.slick.driver.oracle.OracleDriver.simple._
//
//    def pageWhere(params: Map[String, Object]): MaybeFilter[UserTable, UserTable#TableElementType] = {
//      MaybeFilter(users)
//        .filter(params.get("userid"))(v => u => u.userid === v.asInstanceOf[Long])
//        .filter(params.get("departid"))(v => u => u.departid === v.asInstanceOf[Long])
//        .filter(params.get("useraccount"))(v => u => u.useraccount === v.asInstanceOf[String])
//        .filter(params.get("username"))(v => u => u.username === v.asInstanceOf[String])
//        .filter(params.get("password"))(v => u => u.password === v.asInstanceOf[String])
//        .filter(params.get("username"))(v => u => u.useraccount === v.asInstanceOf[String])
//        .filter(params.get("idnum"))(v => u => u.idnum === v.asInstanceOf[String])
//        .filter(params.get("mobilePhone"))(v => u => u.mobilePhone === v.asInstanceOf[String])
//        .filter(params.get("email"))(v => u => u.email === v.asInstanceOf[String])
//        .filter(params.get("isVaild"))(v => u => u.isVaild === v.asInstanceOf[String])
//        .filter(params.get("userType"))(v => u => u.userType.like(v.asInstanceOf[String]))
//    }
//
//    /**
//     * 分页查询
//     * @param pageno 页码
//     * @param pagesize 每页显示数
//     * @param params 分页查询条件
//     * @param sort 排序字段
//     * @param dir 升降序
//     * @param session 会话
//     * @return 分页结果
//     */
//    def page(pageno: Int, pagesize: Int, params: Map[String, Object], sort: String, dir: String)(implicit session: Session) = {
//      val total = count(params)
//      val currentPage = new Page(pageno, pagesize)
//
//      val datas = pageWhere(params).query.sortBy(_.userid.desc).drop(currentPage.start).take(currentPage.limit).list()
//      new Page[User](pageno, pagesize, total, datas)
//    }
//
//    /**
//     * 非分页查询
//     * @param params 查询条件
//     * @param session 会话
//     * @return 结果列表
//     */
//    def list(params: Map[String, Object])(implicit session: Session) = pageWhere(params).query.list()
//
//    /**
//     * 分页总数查询
//     * @param params 分页查询条件
//     * @param session 会话
//     * @return 结果数
//     */
//    def count(params: Map[String, Object])(implicit session: Session) = pageWhere(params).query.countDistinct.run
//
//    /**
//     * 通过主键获取单个实体
//     * @param id 主键
//     * @param session 会话
//     * @return 实体
//     */
//    def getById(id: Long)(implicit session: Session) = users.filter(_.userid === id).first()
//
//    /**
//     * 通过主键删除
//     * @param id 主键
//     * @param session 会话
//     * @return
//     */
//    def deleteById(id: Long)(implicit session: Session) = users.filter(_.userid === id).delete
//
//    /**
//     * 删除
//     * @param m 实体
//     * @param session 会话
//     * @return
//     */
//    def delete(m: User)(implicit session: Session) = users.filter(_.userid == m.userid).delete
//
//    /**
//     * 修改
//     * @param m 实体
//     * @param session 会话
//     * @return
//     */
//    def update(m: User)(implicit session: Session) = users.filter(_.userid === m.userid).update(m)
//
//    /**
//     * 插入
//     * @param m 实体
//     * @param session 会话
//     * @return 插入后的实体
//     */
//    def insert(m: User)(implicit session: Session) = {
//      val insertingUser = m.copy(userid = users.baseTableRow.sequence.next.run)
//      users.insert(insertingUser)
//      insertingUser
//    }
//  }
//
//}
//
///**
// * 系统
// */
//trait SystemDaoComponentImpl extends SystemDaoComponent {
//
//  class SystemDaoImpl extends SystemDao {
//    val systems = TableQuery[SystemTable]
//
//    import com.typesafe.slick.driver.oracle.OracleDriver.simple._
//
//    def pageWhere(params: Map[String, Object]): MaybeFilter[SystemTable, SystemTable#TableElementType] = {
//      MaybeFilter(systems).filter(params.get("systemcode"))(v => s => s.systemcode === v.asInstanceOf[String])
//        .filter(params.get("systemname"))(v => s => s.systemname === v.asInstanceOf[String])
//        .filter(params.get("systemdefine"))(v => s => s.systemdefine === v.asInstanceOf[String])
//        .filter(params.get("parentsystemcode"))(v => s => s.parentsystemcode === v.asInstanceOf[String])
//        .filter(params.get("nodeorder"))(v => s => s.nodeorder === v.asInstanceOf[Int])
//        .filter(params.get("isleaf"))(v => s => s.isleaf === v.asInstanceOf[String])
//        .filter(params.get("fullcode"))(v => s => s.fullcode like v.asInstanceOf[String])
//    }
//
//    /**
//     * 插入
//     * @param m 实体
//     * @param session 会话
//     * @return 插入后的实体
//     */
//    def insert(m: System)(implicit session: Session): System = {
//      systems += m
//      m
//    }
//
//    /**
//     * 修改
//     * @param m 实体
//     * @param session 会话
//     * @return
//     */
//    def update(m: System)(implicit session: Session): Unit = systems.filter(_.systemcode === m.systemcode).update(m)
//
//    /**
//     * 删除
//     * @param m 实体
//     * @param session 会话
//     * @return
//     */
//    def delete(m: System)(implicit session: Session): Unit = systems.filter(_.systemcode === m.systemcode).delete
//
//    /**
//     * 通过主键获取单个实体
//     * @param id 主键
//     * @param session 会话
//     * @return 实体
//     */
//    def getById(id: String)(implicit session: Session): System = systems.filter(_.systemcode === id).first()
//
//    /**
//     * 通过主键删除
//     * @param id 主键
//     * @param session 会话
//     * @return
//     */
//    def deleteById(id: String)(implicit session: Session): Unit = systems.filter(_.systemcode === id).delete
//
//    /**
//     * 分页总数查询
//     * @param params 分页查询条件
//     * @param session 会话
//     * @return 结果数
//     */
//    def count(params: Map[String, Object])(implicit session: Session): Int = pageWhere(params).query.countDistinct.run
//
//    /**
//     * 非分页查询
//     * @param params 查询条件
//     * @param session 会话
//     * @return 结果列表
//     */
//    def list(params: Map[String, Object])(implicit session: Session): List[System] = pageWhere(params).query.list()
//
//    /**
//     * 分页查询
//     * @param pageno 页码
//     * @param pagesize 每页显示数
//     * @param params 分页查询条件
//     * @param sort 排序字段
//     * @param dir 升降序
//     * @param session 会话
//     * @return 分页结果
//     */
//    def page(pageno: Int, pagesize: Int, params: Map[String, Object], sort: String, dir: String)(implicit session: Session) = {
//      val currentPage = new Page[System](pageno, pagesize, total = count(params))
//      if (currentPage.total == 0)
//        currentPage
//      else {
//        val datas = pageWhere(params).query.sortBy(_.systemcode.asc).drop(currentPage.start).take(currentPage.limit).list()
//        new Page[System](pageno, pagesize, currentPage.total, datas)
//      }
//
//    }
//  }
//
//}
//
///**
// * 菜单组件
// */
//trait MenuDaoComponentImpl extends MenuDaoComponent {
//
//  /**
//   * 菜单实现
//   */
//  trait MenuDaoImpl extends MenuDao {
//    val menus = TableQuery[MenuTable]
//
//    import com.typesafe.slick.driver.oracle.OracleDriver.simple._
//
//    def pageWhere(params: Map[String, Object], query: Query[MenuTable, Menu] = menus): MaybeFilter[MenuTable, MenuTable#TableElementType] = {
//      MaybeFilter(query).filter(params.get("menucode"))(v => m => m.menucode === v.asInstanceOf[String])
//    }
//
//    /**
//     * 分页查询
//     * @param pageno 页码
//     * @param pagesize 每页显示数
//     * @param params 分页查询条件
//     * @param sort 排序字段
//     * @param dir 升降序
//     * @param session 会话
//     * @return 分页结果
//     */
//    def page(pageno: Int, pagesize: Int, params: Map[String, Object], sort: String, dir: String)
//            (implicit session: Session): Page[Menu] = ???
//
//    /**
//     * 非分页查询
//     * @param params 查询条件
//     * @param session 会话
//     * @return 结果列表
//     */
//    def list(params: Map[String, Object])(implicit session: Session): List[Menu] = pageWhere(params).query.list()
//
//    /**
//     * 分页总数查询
//     * @param params 分页查询条件
//     * @param session 会话
//     * @return 结果数
//     */
//    def count(params: Map[String, Object])(implicit session: Session): Int = pageWhere(params).query.countDistinct.run
//
//    /**
//     * 通过主键获取单个实体
//     * @param id 主键
//     * @param session 会话
//     * @return 实体
//     */
//    def getById(id: String)(implicit session: Session): Menu = menus.filter(_.menucode === id).first()
//
//    /**
//     * 通过主键删除
//     * @param id 主键
//     * @param session 会话
//     * @return
//     */
//    def deleteById(id: String)(implicit session: Session): Unit = menus.filter(_.menucode === id).delete
//
//    /**
//     * 删除
//     * @param m 实体
//     * @param session 会话
//     * @return
//     */
//    def delete(m: Menu)(implicit session: Session): Unit = menus.filter(_.menucode === m.menucode).delete
//
//    /**
//     * 修改
//     * @param m 实体
//     * @param session 会话
//     * @return
//     */
//    def update(m: Menu)(implicit session: Session): Unit = menus.filter(_.menucode === m.menucode).update(m)
//
//    /**
//     * 插入
//     * @param m 实体
//     * @param session 会话
//     * @return 插入后的实体
//     */
//    def insert(m: Menu)(implicit session: Session): Menu = {
//      menus += m
//      m
//    }
//  }
//
//}
//
//trait RoleDaoComponentImpl extends RoleDaoComponent {
//
//  class RoleDaoImpl extends RoleDao {
//    val roles = TableQuery[RoleTable]
//
//    import com.typesafe.slick.driver.oracle.OracleDriver.simple._
//
//
//    def pageWhere(params: Map[String, Object], query: Query[RoleTable, Role] = roles) =
//      MaybeFilter(query).filter(params.get("roleid"))(v => r => r.roleid === v.asInstanceOf[Long])
//        .filter(params.get("departid"))(v => r => r.roleid === v.asInstanceOf[Long])
//        .filter(params.get("rolename"))(v => r => r.rolename like v.asInstanceOf[String])
//        .filter(params.get("roleDescription"))(v => r => r.roleDescription like v.asInstanceOf[String])
//        .filter(params.get("roleType"))(v => r => r.roleType like v.asInstanceOf[String])
//
//    /**
//     * 分页查询
//     * @param pageno 页码
//     * @param pagesize 每页显示数
//     * @param params 分页查询条件
//     * @param sort 排序字段
//     * @param dir 升降序
//     * @param session 会话
//     * @return 分页结果
//     */
//    def page(pageno: Int, pagesize: Int, params: Map[String, Object], sort: String, dir: String)
//            (implicit session: Session): Page[Role] = {
//      val page = new Page[Role](pageno, pagesize, count(params))
//      if (page.total == 0)
//        page
//      else {
//        val datas = pageWhere(params).query.sortBy(_.roleid.asc).drop(page.start)
//          .take(page.limit).list()
//        page.copy(datas = datas)
//      }
//    }
//
//    /**
//     * 非分页查询
//     * @param params 查询条件
//     * @param session 会话
//     * @return 结果列表
//     */
//    def list(params: Map[String, Object])(implicit session: Session): List[Role] = pageWhere(params).query.list()
//
//    /**
//     * 分页总数查询
//     * @param params 分页查询条件
//     * @param session 会话
//     * @return 结果数
//     */
//    def count(params: Map[String, Object])(implicit session: Session): Int =
//      pageWhere(params).query.countDistinct.run
//
//    /**
//     * 通过主键获取单个实体
//     * @param id 主键
//     * @param session 会话
//     * @return 实体
//     */
//    def getById(id: Long)(implicit session: Session): Role = roles.filter(_.roleid === id).first()
//
//    /**
//     * 通过主键删除
//     * @param id 主键
//     * @param session 会话
//     * @return
//     */
//    def deleteById(id: Long)(implicit session: Session): Unit = roles.filter(_.roleid === id).delete
//
//    /**
//     * 删除
//     * @param m 实体
//     * @param session 会话
//     * @return
//     */
//    def delete(m: Role)(implicit session: Session): Unit = roles.filter(_.roleid === m.roleid).delete
//
//    /**
//     * 修改
//     * @param m 实体
//     * @param session 会话
//     * @return
//     */
//    def update(m: Role)(implicit session: Session): Unit = roles.filter(_.roleid === m.roleid).update(m)
//
//    /**
//     * 插入
//     * @param m 实体
//     * @param session 会话
//     * @return 插入后的实体
//     */
//    def insert(m: Role)(implicit session: Session): Role = {
//      val insertingRole = m.copy(roleid = roles.baseTableRow.sequence.next.run)
//      roles.insert(insertingRole)
//      insertingRole
//    }
//  }
//
//}


import org.squeryl._
import org.squeryl.dsl._
import models.CommonTypeMode._

trait DepartmentDaoComponentImpl extends DepartmentDaoComponent {

  class DepartmentDaoImpl extends DepartmentDao {
    def selectForListQuery[T](params: Map[String, Object], sort: String = "departid", dir: String = "asc",
                           isCount: Boolean = false):Query[T] = from(SystemManage.departments)(d =>
      where {
        (d.departid === params.get("departid").asInstanceOf[d.departid.type].?)
          .and(d.departcode === params.get("departcode").asInstanceOf[d.departcode.type].?)
          .and(d.departname like params.get("departname").asInstanceOf[d.departname.type].?)
          .and(d.departfullcode like params.get("departfullcode").asInstanceOf[d.departfullcode.type].?)
          .and(d.departlevel === params.get("departlevel").asInstanceOf[Int].?)
      }
        select (if (isCount) countDistinct(d.departid).asInstanceOf[T] else d.asInstanceOf[T])
        orderBy (if (sort == "departid") if (dir == "asc") d.departid asc else d.departid desc else d.departid asc)
    )

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: Map[String, Object], sort: String = "departid", dir: String = "asc"): Page[Department] = {
      val page = new Page[Department](pageno, pagesize, count(params))
      if (page.total == 0)
        page
      else {
        val datas = selectForListQuery(params, sort, dir).page(page.start,page.limit).toList
        page.copy(datas = datas)
      }
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: Map[String, Object]): List[Department] = selectForListQuery(params)
      .toList

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: Map[String, Object]): Int = selectForListQuery(params, isCount = true).single

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: Long): Department = from(SystemManage.departments) {
      d =>
        where(d.departid === id)
        select(d)
    }.single

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: Long): Unit = SystemManage.departments.deleteWhere( d => id === d.id)

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: Department): Unit = SystemManage.departments.delete(m.id)

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: Department): Unit = SystemManage.departments.update(m)

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: Department): Department = {
      SystemManage.departments.insert(m)
    }
  }

}