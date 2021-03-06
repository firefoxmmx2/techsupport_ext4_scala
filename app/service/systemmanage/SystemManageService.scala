package service.systemmanage

import java.lang.System

import models._
import dao.systemmanage._
import models.squeryl.CommonTypeMode
import models.systemmanage._
import play.api.Logger
import util.Page
import org.apache.commons.lang3.StringUtils
import util.Utils

/**
 * Created by hooxin on 14-3-10.
 */

import CommonTypeMode._

/**
 * 机构服务组件实现
 */
trait DepartmentServiceComponentImpl extends DepartmentServiceComponent {
  this: DepartmentDaoComponent =>

  /**
   * 机构服务
   */
  class DepartmentServiceImpl extends DepartmentService {
    /**
     * 获取机构通过ID
     * @param id
     * @return
     */
    def getById(id: Long): Option[Department] = inTransaction(departmentDao.getById(id))

    /**
     * 获取机构列表不分页
     * @param params
     * @return
     */
    def list(params: DepartmentQueryCondition): List[Department] = inTransaction(departmentDao.list(params))

    /**
     * 获取机构列表分页
     * @param pageno
     * @param pagesize
     * @param params
     * @param sort
     * @param dir
     * @return
     */
    def page(pageno: Int = 1, pagesize: Int = 20, params: DepartmentQueryCondition, sort: String, dir: String): Page[Department] =
      inTransaction(departmentDao.page(pageno, pagesize, params, sort, dir))

    /**
     * 删除机构通过id
     * @param id
     */
    def deleteById(id: Long): Unit = inTransaction {
      departmentDao.deleteById(id)
    }

    /**
     * 修改机构
     * @param e
     */
    def update(e: Department): Unit = inTransaction {
      departmentDao.update(e)
    }

    /**
     * 新增机构
     * @param e
     * @return
     */
    def insert(e: Department): Department = inTransaction {
      departmentDao.insert(e)
    }

    /**
     * 机构代码重复验证
     * @param departcode
     * @return
     */
    def checkDepartcodeAvailable(departcode: String): Boolean = inTransaction {
      if (StringUtils.isEmpty(departcode))
        throw new RuntimeException("机构代码重复验证的机构代码为空")
      val resultcode = departmentDao.count(DepartmentQueryCondition(departcode = Some(departcode)))
      resultcode == 0
    }

    /**
     * 获取制定上级机构id下的最大序号
     * @param parentDepartid
     * @return
     */
    def maxDepartmentOrder(parentDepartid: Long): Int = inTransaction {
      if (parentDepartid <= 0)
        throw new RuntimeException("获取最大序号的上级机构ID为空")
      departmentDao.maxDepartmentOrder(parentDepartid)
    }
  }

}

/**
 *
 * 全局参数服务组件实现
 */
trait GlobalParamServiceComponentImpl extends GlobalParamServiceComponent {
  this: GlobalParamDaoComponent =>

  /**
   * 全局参数服务
   */
  class GlobalParamServiceImpl extends GlobalParamService {

    /**
     * 全局参数代码可用性验证
     * @param id
     * @return
     */
    def checkGlobalParamAvaliable(id: String): Boolean = inTransaction {
      !globalParamDao.checkGlobalParamRepeat(id)
    }

    /**
     * 获取单个通过id
     * @param id
     * @return
     */
    def getById(id: String): Option[GlobalParam] = inTransaction(globalParamDao.getById(id))

    /**
     * 获取列表不分页
     * @param params
     * @return
     */
    def list(params: GlobalParamQueryCondition): List[GlobalParam] = inTransaction(globalParamDao.list(params))

    /**
     * 获取列表分页
     * @param pageno
     * @param pagesize
     * @param params
     * @param sort
     * @param dir
     * @return
     */
    def page(pageno: Int, pagesize: Int, params: GlobalParamQueryCondition, sort: String, dir: String): Page[GlobalParam] =
      inTransaction(globalParamDao.page(pageno, pagesize, params, sort, dir))

    /**
     * 删除通过id
     * @param id
     */
    def deleteById(id: String): Unit = inTransaction(globalParamDao.deleteById(id))

