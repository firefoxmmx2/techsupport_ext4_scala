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

  trait DepartmentService extends BaseService[Department, Long] {
  }

}

trait UserServiceComponent {
  val userService: UserService

  trait UserService extends BaseService[User, Long] {
  }

}

trait SystemServiceComponent {

  trait SystemService extends BaseService[System, String] {
  }

  val systemService: SystemService
}

trait RoleServiceComponent {

  trait RoleService extends BaseService[Role, Long] {

  }

  val roleService: RoleService
}

trait MenuServiceComponent {

  trait MenuService extends BaseService[Menu, String]

  val menuService: MenuService
}

trait GlobalParamServiceComponent {

  trait GlobalParamService extends BaseService[GlobalParam,String]

  val globalParamService: GlobalParamService
}

