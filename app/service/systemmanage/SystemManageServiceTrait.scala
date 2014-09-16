package service.systemmanage

import models._
import util.Page
import dao.systemmanage.DepartmentDaoComponent
import service.BaseService
import models.Department
import models.System
import models.User
import models.Role

/**
 * Created by hooxin on 14-3-10.
 */
/**
 * 机构服务组件声明
 */
trait DepartmentServiceComponent {
  val departmentService: DepartmentService

  trait DepartmentService extends BaseService[Department, Long, DepartmentQueryCondition] {
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
    def getMaxUserOrder(departid:Long):Int

    /**
     * 检查用户帐号是否重复
      * @param useraccount
     * @return
     */
    def checkUseraccountAvailable(useraccount:String):Boolean
  }

}

/**
 * 系统服务组件声明
 */
trait SystemServiceComponent {

  trait SystemService extends BaseService[System, String, SystemQueryCondition] {
    def getUserSystems(userid: Long): List[System]
  }

  val systemService: SystemService
}

/**
 * 角色服务组件声明
 */
trait RoleServiceComponent {

  trait RoleService extends BaseService[Role, Long, RoleQueryCondition] {

  }

  val roleService: RoleService
}

/**
 * 菜单服务组件声明
 */
trait MenuServiceComponent {

  trait MenuService extends BaseService[Menu, String, MenuQueryCondition] {
    def getUserMenus(userid: Long, parentmenucode: String = "0", systemcode: Option[String] = None): List[Menu]
  }

  val menuService: MenuService
}

/**
 * 全局参数服务组件声明
 */
trait GlobalParamServiceComponent {

  trait GlobalParamService extends BaseService[GlobalParam, String, GlobalParamQueryCondition]

  val globalParamService: GlobalParamService
}

/**
 * 字典项服务组件声明
 */
trait DictItemServiceComponent {

  trait DictItemService extends BaseService[DictItem, Long, DictItemQueryCondition]

  val dictItemService: DictItemService
}