    /**
     * 修改
     * @param e
     */
    def update(e: GlobalParam): Unit = inTransaction(globalParamDao.update(e))

    /**
     * 新增
     * @param e
     * @return
     */
    def insert(e: GlobalParam): GlobalParam = inTransaction(globalParamDao.insert(e))
  }

}

/**
 * 菜单服务组件实现
 */
trait MenuServiceComponentImpl extends MenuServiceComponent {
  this: MenuDaoComponent =>

  /**
   * 菜单服务
   */
  class MenuSerivceImpl extends MenuService {
    /**
     * 获取单个通过id
     * @param id
     * @return
     */
    def getById(id: String): Option[Menu] = inTransaction(menuDao.getById(id))

    /**
     * 获取列表不分页
     * @param params
     * @return
     */
    def list(params: MenuQueryCondition): List[Menu] = inTransaction(menuDao.list(params))

    /**
     * 获取列表分页
     * @param pageno
     * @param pagesize
     * @param params
     * @param sort
     * @param dir
     * @return
     */
    def page(pageno: Int, pagesize: Int, params: MenuQueryCondition, sort: String, dir: String): Page[Menu] =
      inTransaction(menuDao.page(pageno, pagesize, params, sort, dir))

    /**
     * 删除通过id
     * @param id
     */
    def deleteById(id: String): Unit = inTransaction(menuDao.deleteById(id))

    /**
     * 修改
     * @param e
     */
    def update(e: Menu): Unit = inTransaction(menuDao.update(e))

    /**
     * 新增
     * @param e
     * @return
     */
    def insert(e: Menu): Menu = inTransaction(menuDao.insert(e))

    /**
     * 获取用户菜单
     * @param userid 用户id
     * @param parentmenucode 上级菜单代码
     * @param systemcode 系统代码
     * @return 菜单列表
     */
    def getUserMenus(userid: Long, parentmenucode: String, systemcode: Option[String] = None): List[Menu] = inTransaction(
      menuDao.getUserMenus(userid, parentmenucode, systemcode)
    )

    /**
     * 菜单代码可用性验证
     * @param menucode 菜单代码
     * @return
     */
    def checkMenucodeAvaliable(menucode: String): Boolean = inTransaction(
      !menuDao.checkMenucodeRepeat(menucode)
    )

    /**
     * 获取制定父菜单id的当前最大序列
     * @param parentId 父菜单id
     * @return
     */
    def maxMenuOrder(parentId: String): Int = inTransaction {
      menuDao.maxOrder(parentId)
    }
  }

}

/**
 * 角色服务组件实现
 */
trait RoleServiceComponentImpl extends RoleServiceComponent {
  this: RoleDaoComponent
    with RoleMenuDaoComponent
    with RoleFuncDaoComponent
    with FunctionDaoComponent
    with MenuDaoComponent =>

  class RoleServiceImpl extends RoleService {
    /**
     * 获取单个通过id
     * @param id
     * @return
     */
    def getById(id: Long): Option[Role] = inTransaction(roleDao.getById(id))

    /**
     * 获取列表不分页
     * @param params
     * @return
     */
    def list(params: RoleQueryCondition): List[Role] = inTransaction(roleDao.list(params))

    /**
     * 获取列表分页
     * @param pageno
     * @param pagesize
     * @param params
     * @param sort
     * @param dir
     * @return
     */
    def page(pageno: Int, pagesize: Int, params: RoleQueryCondition, sort: String, dir: String): Page[Role] =
      inTransaction(roleDao.page(pageno, pagesize, params, sort, dir))

    /**
     * 删除通过id
     * @param id
     */
    def deleteById(id: Long): Unit = inTransaction(roleDao.deleteById(id))

    /**
     * 修改
     * @param e
     */
    def update(e: Role): Unit = inTransaction(roleDao.update(e))

    /**
     * 新增
     * @param e
     * @return
     */
    def insert(e: Role): Role = inTransaction(roleDao.insert(e))

    /**
     * 验证角色名称可用性
     * @param rolename
     * @return
     */
    def checkRolenameAvailable(rolename: String): Boolean = inTransaction {
      !roleDao.checkRolenameRepeat(rolename)
    }

