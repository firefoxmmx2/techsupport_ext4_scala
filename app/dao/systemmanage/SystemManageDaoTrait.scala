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

  trait DepartmentDao extends BaseDao[Department, Long, DepartmentQueryCondition] {

  }

}

/**
 * 用户
 */
trait UserDaoComponent {
  val userDao: UserDao

  trait UserDao extends BaseDao[User, Long, UserQueryCondition]

}

/**
 * 系统
 */
trait SystemDaoComponent {
  val systemDao: SystemDao

  trait SystemDao extends BaseDao[System, String, SystemQueryCondition] {
    def getUserSystem(userid:Long):List[System]
  }

}

/**
 * 菜单
 */
trait MenuDaoComponent {

  trait MenuDao extends BaseDao[Menu, String, MenuQueryCondition] {
    def getUserMenus(userid: Long, parentmenucode: String = "0",systemcode:Option[String]=None):List[Menu]
  }

  val menuDao: MenuDao
}

/**
 * 角色
 */
trait RoleDaoComponent {

  trait RoleDao extends BaseDao[Role, Long, RoleQueryCondition]

  val roleDao: RoleDao
}

/**
 * 全局参数
 */
trait GlobalParamDaoComponent {

  trait GlobalParamDao extends BaseDao[GlobalParam, String, GlobalParamQueryCondition]

  val globalParamDao: GlobalParamDao
}

