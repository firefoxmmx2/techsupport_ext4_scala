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

trait DepartmentServiceComponent {
  val departmentService: DepartmentService

  trait DepartmentService extends BaseService[Department, Long, DepartmentQueryCondition] {
  }

}

trait UserServiceComponent {
  val userService: UserService

  trait UserService extends BaseService[User, Long, UserQueryCondition] {
    def getByUseraccountPassword(useraccount: String, password: String): User
  }

}

trait SystemServiceComponent {

  trait SystemService extends BaseService[System, String, SystemQueryCondition] {
    def getUserSystems(userid: Long): List[System]
  }

  val systemService: SystemService
}

trait RoleServiceComponent {

  trait RoleService extends BaseService[Role, Long, RoleQueryCondition] {

  }

  val roleService: RoleService
}

trait MenuServiceComponent {

  trait MenuService extends BaseService[Menu, String, MenuQueryCondition] {
    def getUserMenus(userid: Long, parentmenucode: String = "0", systemcode: Option[String] = None): List[Menu]
  }

  val menuService: MenuService
}

trait GlobalParamServiceComponent {

  trait GlobalParamService extends BaseService[GlobalParam, String, GlobalParamQueryCondition]

  val globalParamService: GlobalParamService
}