    /**
     * 关联功能
     * @param roleIds
     * @param removedFunctionIds
     * @param addedFunctionIds
     */
    def relateFunctions(roleIds: Seq[Long],
                        removedFunctionIds: Option[Seq[String]] = None,
                        addedFunctionIds: Option[Seq[String]] = None): Unit = inTransaction {
      roleIds.foreach {
        roleId =>
          removedFunctionIds match {
            case Some(functionIds) =>
              functionIds.foreach {
                funccode =>
                  roleFuncDao.delete(RoleFunc(roleId, funccode))
              }
            case None =>
          }
          addedFunctionIds match {
            case Some(functionIds) =>
              functionIds.foreach {
                funccode =>
                  roleFuncDao.getById(roleId, funccode) match {
                    case Some(roleFunc)=>
                    case None =>
                      roleFuncDao.insert(RoleFunc(roleId, funccode))
                  }
              }
            case None =>
          }
      }
    }

    /**
     * 关联菜单
     * @param roleIds
     * @param removedMenuIds
     * @param addedMenuIds
     */
    def relateMenus(roleIds: Seq[Long],
                    removedMenuIds: Option[Seq[String]],
                    addedMenuIds: Option[Seq[String]]): Unit = inTransaction {
      roleIds.foreach {
        roleId =>
          removedMenuIds match {
            case Some(menuIds) =>
              menuIds.foreach {
                menucode =>
                  roleMenuDao.delete(RoleMenu(menucode, roleId))
              }
            case None =>
          }
          addedMenuIds match {
            case Some(menuIds) =>
              menuIds.foreach {
                menucode =>
                  roleMenuDao.getById((menucode, roleId)) match {
                    case Some(roleMenu) =>
                    case None =>
                      //当为空的时候才插入新的关联
                      roleMenuDao.insert(RoleMenu(menucode, roleId))
                  }

              }
            case None =>
          }
      }
    }

    /**
     * 获取关联功能列表
     * @param roleIds
     */
    def getRelateFunctions(roleIds: Seq[Long]): List[Function] = inTransaction {
      require(roleIds != null)
      roleIds.map {
        roleId =>
          functionDao.getRelatedFunctionsByRoleid(roleId)
      }.reduceLeft {
        (x, y) =>
          x intersect y
      }
    }

    /**
     * 获取关联菜单列表
     * @param roleIds
     * @return
     */
    def getRelateMenus(roleIds: Seq[Long]): List[Menu] = inTransaction {
      require(roleIds != null && roleIds.size > 0)
      roleIds.map {
        roleId =>
          menuDao.getRelatedMenusByRoleId(roleId)
      }.reduceLeft {
        (x, y) =>
          x intersect y
      }
    }
  }

}

/**
 * 角色菜单
 */
trait RoleMenuServiceComponentImpl extends RoleMenuServiceComponent {
  this: RoleMenuDaoComponent =>

  /**
   * 角色菜单
   */
  class RoleMeneServiceImpl extends RoleMenuService {
    def insert(e: RoleMenu): RoleMenu = inTransaction {
      roleMenuDao.insert(e)
    }

    def update(e: RoleMenu): Unit = inTransaction {
      roleMenuDao.update(e)
    }

    def deleteById(id: (String, Long)): Unit = inTransaction {
      roleMenuDao.deleteById(id)
    }

    def page(pageno: Int, pagesize: Int, params: RoleMenuQueryCondition, sort: String, dir: String): Page[RoleMenu] = inTransaction {
      roleMenuDao.page(pageno, pagesize, params, sort, dir)
    }

    def getById(id: (String, Long)): Option[RoleMenu] = inTransaction {
      roleMenuDao.getById(id)
    }

    def list(params: RoleMenuQueryCondition): List[RoleMenu] = inTransaction {
      roleMenuDao.list(params)
    }
  }

}

/**
 * 角色功能
 */
trait RoleFuncServiceComponentImpl extends RoleFuncServiceComponent {
  this: RoleFuncDaoComponent =>

