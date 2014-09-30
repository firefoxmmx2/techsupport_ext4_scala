package dao.systemmanage

import models._
import util.Page
import models.Role
import models.Department
import models.Menu
import models.User
import models.System
import play.api.Logger

/**
 * Created by hooxin on 14-2-14.
 */

import org.squeryl._
import org.squeryl.dsl._
import models.CommonTypeMode._

trait DepartmentDaoComponentImpl extends DepartmentDaoComponent {

  class DepartmentDaoImpl extends DepartmentDao {
    def selectForListQuery(params: DepartmentQueryCondition, sort: String = "nodeOrder", dir: String = "asc") =
      from(SystemManage.departments)(
        d =>
          where(d.id === params.id.?
            and d.departcode === params.departcode.?
            and (d.departname like params.departname.?)
            and (d.departfullcode like params.departfullcode.?)
            and d.departlevel === params.departlevel.?
            and d.parentDepartid === params.parentDepartid.?)
            select (d)
            orderBy {
            if (sort == "nodeOrder")
              if (dir == "asc")
                d.nodeOrder asc
              else
                d.nodeOrder desc
            else if (sort == "departcode")
              if (dir == "asc")
                d.departcode asc
              else
                d.departcode desc
            else if (sort == "departcode")
              if (dir == "asc")
                d.departcode asc
              else
                d.departcode desc
            else if (sort == "departlevel")
              if (dir == "asc")
                d.departlevel asc
              else
                d.departlevel desc
            else if (sort == "departfullcode")
              if (dir == "asc")
                d.departfullcode asc
              else
                d.departfullcode desc
            else if (sort == "parentDepartid")
              if (dir == "asc")
                d.parentDepartid asc
              else
                d.parentDepartid desc
            else if (sort == "isLeaf")
              if (dir == "asc")
                d.isLeaf asc
              else
                d.isLeaf desc
            else if (sort == "departsimplepin")
              if (dir == "asc")
                d.departsimplepin asc
              else
                d.departsimplepin desc
            else if (sort == "departallpin")
              if (dir == "asc")
                d.departallpin asc
              else
                d.departallpin desc
            else if (sort == "departbrevitycode")
              if (dir == "asc")
                d.departbrevitycode asc
              else
                d.departbrevitycode desc
            else if (sort == "id")
              if (dir == "asc")
                d.id asc
              else
                d.id desc
            else d.nodeOrder asc
          }
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
    def page(pageno: Int, pagesize: Int, params: DepartmentQueryCondition, sort: String = "nodeOrder", dir: String = "asc"): Page[Department] = {
      val page = new Page[Department](pageno, pagesize, count(params))
      if (page.total == 0)
        page
      else {
        //        println("=" * 13 + selectForListQueryTest(params, sort, dir).page(page.start, page.limit).statement + "=" * 13)
        val datas = selectForListQuery(params, sort, dir).page(page.start, page.limit).toList
        //        page
        page.copy(data = datas)
      }
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: DepartmentQueryCondition): List[Department] = selectForListQuery(params)
      .toList

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: DepartmentQueryCondition): Int = selectForListQuery(params).Count.toInt

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: Long): Department = from(SystemManage.departments)(d =>
      where(d.id === id)
        select (d)
    ).single

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: Long): Unit = SystemManage.departments.deleteWhere(d => d.id === id)

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
     * 获取机构制定上级机构下最大序号
     * @param parentdepartid 上级机构id
     * @return
     */
    def maxDepartmentOrder(parentdepartid: Long): Int =
      from(SystemManage.departments)(
        d =>
          where(d.parentDepartid === parentdepartid)
            compute (max(d.nodeOrder))
      ).single.measures.getOrElse(0)

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: Department): Department = SystemManage.departments.insert(m)
  }

}

trait UserDaoComponentImpl extends UserDaoComponent {

