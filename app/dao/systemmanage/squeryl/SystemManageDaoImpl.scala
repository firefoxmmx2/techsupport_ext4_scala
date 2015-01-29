package dao.systemmanage.squeryl

import dao.systemmanage._
import models._
import models.squeryl.systemmanage.SystemManage
import models.systemmanage._
import play.api.Logger
import util.Page

/**
 * Created by hooxin on 14-2-14.
 */

import models.squeryl.CommonTypeMode._

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
            select (d.convertTo())
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
    def getById(id: Long): Option[Department] = from(SystemManage.departments)(d =>
      where(d.id === id)
        select (models.squeryl.systemmanage.Department.convertTo(d))
    ).singleOption

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
    def update(m: Department): Unit = SystemManage.departments.update(
      models.squeryl.systemmanage.Department.convertFrom(m))

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
    def insert(m: Department): Department = SystemManage.departments.insert(models.squeryl.systemmanage.Department.convertFrom(m)).convertTo()
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
          select (u.convertTo)
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
    def getById(id: Long): Option[User] = from(SystemManage.users)(
      u =>
        where(u.id === id)
          select (u.convertTo)
    ).singleOption

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
    def update(m: User): Unit = SystemManage.users.update(models.squeryl.systemmanage.User.convertFrom(m))

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: User): User = SystemManage.users.insert(models.squeryl.systemmanage.User.convertFrom(m)).convertTo

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
  private val log = Logger(classOf[SystemDaoComponentImpl])

  class SystemDaoImpl extends SystemDao {

    /**
     * 验证系统代码是否重复
     * @return
     */
    def checkSystemcodeRepeat(systemcode: String): Boolean = from(SystemManage.systems)(
      s =>
        where(s.id === systemcode)
          select (s.id)
    ).Count > 0

    /**
     * 最大序列
     * @return
     */
    def maxOrder: Int = from(SystemManage.systems)(
      s =>
        compute(max(s.nodeorder))
    ).single.measures.getOrElse(0)

    def getUserSystem(userid: Long): List[systemmanage.System] = {
      from(SystemManage.systems, SystemManage.userRoles, SystemManage.roleMenus, SystemManage.menus)(
        (s, ur, rm, m) =>
          where(
            ur.roleid === rm.roleid
              and s.id === m.systemcode
              and rm.menucode === m.id
              and ur.userid === userid
          )
            select (s.convertTo)
      ).distinct.toList
    }


    def selectForPage(params: SystemQueryCondition, sort: String = "systemcode", dir: String = "asc") = {
      val systemnameLikeOpt = params.systemname match {
        case Some(x) => Some("%" + x + "%")
        case _ => None
      }
      val systemDeineOpt = params.systemdefine match {
        case Some(x) => Some("%" + x + "%")
        case _ => None
      }
      from(SystemManage.systems)(
        s =>
          where(
            s.id === params.id.?
              and s.isleaf === params.isleaf.?
              and s.parentsystemcode === params.parentsystemcode.?
              and (s.systemname like systemnameLikeOpt.?)
              and (s.fullcode like params.fullcode.?)
              and (s.systemdefine like systemDeineOpt.?))
            select (s.convertTo)
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
    def page(pageno: Int, pagesize: Int, params: SystemQueryCondition, sort: String, dir: String): Page[systemmanage.System] = {
      log.debug("=" * 13 + "系统列表查询" + "=" * 13)
      val page = Page[systemmanage.System](pageno, pagesize, count(params))
      log.debug("=" * 13 + "page sql is " + selectForPage(params, sort, dir).page(page.start, page.limit).statement + "=" * 13)
      if (page.total == 0)
        page
      else {
        val data = selectForPage(params, sort, dir).page(page.start, page.limit).toList
        page.copy(data = data)
      }
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: SystemQueryCondition): List[systemmanage.System] = selectForPage(params).toList

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
    def getById(id: String): Option[systemmanage.System] = from(SystemManage.systems)(
      s =>
        where(s.id === id)
          select (s.convertTo)
    ).singleOption

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
    def delete(m: systemmanage.System): Unit = SystemManage.systems.delete(m.id)

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: systemmanage.System): Unit = SystemManage.systems.update(models.squeryl.systemmanage.System.convertFrom(m))

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: systemmanage.System): systemmanage.System = SystemManage.systems.insert(models.squeryl.systemmanage.System.convertFrom(m)).convertTo
  }

}