  /**
   * 角色功能
   */
  class RoleFuncServiceImpl extends RoleFuncService {
    def insert(e: RoleFunc): RoleFunc = inTransaction {
      roleFuncDao.insert(e)
    }

    def update(e: RoleFunc): Unit = inTransaction {
      roleFuncDao.update(e)
    }

    def deleteById(id: (Long, String)): Unit = inTransaction {
      roleFuncDao.deleteById(id)
    }

    def page(pageno: Int, pagesize: Int, params: RoleFuncQueryCondition, sort: String, dir: String): Page[RoleFunc] = inTransaction {
      roleFuncDao.page(pageno, pagesize, params, sort, dir)
    }

    def getById(id: (Long, String)): Option[RoleFunc] = inTransaction {
      roleFuncDao.getById(id)
    }

    def list(params: RoleFuncQueryCondition): List[RoleFunc] = inTransaction {
      roleFuncDao.list(params)
    }
  }

}

/**
 * 系统服务组件
 */
trait SystemServiceComponentImpl extends SystemServiceComponent {
  this: SystemDaoComponent =>

  /**
   * 系统服务
   */
  class SystemServiceImpl extends SystemService {


    /**
     * 系统代码可用性验证
     * @return
     */
    def checkSystemcodeAvaliable(systemcode: String): Boolean = inTransaction {
      !systemDao.checkSystemcodeRepeat(systemcode)
    }

    /**
     * 获取最大系统序列
     * @return
     */
    def maxSystemOrder: Int = inTransaction {
      systemDao.maxOrder
    }

    /**
     * 获取用户系统
     * @param userid
     * @return
     */

    def getUserSystems(userid: Long): List[systemmanage.System] = inTransaction(systemDao.getUserSystem(userid))

    /**
     * 获取单个通过id
     * @param id
     * @return
     */
    def getById(id: String): Option[systemmanage.System] = inTransaction(systemDao.getById(id))

    /**
     * 获取列表不分页
     * @param params
     * @return
     */
    def list(params: SystemQueryCondition): List[systemmanage.System] = inTransaction(systemDao.list(params))

    /**
     * 获取列表分页
     * @param pageno
     * @param pagesize
     * @param params
     * @param sort
     * @param dir
     * @return
     */
    def page(pageno: Int, pagesize: Int, params: SystemQueryCondition, sort: String, dir: String): Page[systemmanage.System] =
      inTransaction(systemDao.page(pageno, pagesize, params, sort, dir))

    /**
     * 删除通过id
     * @param id
     */
    def deleteById(id: String): Unit = inTransaction(systemDao.deleteById(id))

    /**
     * 修改
     * @param e
     */
    def update(e: systemmanage.System): Unit = inTransaction(systemDao.update(e))

    /**
     * 新增
     * @param e
     * @return
     */
    def insert(e: systemmanage.System): systemmanage.System = inTransaction(systemDao.insert(e))
  }

}

/**
 * 用户服务组件
 */
trait UserServiceComponentImpl extends UserServiceComponent {
  this: UserDaoComponent =>

  /**
   * 用户服务
   */
  class UserServiceImpl extends UserService {
    /**
     * 获取单个通过id
     * @param id
     * @return
     */
    def getById(id: Long): Option[User] = inTransaction(userDao.getById(id))

    /**
     * 获取列表不分页
     * @param params
     * @return
     */
    def list(params: UserQueryCondition): List[User] = inTransaction(userDao.list(params))

    /**
     * 获取列表分页
     * @param pageno
     * @param pagesize
     * @param params
     * @param sort
     * @param dir
     * @return
     */
    def page(pageno: Int, pagesize: Int, params: UserQueryCondition, sort: String, dir: String): Page[User] =
      inTransaction(userDao.page(pageno, pagesize, params, sort, dir))

    /**
     * 删除通过id
     * @param id
     */
    def deleteById(id: Long): Unit = inTransaction(userDao.deleteById(id))

    /**
     * 修改
     * @param e
     */
    def update(e: User): Unit = inTransaction(userDao.update(e))

    /**
     * 新增
     * @param e
     * @return
     */
    def insert(e: User): User = inTransaction(userDao.insert(e))