  class UserDaoImpl extends UserDao {
    def selectForPage(params: UserQueryCondition, sort: String = "id", dir: String = "asc") = {
      from(SystemManage.users)(
        u => where(
          u.id === params.id.?
            and (u.userType like params.userType.?)
            and u.useraccount === params.useraccount.?
            and u.departid === params.departid.?
            and u.isValid === params.isValid.?
            and u.mobilePhone === params.mobilePhone.?
            and u.idnum === params.idnum.?
            and u.userorder === params.userorder.?
            and u.password === params.password.?)
          select (u)
          orderBy {
          if (sort == "id")
            if (dir == "asc")
              u.id asc
            else
              u.id desc
          else if (sort == "departid")
            if (dir == "asc")
              u.departid asc
            else
              u.departid desc
          else if (sort == "useraccount")
            if (dir == "asc")
              u.useraccount asc
            else
              u.useraccount desc
          else if (sort == "username")
            if (dir == "asc")
              u.username asc
            else
              u.username desc
          else if (sort == "password")
            if (dir == "asc")
              u.password asc
            else
              u.password desc
          else if (sort == "idnum")
            if (dir == "asc")
              u.idnum asc
            else
              u.idnum desc
          else if (sort == "mobilePhone")
            if (dir == "asc")
              u.mobilePhone asc
            else
              u.mobilePhone desc
          else if (sort == "userorder")
            if (dir == "asc")
              u.userorder asc
            else
              u.userorder desc
          else if (sort == "isValid")
            if (dir == "asc")
              u.isValid asc
            else
              u.isValid desc
          else if (sort == "userType")
            if (dir == "asc")
              u.userType asc
            else
              u.userType desc
          else if (sort == "email")
            if (dir == "asc")
              u.email asc
            else
              u.email desc
          else
            u.id asc
        }
      )
    }


    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: UserQueryCondition, sort: String, dir: String): Page[User] = {
      val page = new Page[User](pageno, pagesize, count(params))
      if (page.total == 0)
        page
      else {
        //        Logger.debug("=" * 13 + " user dao page " + "[page.start]=[" + page.start + "],[page.limit]=[" + page.limit + "]" + "=" * 13)
        Logger.debug("=" * 13 + selectForPage(params, sort, dir).page(page.start, page.limit).statement + "=" * 13)
        val datas = selectForPage(params, sort, dir).page(page.start, page.limit).toList
        page.copy(data = datas)
      }
    }


    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: UserQueryCondition): List[User] = selectForPage(params).toList

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: UserQueryCondition): Int = selectForPage(params).Count.toInt

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: Long): User = SystemManage.users.where(u => u.id === id).single

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: Long): Unit = SystemManage.users.deleteWhere(u => u.id === id)

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: User): Unit = SystemManage.users.delete(m.id)

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: User): Unit = SystemManage.users.update(m)

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: User): User = SystemManage.users.insert(m)

    /**
     * 获取指定机构上最大的用户序列
     * @param departid
     * @return
     */
    def maxUserOrder(departid: Long): Int = from(SystemManage.users)(
      u =>
        where(u.departid === departid)
          compute (max(u.userorder))
    ).single.measures.getOrElse(0)
  }

}


trait SystemDaoComponentImpl extends SystemDaoComponent {

  class SystemDaoImpl extends SystemDao {

    def getUserSystem(userid: Long): List[System] = {
      from(SystemManage.systems, SystemManage.userRoles, SystemManage.roleMenus, SystemManage.menus)(
        (s, ur, rm, m) =>
          where(
            ur.roleid === rm.roleid
              and s.id === m.systemcode
              and rm.menucode === m.id
              and ur.userid === userid
          )
            select (s)
      ).distinct.toList
    }


    def selectForPage(params: SystemQueryCondition, sort: String = "systemcode", dir: String = "asc") = {
      from(SystemManage.systems)(
        s =>
          where(
            s.id === params.id.?
              and s.isleaf === params.isleaf.?
              and s.parentsystemcode === params.parentsystemcode.?
              and (s.systemname like params.systemname.?)
              and (s.fullcode like params.fullcode.?)
              and s.systemdefine === params.systemdefine.?)
            select (s)
            orderBy {
            if (sort == "id")
              if (dir == "asc")
                s.id asc
              else
                s.id desc
            else if (sort == "systemname")
              if (dir == "asc")
                s.systemname asc
              else
                s.systemname desc
            else if (sort == "fullcode")
              if (dir == "asc")
                s.fullcode asc
              else
                s.fullcode desc
            else if (sort == "systemdefine")
              if (dir == "asc")
                s.systemdefine asc
              else
                s.systemdefine desc
            else if (sort == "parentsystemcode")
              if (dir == "asc")
                s.parentsystemcode asc
              else
                s.parentsystemcode desc
            else if (sort == "nodeorder")
              if (dir == "asc")
                s.nodeorder asc
              else
                s.nodeorder desc
            else if (sort == "isleaf")
              if (dir == "asc")
                s.isleaf asc
              else
                s.isleaf desc
            else
              s.id asc
          }
      )
    }

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: SystemQueryCondition, sort: String, dir: String): Page[System] = {
      val page = Page[System](pageno, pagesize, count(params))
      if (page.total == 0)
        page
      else {
        val datas = selectForPage(params, sort, dir).page(page.start, page.limit).toList
        page.copy(data = datas)
      }
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: SystemQueryCondition): List[System] = selectForPage(params).toList

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: SystemQueryCondition): Int = selectForPage(params).Count.toInt

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: String): System = SystemManage.systems.where(s => s.id === id).single

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: String): Unit = SystemManage.systems.deleteWhere(s => s.id === id)

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: System): Unit = SystemManage.systems.delete(m.id)

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: System): Unit = SystemManage.systems.update(m)

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: System): System = SystemManage.systems.insert(m)
  }

}