trait RoleDaoComponentImpl extends RoleDaoComponent {

  class RoleDaoImpl extends RoleDao {
    def selectForPage(params: RoleQueryCondition, sort: String = "id", dir: String = "asc") = {
      val rolenameLike = params.rolename match {
        case Some(x) => Some("%" + x + "%")
        case None => None
      }
      val roleDescriptionLike = params.roleDescript match {
        case Some(x) => Some("%" + x + "%")
        case None => None
      }
      from(SystemManage.roles)(
        r =>
          where(
            r.id === params.id.?
              and r.departid === params.departid.?
              and (r.rolename like rolenameLike.?)
              and (r.roleType === params.roleType.?)
              and (r.roleDescription like roleDescriptionLike.?))
            select (r.convertTo)
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
    def getById(id: Long): Option[Role] = from(SystemManage.roles)(
      r =>
        where(r.id === id)
          select (r.convertTo)
    ).singleOption

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
    def update(m: Role): Unit = SystemManage.roles.update(models.squeryl.systemmanage.Role.convertForm(m))

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: Role): Role = SystemManage.roles.insert(models.squeryl.systemmanage.Role.convertForm(m)).convertTo

    /**
     * 角色名称重复验证
     * @param rolename
     * @return
     */
    def checkRolenameRepeat(rolename: String): Boolean = from(SystemManage.roles)(
      r =>
        where(r.rolename === rolename)
          select (r)
    ).Count > 0
  }

}

trait MenuDaoComponentImpl extends MenuDaoComponent {

  class MenuDaoImpl extends MenuDao {


    /**
     * 获取指定父菜单id当前的最大序列
     * @param parentId 父菜单id
     * @return
     */
    def maxOrder(parentId: String): Int =
      from(SystemManage.menus)(
        m =>
          where(m.parentmenucode === parentId)
            compute (max(m.nodeorder))
      ).single.measures.getOrElse(0)

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
            select (m.convertTo)
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
          select (m.convertTo)
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
    def getById(id: String): Option[Menu] = from(SystemManage.menus)(m =>
      where(m.id === id)
        select (m.convertTo)
    ).singleOption

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
    def update(m: Menu): Unit = SystemManage.menus.update(models.squeryl.systemmanage.Menu.convertFrom(m))

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: Menu): Menu = SystemManage.menus.insert(models.squeryl.systemmanage.Menu.convertFrom(m)).convertTo

    /**
     *
     * @param roleId
     * @return
     */
    def getRelatedMenusByRoleId(roleId: Long): List[Menu] =
      join(SystemManage.menus, SystemManage.roleMenus)(
        (m, rm) =>
          where(rm.roleid === roleId)
            select (m.convertTo)
            on (m.id === rm.menucode)
      ).toList
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
            select (g.convertTo)
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
    def getById(id: String): Option[GlobalParam] = from(SystemManage.globalParams)(g =>
      where(g.id === id)
        select (g.convertTo)
    ).singleOption

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
    def update(m: GlobalParam): Unit = SystemManage.globalParams.update(models.squeryl.systemmanage.GlobalParam.convertFrom(m))

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: GlobalParam): GlobalParam = SystemManage.globalParams.insert(models.squeryl.systemmanage.GlobalParam.convertFrom(m)).convertTo

