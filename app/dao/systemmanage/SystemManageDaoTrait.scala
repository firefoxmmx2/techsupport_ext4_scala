package dao.systemmanage

import dao.BaseDao
import models._
import models.Department
import models.Menu
import models.User
import models.Role

/**
 * Created by hooxin on 14-3-10.
 */

/**
 * 机构
 */
trait DepartmentDaoComponent {
  val departmentDao: DepartmentDao

  trait DepartmentDao extends BaseDao[Department, Long] {

  }

}

/**
 * 用户
 */
trait UserDaoComponent {
  val userDao: UserDao

  trait UserDao extends BaseDao[User, Long]

}

/**
 * 系统
 */
trait SystemDaoComponent {
  val systemDao: SystemDao

  trait SystemDao extends BaseDao[System, String]

}

/**
 * 菜单
 */
trait MenuDaoComponent {

  trait MenuDao extends BaseDao[Menu, String]

  val menuDao: MenuDao
}

/**
 * 角色
 */
trait RoleDaoComponent {
  trait RoleDao extends BaseDao[Role,Long]

  val roleDao:RoleDao
}

/**
 * 全局参数
 */
trait GlobalParamDaoComponent {
  trait GlobalParamDao extends BaseDao[GlobalParam,String]

  val globalParamDao:GlobalParamDao
}