trait RoleDaoComponentImpl extends RoleDaoComponent {

  class RoleDaoImpl extends RoleDao {
    def selectForPage(params: RoleQueryCondition, sort: String = "id", dir: String = "asc") = {
      from(SystemManage.roles)(
        r =>
          where(
            r.id === params.id.?
              and r.departid === params.departid.?
              and (r.rolename like params.rolename.?)
              and (r.roleType like params.roleType.?)
              and (r.roleDescription like params.roleDescript.?))
            select (r)
            orderBy {
            if (sort == "id")
              if (dir == "asc")
                r.id asc
              else
                r.id desc
            else if (sort == "departid")
              if (dir == "asc")
                r.departid asc
              else
                r.departid desc
            else if (sort == "rolename")
              if (dir == "asc")
                r.rolename asc
              else
                r.rolename desc
            else if (sort == "roleType")
              if (dir == "asc")
                r.roleType asc
              else
                r.roleType desc
            else r.id.asc
          })
    }

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: RoleQueryCondition, sort: String, dir: String): Page[Role] = {
      val page = Page[Role](pageno, pagesize, count(params))
      if (page.total == 0)
        page
      else
        page.copy(data = selectForPage(params, sort, dir).page(page.start, page.limit).toList)
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: RoleQueryCondition): List[Role] = selectForPage(params).toList

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: RoleQueryCondition): Int = selectForPage(params).Count.toInt

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: Long): Role = SystemManage.roles.where(r => r.id === id).single

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: Long): Unit = SystemManage.roles.deleteWhere(r => r.id === id)

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: Role): Unit = SystemManage.roles.delete(m.id)

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: Role): Unit = SystemManage.roles.update(m)

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: Role): Role = SystemManage.roles.insert(m)
  }

}

trait MenuDaoComponentImpl extends MenuDaoComponent {

  class MenuDaoImpl extends MenuDao {


    /**
     * 验证菜单代码重复
     * @param menucode
     * @return
     */
    def checkMenucodeRepeat(menucode: String): Boolean = selectForPage(MenuQueryCondition(id = Some(menucode))).Count > 0

    def getUserMenus(userid: Long, parentmenucode: String = "0", systemcode: Option[String] = None): List[Menu] = {
      from(SystemManage.menus, SystemManage.userRoles, SystemManage.roleMenus)(
        (m, ur, rm) =>
          where(ur.roleid === rm.roleid
            and m.id === rm.menucode
            and ur.userid === userid
            and m.parentmenucode === parentmenucode
            and m.systemcode === systemcode.?)
            select (m)
            orderBy (m.nodeorder asc)
      ).toList
    }