    /**
     * 通过用户帐号和密码获取用户信息
     * @param useraccount
     * @param password
     * @return
     */
    def getByUseraccountPassword(useraccount: String, password: String): User = inTransaction {
      require(StringUtils.isNotEmpty(useraccount))
      require(StringUtils.isNotEmpty(password))


      val users = userDao.list(UserQueryCondition(useraccount = Some(useraccount), password = Some(Utils.md5(password))))
      if (users.size > 0)
        users(0)
      else
        null
    }

    /**
     * 获取指定机构上的最大用户序列
     * @param departid 机构ID
     * @return
     */
    def getMaxUserOrder(departid: Long): Int = inTransaction {
      require(departid >= 0)
      userDao.maxUserOrder(departid)
    }

    /**
     * 检查用户帐号是否重复
     * @param useraccount
     * @return
     */
    def checkUseraccountAvailable(useraccount: String): Boolean = inTransaction {
      require(StringUtils.isNotEmpty(useraccount))
      val count = userDao.count(UserQueryCondition(useraccount = Option(useraccount)))
      count == 0
    }
  }

}

/**
 * 字典项服务组件实现
 */
trait DictItemServiceComponentImpl extends DictItemServiceComponent {
  // 混入字典项数据访问组件
  this: DictItemDaoComponent =>

  /**
   * 字典项服务
   */
  class DictItemServiceImpl extends DictItemService {
    /**
     * 新增
     * @param e
     * @return
     */
    def insert(e: DictItem): DictItem = inTransaction {
      val inserted = dictItemDao.insert(e)
      dictItemDao.getById(inserted.superItemId.get) match {
        case Some(parent) =>
          dictItemDao.update(parent.copy(isleaf = "N"))
        case None=>
      }
      inserted
    }

    /**
     * 修改
     * @param e
     */
    def update(e: DictItem): Unit = inTransaction(dictItemDao.update(e))

    /**
     * 删除通过id
     * @param id
     */
    def deleteById(id: Long): Unit = inTransaction {
      val children = list(DictItemQueryCondition(superItemId = Some(id)))
      children.foreach {
        child =>
          dictItemDao.deleteById(child.id.get)
      }

      dictItemDao.deleteById(id)
      dictItemDao.getById(id) match {
        case Some(thisDictItem) =>
          dictItemDao.getById(thisDictItem.superItemId.get) match {
            case Some(parent) if parent.isleaf == "N" && !dictItemDao.hasChildren(parent.id.get) =>
              dictItemDao.update(parent.copy(isleaf = "Y"))
            case None=>
          }
        case None=>
      }
    }


    /**
     * 获取列表分页
     * @param pageno
     * @param pagesize
     * @param params
     * @param sort
     * @param dir
     * @return
     */
    def page(pageno: Int, pagesize: Int, params: DictItemQueryCondition, sort: String = null, dir: String = null): Page[DictItem] =
      inTransaction(dictItemDao.page(pageno, pagesize, params, sort, dir))

    /**
     * 获取单个通过id
     * @param id
     * @return
     */
    def getById(id: Long): Option[DictItem] = inTransaction(dictItemDao.getById(id))

    /**
     * 获取列表不分页
     * @param params
     * @return
     */
    def list(params: DictItemQueryCondition): List[DictItem] = inTransaction(dictItemDao.list(params))

    /**
     * 获取指定id下的最大序列
     * @param dictcode
     * @param id
     * @return
     */
    def maxDictItemOrder(dictcode: String, id: Long): Int = inTransaction {
      if (StringUtils.isEmpty(dictcode))
        throw new RuntimeException("获取最大序列的dictcode不能为空")
      if (id <= 0)
        throw new RuntimeException("获取最大序列的id不能为空")
      dictItemDao.maxOrder(dictcode, id)
    }
  }

}

/**
 * 字典服务组件
 */
trait DictServiceComponentImpl extends DictServiceComponent {
  self: DictDaoComponent with DictItemDaoComponent =>