    /**
     * 全局参数代码重复性验证
     * @param globalParamCode
     * @return
     */
    def checkGlobalParamRepeat(globalParamCode: String): Boolean =
      from(SystemManage.globalParams)(
        g =>
          where(g.id === globalParamCode)
            select (g.id)
      ).Count > 0
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
            select (ur.convertTo)
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
    def getById(id: (Long, Long)): Option[UserRole] = from(SystemManage.userRoles)(ur =>
      where {
        (ur.roleid === id._1) and (ur.userid === id._2)
      } select (ur.convertTo)).singleOption

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
    def update(m: UserRole): Unit = SystemManage.userRoles.update(models.squeryl.systemmanage.UserRole.convertForm(m))

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: UserRole): UserRole = SystemManage.userRoles.insert(models.squeryl.systemmanage.UserRole.convertForm(m)).convertTo
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
            select (rm.convertTo)
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
    def getById(id: (String, Long)): Option[RoleMenu] = from(SystemManage.roleMenus)(rm =>
      where(rm.menucode === id._1 and rm.roleid === id._2)
      select(rm.convertTo)
    ).singleOption

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
    def update(m: RoleMenu): Unit = SystemManage.roleMenus.update(models.squeryl.systemmanage.RoleMenu.convertFrom(m))

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: RoleMenu): RoleMenu = SystemManage.roleMenus.insert(models.squeryl.systemmanage.RoleMenu.convertFrom(m)).convertTo
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
              and di.superItemId === params.superItemId.?
              and di.appendValue === params.appendValue.?
              and di.displayFlag === params.displayFlag.?
              and (di.displayName like params.displayName.?
              and (di.factValue === params.queryfield.?
              or di.displayName === params.queryfield.?
              or di.dictcode === params.queryfield.?))
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
    def list(params: DictItemQueryCondition): List[DictItem] = {
      Logger.debug("=" * 13 + " 字典项非分页查询语句=" + selectForPage(params).statement + "=" * 13)
      selectForPage(params).toList
    }

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: Long): Option[DictItem] = SystemManage.dictItems.where(di =>
      di.id === id).singleOption

    /**
     * 吃否有子字典项
     * @param id
     * @return
     */
    def hasChildren(id: Long): Boolean = {
      //      Logger.debug("=" * 13 + "hasChildren sql = " + from(SystemManage.dictItems)(
      //        di =>
      //          where(
      //            di.superItemId === id
      //          )
      //            select (di.id)
      //      ).Count.statement + "=" * 13)
      from(SystemManage.dictItems)(
        di =>
          where(
            di.superItemId === id
          )
            select (di.id)
      ).Count > 0
    }

    /**
     * 获取指定ID下的最大序列
     * @param dictcode
     * @param id
     * @return
     */
    def maxOrder(dictcode: String, id: Long): Int = from(SystemManage.dictItems)(
      di =>
        where(
          di.superItemId === id
            and di.dictcode === dictcode
        )
          compute (max(di.sibOrder))
    ).single.measures.getOrElse(0)
  }

}

trait DictDaoComponentImpl extends DictDaoComponent {

  class DictDaoImpl extends DictDao {
    def selectForPage(params: DictQueryCondition, sort: String = "sibOrder", dir: String = "asc") = {
      val dictnameLike = params.dictname match {
        case Some(x) => Some("%" + x + "%")
        case _ => None
      }
      from(SystemManage.dicts)(
        d =>
          where(
            d.id === params.id.? and
              d.dictcode === params.dictcode.?
              and (d.dictname like dictnameLike.?)
              and d.superDictcode === params.superDictcode.?
              and d.createTime === params.createTime.?
              and (d.dictcode === params.queryfield.?
              or d.dictname === params.queryfield.?
              or d.dictAllPin === params.queryfield.?
              or d.dictSimplePin === params.queryfield.?
              )
          )
            select (d)
            orderBy {
            if (sort == "id")
              if (dir == "asc")
                d.id asc
              else
                d.id desc
            else if (sort == "dictcode")
              if (dir == "asc")
                d.dictcode asc
              else
                d.dictcode desc
            else if (sort == "dictname")
              if (dir == "asc")
                d.dictname asc
              else
                d.dictname desc
            else if (sort == "superDictcode")
              if (dir == "asc")
                d.superDictcode asc
              else
                d.superDictcode desc
            else if (sort == "createTime")
              if (dir == "asc")
                d.createTime asc
              else
                d.createTime desc
            else
              d.sibOrder asc
          }
      )
    }

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: Dict): Dict = SystemManage.dicts.insert(m)

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: DictQueryCondition): Int = selectForPage(params).Count.toInt

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: Dict): Unit = SystemManage.dicts.update(m)

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: Dict): Unit = SystemManage.dicts.delete(m.id)

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: Long): Unit = SystemManage.dicts.deleteWhere(d => d.id === id)

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: DictQueryCondition, sort: String, dir: String): Page[Dict] = {
      val page = Page[Dict](pageno, pagesize, count(params))
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
    def list(params: DictQueryCondition): List[Dict] = selectForPage(params).toList

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: Long): Option[Dict] = SystemManage.dicts.where(d => d.id === id).singleOption

    /**
     * 验证字典代码是否重复
     * @param dictcode
     * @return
     */
    def checkDictcodeRepeat(dictcode: String): Boolean =
      from(SystemManage.dicts)(
        d =>
          where(d.dictcode === dictcode)
            select (d.id)
      ).Count.toInt > 0

    /**
     * 获取最大序号(不分级)
     * @return
     */
    def maxOrder: Int = from(SystemManage.dicts)(
      d =>
        compute(max(d.sibOrder))
    ).single.measures.getOrElse(0)
  }

}