    def selectForPage(params: MenuQueryCondition, sort: String = "id", dir: String = "asc") =
      from(SystemManage.menus)(
        m => where(
          m.id === params.id.?
            and (m.menuname like params.menuname.?)
            and (m.menufullcode like params.menufullcode.?)
            and m.menulevel === params.menulevel.?
            and m.parentmenucode === params.parentmenucode.?
            and m.systemcode === params.systemcode.?
            and m.isleaf === params.isleaf.?
            and (m.funcentry like params.funcentry.?))
          select (m)
          orderBy {
          if (sort == "id")
            if (dir == "asc")
              m.id asc
            else
              m.id desc
          else if (sort == "nodeorder")
            if (dir == "asc")
              m.nodeorder asc
            else
              m.nodeorder desc
          else if (sort == "menuname")
            if (dir == "asc")
              m.menuname asc
            else
              m.menuname desc
          else if (sort == "funcentry")
            if (dir == "asc")
              m.funcentry asc
            else
              m.funcentry desc
          else if (sort == "menufullcode")
            if (dir == "asc")
              m.menufullcode asc
            else
              m.menufullcode desc
          else if (sort == "isleaf")
            if (dir == "asc")
              m.isleaf asc
            else
              m.isleaf desc
          else if (sort == "systemcode")
            if (dir == "asc")
              m.systemcode asc
            else
              m.systemcode desc
          else if (sort == "menulevel")
            if (dir == "asc")
              m.menulevel asc
            else
              m.menulevel desc
          else
            m.id asc
        })

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: MenuQueryCondition, sort: String = "id", dir: String = "asc"): Page[Menu] = {
      val page = Page[Menu](pageno, pagesize, count(params))
      if (page.total == 0)
        page
      else
        page.copy(data = selectForPage(params, sort, dir).page(page.start, page.limit).toList)
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: MenuQueryCondition): List[Menu] = selectForPage(params).toList

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: MenuQueryCondition): Int = selectForPage(params).Count.toInt

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: String): Menu = SystemManage.menus.where(m => m.id === id).single

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: String): Unit = SystemManage.menus.deleteWhere(m => m.id === id)

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: Menu): Unit = SystemManage.menus.delete(m.id)

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: Menu): Unit = SystemManage.menus.update(m)

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: Menu): Menu = SystemManage.menus.insert(m)
  }

}

trait GlobalParamDaoComponentImpl extends GlobalParamDaoComponent {

  class GlobalParamDaoImpl extends GlobalParamDao {
    def selectForPage(params: GlobalParamQueryCondition, sort: String = "id", dir: String = "asc") =
      from(SystemManage.globalParams)(
        g =>
          where(
            g.id === params.id.?
              and (g.globalparname like params.globalparname.?)
              and g.globalparvalue === params.globalparvalue.?)
            select (g)
            orderBy {
            if (sort == "id")
              if (dir == "asc")
                g.id asc
              else
                g.id desc
            else if (sort == "globalparname")
              if (dir == "asc")
                g.globalparname asc
              else
                g.globalparname desc
            else if (sort == "globalparvalue")
              if (dir == "asc")
                g.globalparvalue asc
              else
                g.globalparvalue desc
            else g.id asc
          })

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: GlobalParamQueryCondition, sort: String, dir: String): Page[GlobalParam] = {
      val page = Page[GlobalParam](pageno, pagesize, count(params))
      if (page.total == 0)
        page
      else
        page.copy(data = selectForPage(params).page(page.start, page.limit).toList)
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: GlobalParamQueryCondition): List[GlobalParam] = selectForPage(params).toList

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: GlobalParamQueryCondition): Int = selectForPage(params).Count.toInt

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: String): GlobalParam = SystemManage.globalParams.where(g => g.id === id).single

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: String): Unit = SystemManage.globalParams.deleteWhere(g => g.id === id)

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: GlobalParam): Unit = SystemManage.globalParams.delete(m.id)

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: GlobalParam): Unit = SystemManage.globalParams.update(m)

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: GlobalParam): GlobalParam = SystemManage.globalParams.insert(m)
  }

}

trait UserRoleDaoComponentImpl extends UserRoleDaoComponent {

  class UserRoleDaoImpl extends UserRoleDao {
    def selectForPage(params: UserRoleQueryCondition, sort: String = "userid", dir: String = "asc") =
      from(SystemManage.userRoles)(
        ur =>
          where(
            ur.userid.? === params.userid
              and ur.roleid.? === params.roleid
          )
            select (ur)
            orderBy {
            if (sort == "userid")
              if (dir == "asc")
                ur.userid asc
              else
                ur.userid desc
            else
              ur.userid asc
          }
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
    def page(pageno: Int, pagesize: Int, params: UserRoleQueryCondition, sort: String, dir: String): Page[UserRole] = {
      val pager = Page[UserRole](pageno, pagesize, total = count(params))
      if (pager.total == 0)
        pager
      else
        pager.copy(data = selectForPage(params, sort, dir).page(pager.start, pager.limit).toList)
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: UserRoleQueryCondition): List[UserRole] = selectForPage(params).toList

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: UserRoleQueryCondition): Int = selectForPage(params).Count.toInt

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: (Long, Long)): UserRole = SystemManage.userRoles.where {
      ur => (ur.roleid === id._1) and (ur.userid === id._2)
    }.single

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: (Long, Long)): Unit = SystemManage.userRoles.deleteWhere {
      ur =>
        (ur.roleid === id._1) and (ur.userid === id._2)
    }

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: UserRole): Unit = SystemManage.userRoles.delete(m.id)

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: UserRole): Unit = SystemManage.userRoles.update(m)

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: UserRole): UserRole = SystemManage.userRoles.insert(m)
  }

}