  class DictServiceImpl extends DictService {
    /**
     * 验证字典代码是否可用
     * @param dictcode
     * @return
     */
    def checkDictcodeAvaliable(dictcode: String): Boolean = inTransaction {
      if (StringUtils.isEmpty(dictcode))
        throw new RuntimeException("字典代码为空")
      !dictDao.checkDictcodeRepeat(dictcode)
    }

    def update(e: Dict): Unit = inTransaction {
      dictDao.update(e)
    }

    def insert(e: Dict): Dict = inTransaction {
      dictDao.insert(e)
    }

    def deleteById(id: Long): Unit = inTransaction {
      if (id <= 0)
        throw new RuntimeException("字典ID为空")
      dictDao.getById(id) match {
        case Some(x) => dictItemDao.list(DictItemQueryCondition(dictcode = Some(x.dictcode)))
          .foreach(di => dictItemDao.deleteById(di.id.get))
          dictDao.deleteById(id)
        case None=>
      }

    }

    def page(pageno: Int, pagesize: Int, params: DictQueryCondition, sort: String = "sibOrder", dir: String = "asc"): Page[Dict] = inTransaction {
      if (pageno == 0)
        throw new RuntimeException("页数不能为空")
      if (pagesize == 0)
        throw new RuntimeException("每页显示数不能为空")
      if (StringUtils.isEmpty(sort))
        throw new RuntimeException("排序列不能为空")
      if (StringUtils.isEmpty(dir))
        throw new RuntimeException("排序类型不能为空")
      dictDao.page(pageno, pagesize, params, sort, dir)
    }

    def list(params: DictQueryCondition): List[Dict] = inTransaction {
      dictDao.list(params)
    }

    def getById(id: Long): Option[Dict] = inTransaction {
      if (id <= 0)
        throw new RuntimeException("获取指定id字典,字典id为空")
      dictDao.getById(id)
    }

    /**
     * 获取最大的字典序列
     * @return
     */
    def maxDictOrder: Int = inTransaction {
      dictDao.maxOrder
    }
  }

}

/**
 * 登录日志服务组件实现
 */
trait LoginLogServiceComponentImpl extends LoginLogServiceComponent {
  this: LoginLogDaoComponent =>

  /**
   * 登录日志服务实现
   */
  class LoginLogServiceImpl extends LoginLogService {

    def insert(e: LoginLog): LoginLog = inTransaction {
      loginLogDao.insert(e)
    }

    def update(e: LoginLog): Unit = inTransaction {
      loginLogDao.update(e)
    }

    def deleteById(id: Long): Unit = inTransaction {
      loginLogDao.deleteById(id)
    }

    def page(pageno: Int, pagesize: Int, params: LoginLogQueryCondition, sort: String, dir: String): Page[LoginLog] = inTransaction {
      loginLogDao.page(pageno, pagesize, params, sort, dir)
    }

    def getById(id: Long): Option[LoginLog] = inTransaction {
      loginLogDao.getById(id)
    }

    def list(params: LoginLogQueryCondition): List[LoginLog] = inTransaction {
      loginLogDao.list(params)
    }
  }

}

/**
 * 功能服务组件
 */
trait FunctionServiceComponentImpl extends FunctionServiceComponent {
  this: FunctionDaoComponent =>

  /**
   * 功能服务实现
   */
  class FunctionServiceImpl extends FunctionService {
    def insert(e: Function): Function = inTransaction {
      functionDao.insert(e)
    }

    def update(e: Function): Unit = inTransaction {
      functionDao.update(e)
    }

    def deleteById(id: String): Unit = inTransaction {
      functionDao.deleteById(id)
    }

    def page(pageno: Int, pagesize: Int, params: FunctionQueryCondition, sort: String, dir: String): Page[Function] = inTransaction {
      functionDao.page(pageno, pagesize, params, sort, dir)
    }

    def getById(id: String): Option[Function] = inTransaction {
      functionDao.getById(id)
    }

    def list(params: FunctionQueryCondition): List[Function] = inTransaction {
      functionDao.list(params)
    }

    /**
     * 功能代码可用验证
     * @param funccode
     * @return
     */
    def checkFunccodeAvailable(funccode: String): Boolean = inTransaction {
      !functionDao.checkIDRepeat(funccode)
    }
  }

}