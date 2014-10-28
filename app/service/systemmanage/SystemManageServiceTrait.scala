package service.systemmanage

import models._
import util.Page
import dao.systemmanage.DepartmentDaoComponent
import service.BaseService
import models.Department
import models.System
import models.User
import models.Role
import org.apache.commons.lang3.StringUtils
import org.apache.xpath.functions.FuncStartsWith

/**
 * Created by hooxin on 14-3-10.
 */
/**
 * 机构服务组件声明
 */
trait DepartmentServiceComponent {
  val departmentService: DepartmentService

  trait DepartmentService extends BaseService[Department, Long, DepartmentQueryCondition] {
    /**
     * 机构代码重复验证
     * @param departcode
     * @return
     */
    def checkDepartcodeAvailable(departcode: String): Boolean

    /**
     * 获取制定上级机构id下的最大序号
     * @param parentDepartid
     * @return
     */
    def maxDepartmentOrder(parentDepartid: Long): Int
  }

}

/**
 * 用户服务组件声明
 */
trait UserServiceComponent {
  val userService: UserService

  trait UserService extends BaseService[User, Long, UserQueryCondition] {
    /**
     * 通过用户帐号和密码获取用户信息
     * @param useraccount
     * @param password
     * @return
     */
    def getByUseraccountPassword(useraccount: String, password: String): User

    /**
     * 获取指定机构上的最大用户序列
     * @param departid 机构ID
     * @return
     */
    def getMaxUserOrder(departid: Long): Int

    /**
     * 检查用户帐号是否重复
     * @param useraccount
     * @return
     */
    def checkUseraccountAvailable(useraccount: String): Boolean
  }

}

/**
 * 系统服务组件声明
 */
trait SystemServiceComponent {

  /**
   * 系统服务
   */
  trait SystemService extends BaseService[System, String, SystemQueryCondition] {
    /**
     * 获取用户系统
     * @param userid
     * @return
     */
    def getUserSystems(userid: Long): List[System]

    /**
     * 获取最大系统序列
     * @return
     */
    def maxSystemOrder: Int

    /**
     * 系统代码可用性验证
     * @return
     */
    def checkSystemcodeAvaliable(systemcode: String): Boolean
  }

  val systemService: SystemService
}

/**
 * 角色服务组件声明
 */
trait RoleServiceComponent {

  trait RoleService extends BaseService[Role, Long, RoleQueryCondition] {
    def insert(role: Role, roleMenus: Option[List[RoleMenu]] = None, roleFuncs: Option[List[RoleFunc]] = None)
  }

  val roleService: RoleService
}

/**
 * 角色菜单
 */
trait RoleMenuServiceComponent {

  trait RoleMenuService extends BaseService[RoleMenu, (String, Long), RoleMenuQueryCondition]

  val roleMenuService: RoleMenuService
}

/**
 * 角色功能
 */
trait RoleFuncServiceComponent {

  trait RoleFuncService extends BaseService[RoleFunc, (Long, String), RoleFuncQueryCondition]

  val roleFuncService: RoleFuncService
}

/**
 * 菜单服务组件声明
 */
trait MenuServiceComponent {

  trait MenuService extends BaseService[Menu, String, MenuQueryCondition] {
    /**
     * 获取用户菜单
     * @param userid 用户id
     * @param parentmenucode 上级菜单代码
     * @param systemcode 系统代码
     * @return 菜单列表
     */
    def getUserMenus(userid: Long, parentmenucode: String = "0", systemcode: Option[String] = None): List[Menu]

    /**
     * 菜单代码可用性验证
     * @param menucode 菜单代码
     * @return
     */
    def checkMenucodeAvaliable(menucode: String): Boolean

    /**
     * 获取制定父菜单id的当前最大序列
     * @param parentId 父菜单id
     * @return
     */
    def maxMenuOrder(parentId: String): Int
  }

  val menuService: MenuService
}

/**
 * 全局参数服务组件声明
 */
trait GlobalParamServiceComponent {

  trait GlobalParamService extends BaseService[GlobalParam, String, GlobalParamQueryCondition] {
    /**
     * 全局参数代码可用性验证
     * @param id
     * @return
     */
    def checkGlobalParamAvaliable(id: String): Boolean
  }

  val globalParamService: GlobalParamService
}

/**
 * 字典项服务组件声明
 */
trait DictItemServiceComponent {

  trait DictItemService extends BaseService[DictItem, Long, DictItemQueryCondition] {
    /**
     * 获取指定id下的最大序列
     * @param dictcode
     * @param id
     * @return
     */
    def maxDictItemOrder(dictcode: String, id: Long): Int
  }

  val dictItemService: DictItemService
}

/**
 * 字典服务组件声明
 */
trait DictServiceComponent {

  trait DictService extends BaseService[Dict, Long, DictQueryCondition] {
    /**
     * 验证字典代码是否可用
     * @param dictcode
     * @return
     */
    def checkDictcodeAvaliable(dictcode: String): Boolean

    /**
     * 获取最大的字典序列
     * @return
     */
    def maxDictOrder: Int
  }

  val dictService: DictService
}

/**
 * 登录日志服务组件
 */
trait LoginLogServiceComponent {

  /**
   * 登录日志服务
   */
  trait LoginLogService extends BaseService[LoginLog, Long, LoginLogQueryCondition] {

  }

  val loginLogService: LoginLogService
}

/**
 * 功能服务组件
 */
trait FunctionServiceComponent {

  /**
   * 功能服务
   */
  trait FunctionService extends BaseService[Function, String, FunctionQueryCondition]

  val functionService: FunctionService
}