trait RoleMenuDaoComponentImpl extends RoleMenuDaoComponent {

  class RoleMenuDaoImpl extends RoleMenuDao {
    def selectForPage(params: RoleMenuQueryCondition, sort: String = "roleid", dir: String = "menucode") =
      from(SystemManage.roleMenus)(
        rm =>
          where(
            rm.menucode.? === params.menucode
              and rm.roleid.? === params.roleid
          )
            select (rm)
            orderBy {
            if (sort == "roleid")
              if (dir == "asc")
                rm.roleid asc
              else rm.roleid desc
            else rm.roleid asc
          }
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
    def page(pageno: Int, pagesize: Int, params: RoleMenuQueryCondition, sort: String, dir: String): Page[RoleMenu] = {
      val pager = Page[RoleMenu](pageno, pagesize, count(params))
      if (pager.total == 0)
        pager
      else
        pager.copy(data = selectForPage(params, sort, dir).page(pager.start, pager.limit).toList)
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: RoleMenuQueryCondition): List[RoleMenu] = selectForPage(params).toList

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: RoleMenuQueryCondition): Int = selectForPage(params).Count.toInt

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: (String, Long)): RoleMenu = SystemManage.roleMenus.where {
      rm =>
        (rm.menucode === id._1) and (rm.roleid === id._2)
    }.single

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: (String, Long)): Unit = SystemManage.roleMenus.deleteWhere {
      rm =>
        (rm.menucode === id._1) and (rm.roleid === id._2)
    }

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: RoleMenu): Unit = SystemManage.roleMenus.delete(m.id)

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: RoleMenu): Unit = SystemManage.roleMenus.update(m)

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: RoleMenu): RoleMenu = SystemManage.roleMenus.insert(m)
  }

}

/**
 * 字典项数据访问组件实现
 */
trait DictItemDaoComponentImpl extends DictItemDaoComponent {

  class DictItemDaoImpl extends DictItemDao {

    def selectForPage(params: DictItemQueryCondition, sort: String = "id", dir: String = "asc") = {
      from(SystemManage.dictItems)(
        di =>
          where(
            di.id === params.id.?
              and di.dictcode === params.dictcode.?
              and params.factValue.? === di.factValue
              and params.appendValue.? === di.appendValue
              and (di.displayName like params.displayName.?)
            //              and params.displayFlag === di.displayFlag
          )
            select (di)
            orderBy {
            if (sort == "id")
              if (dir == "asc")
                di.id asc
              else
                di.id desc
            else if (sort == "dictcode")
              if (dir == "asc")
                di.dictcode asc
              else
                di.dictcode desc
            else if (sort == "factValue")
              if (dir == "asc")
                di.factValue asc
              else
                di.factValue desc
            else if (sort == "appendValue")
              if (dir == "asc")
                di.appendValue asc
              else
                di.appendValue desc
            else if (sort == "displayName")
              if (dir == "asc")
                di.displayName asc
              else
                di.displayName desc
            else if (sort == "sibOrder")
              if (dir == "asc")
                di.sibOrder asc
              else
                di.sibOrder desc
            else
              di.id asc
          }

      )
    }

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: DictItem): DictItem = SystemManage.dictItems.insert(m)

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: DictItemQueryCondition): Int = selectForPage(params).Count.toInt

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: DictItem): Unit = SystemManage.dictItems.update(m)

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: DictItem): Unit = SystemManage.dictItems.delete(m.id)

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: Long): Unit = SystemManage.dictItems.deleteWhere(d => d.id === id)

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: DictItemQueryCondition, sort: String, dir: String): Page[DictItem] = {
      val pager = Page[DictItem](pageno, pagesize, count(params))
      if (pager.total == 0)
        pager
      else
        pager.copy(data = selectForPage(params, sort, dir).page(pager.start, pager.limit).toList)
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: DictItemQueryCondition): List[DictItem] = selectForPage(params).toList

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: Long): DictItem = SystemManage.dictItems.where(di =>
      di.id === id).single
  }

}