/**
 * 登录日志dao组件实现
 */
trait LoginLogDaoComponentImpl extends LoginLogDaoComponent {

  /**
   * 登录日志dao
   */
  class LoginLogDaoImpl extends LoginLogDao {
    def selectForPage(params: LoginLogQueryCondition, sort: String = "logintiime", dir: String = "desc") = {
      val loginUnitNameLike = params.loginunitname match {
        case Some(x) => Some("%" + x + "%")
        case _ => None
      }
      from(SystemManage.loginLogs)(
        l =>
          where(
            l.id === params.id.?
              and l.loginip === params.loginip.?
              and l.loginmac === params.loginip.?
              and l.logintiime >= params.logintiimeStart.?
              and l.logintiime <= params.logintiimeEnd.?
              and l.quittime >= params.quittimeStart.?
              and l.quittime <= params.quittimeEnd.?
              and l.loginuserid === params.loginuserid.?
              and l.useraccount === params.useraccount.?
              and l.username === params.username.?
              and l.loginunitcode === params.loginunitcode.?
              and (l.loginunitname like loginUnitNameLike.?)
          )
            select (l)
            orderBy {
            if (sort == "id")
              if (dir == "asc")
                l.id asc
              else l.id desc
            else if (sort == "loginip")
              if (dir == "asc")
                l.loginip asc
              else l.loginip desc
            else if (sort == "loginmac")
              if (dir == "asc")
                l.loginmac asc
              else l.loginmac desc
            else if (sort == "logintiime")
              if (dir == "asc")
                l.logintiime asc
              else l.logintiime desc
            else if (sort == "logintiime")
              if (dir == "asc")
                l.logintiime asc
              else l.logintiime desc
            else if (sort == "quittime")
              if (dir == "asc")
                l.quittime asc
              else l.quittime desc
            else if (sort == "quittime")
              if (dir == "asc")
                l.quittime asc
              else l.quittime desc
            else if (sort == "loginuserid")
              if (dir == "asc")
                l.loginuserid asc
              else l.loginuserid desc
            else if (sort == "useraccount")
              if (dir == "asc")
                l.useraccount asc
              else l.useraccount desc
            else if (sort == "username")
              if (dir == "asc")
                l.username asc
              else l.username desc
            else if (sort == "loginunitcode")
              if (dir == "asc")
                l.loginunitcode asc
              else l.loginunitcode desc
            else if (sort == "loginunitname")
              if (dir == "asc")
                l.loginunitname asc
              else l.loginunitname desc
            else
              l.logintiime desc
          }
      )
    }


    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: LoginLog): LoginLog = SystemManage.loginLogs.insert(m)

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: LoginLogQueryCondition): Int = selectForPage(params).Count.toInt

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: LoginLog): Unit = SystemManage.loginLogs.update(m)

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: LoginLog): Unit = SystemManage.loginLogs.delete(m.id)

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: Long): Unit = SystemManage.loginLogs.deleteWhere(l => l.id === id)

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: LoginLogQueryCondition, sort: String, dir: String): Page[LoginLog] = {
      val page = Page[LoginLog](pageno, pagesize, count(params))
      if (page.total == 0)
        page
      else {
        page.copy(data = selectForPage(params, sort, dir).page(page.start, page.limit).toList)
      }
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: LoginLogQueryCondition): List[LoginLog] = selectForPage(params).toList

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: Long): Option[LoginLog] = SystemManage.loginLogs.where(l => l.id === id).singleOption
  }

}

trait RoleFuncDaoComponentImpl extends RoleFuncDaoComponent {

  class RoleFuncDaoImpl extends RoleFuncDao {
    def selectForPage(params: RoleFuncQueryCondition, sort: String = "roleid", dir: String = "asc") = {
      from(SystemManage.roleFuncs)(
        rf =>
          where(
            rf.roleId === params.roleId.?
              and rf.funccode === params.funccode.?
          )
            select (rf)
            orderBy {
            if (sort == "roleId")
              if (dir == "asc")
                rf.roleId asc
              else
                rf.roleId desc
            else if (sort == "funccode")
              if (dir == "asc")
                rf.funccode asc
              else
                rf.funccode desc
            else
              rf.roleId asc
          }
      )
    }

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: RoleFunc): RoleFunc = SystemManage.roleFuncs.insert(m)

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: RoleFuncQueryCondition): Int = selectForPage(params).Count.toInt

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: RoleFunc): Unit = SystemManage.roleFuncs.update(m)

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: RoleFunc): Unit = SystemManage.roleFuncs.delete(m.id)

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: (Long, String)): Unit = SystemManage.roleFuncs.deleteWhere(rf => rf.id === id)

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: RoleFuncQueryCondition, sort: String, dir: String): Page[RoleFunc] = {
      val pager = Page[RoleFunc](pageno, pagesize, count(params))
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
    def list(params: RoleFuncQueryCondition): List[RoleFunc] = selectForPage(params).toList

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: (Long, String)): Option[RoleFunc] = SystemManage.roleFuncs.where(rf => rf.id === id).singleOption
  }

}

/**
 * 功能组件实现
 */
trait FunctionDaoComponentImpl extends FunctionDaoComponent {

  /**
   * 功能Dao实现
   */
  class FunctionDaoImpl extends FunctionDao {
    def selectForPage(params: FunctionQueryCondition, sort: String = "id", dir: String = "asc") = {
      from(SystemManage.functions)(
        f =>
          where(
            f.id === params.id.?
              and f.systemcode === params.systemcode.?
              and f.funcname === params.funcname.?
              and f.funcdefine === params.funcdefine.?
              and f.functype === params.functype.?
          )
            select (f)
            orderBy {
            if (sort == "id") {
              if (dir == "asc")
                f.id asc
              else
                f.id desc
            }
            else if (sort == "systemcode") {
              if (dir == "asc")
                f.systemcode asc
              else
                f.systemcode desc
            }
            else if (sort == "funcname") {
              if (dir == "asc")
                f.funcname asc
              else
                f.funcname desc
            }
            else if (sort == "funcdefine") {
              if (dir == "asc")
                f.funcdefine asc
              else
                f.funcdefine desc
            }
            else if (sort == "functype") {
              if (dir == "asc")
                f.functype asc
              else
                f.functype desc
            }
            else
              f.id asc
          }
      )
    }

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: Function): Function = SystemManage.functions.insert(m)

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: FunctionQueryCondition): Int = selectForPage(params).Count.toInt

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: Function): Unit = SystemManage.functions.update(m)

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: Function): Unit = SystemManage.functions.delete(m.id)

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: String): Unit = SystemManage.functions.deleteWhere(f => f.id === id)

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: FunctionQueryCondition, sort: String, dir: String): Page[Function] = {
      val page = Page[Function](pageno, pagesize, count(params))
      if (page.total == 0)
        page
      else {
        page.copy(data = selectForPage(params, sort, dir).page(page.start, page.limit).toList)
      }
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: FunctionQueryCondition): List[Function] = selectForPage(params).toList

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: String): Option[Function] = SystemManage.functions.where(f => f.id === id).singleOption

    /**
     * 验证ID是否重复
     * @param id
     */
    def checkIDRepeat(id: String): Boolean = from(SystemManage.functions)(
      f =>
        where(f.id === id)
          select (f.id)
    ).Count > 0

    /**
     * 通过角色ID获取关联的功能信息
     */
    def getRelatedFunctionsByRoleid(roleId: Long): List[Function] = {
      Logger.debug("通过角色ID获取关联的功能信息语句=====")
      join(SystemManage.functions, SystemManage.roleFuncs)(
        (f, rf) =>
          where(rf.roleId === roleId)
            select (f)
            on (f.id === rf.funccode)
      ).toList
    }
  }

}