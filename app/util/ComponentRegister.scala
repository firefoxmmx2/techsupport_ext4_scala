package util

import dao.systemmanage._
import service.systemmanage._

/**
 * Created by hooxin on 14-3-16.
 */
trait ComponentRegister extends DepartmentDaoComponentImpl
with DepartmentServiceComponentImpl
with UserDaoComponentImpl
with UserServiceComponentImpl
with GlobalParamServiceComponentImpl
with GlobalParamDaoComponentImpl
with MenuServiceComponentImpl
with MenuDaoComponentImpl
with RoleServiceComponentImpl
with RoleDaoComponentImpl
with SystemDaoComponentImpl
with SystemServiceComponentImpl
with DictItemDaoComponentImpl
with DictItemServiceComponentImpl
with DictDaoComponentImpl
with DictServiceComponentImpl
with LoginLogServiceComponentImpl
with LoginLogDaoComponentImpl {

  val departmentService: DepartmentService = new DepartmentServiceImpl
  val departmentDao: DepartmentDao = new DepartmentDaoImpl
  val globalParamService: GlobalParamService = new GlobalParamServiceImpl
  val menuService: MenuService = new MenuSerivceImpl
  val roleDao: RoleDao = new RoleDaoImpl
  val systemService: SystemService = new SystemServiceImpl
  val roleService: RoleService = new RoleServiceImpl
  val systemDao: SystemDao = new SystemDaoImpl
  val userDao: UserDao = new UserDaoImpl
  val userService: UserService = new UserServiceImpl
  val globalParamDao: GlobalParamDao = new GlobalParamDaoImpl
  val menuDao: MenuDao = new MenuDaoImpl
  val dictItemDao: DictItemDao = new DictItemDaoImpl
  val dictItemService: DictItemService = new DictItemServiceImpl
  val dictDao: DictDao = new DictDaoImpl
  val dictService: DictService = new DictServiceImpl
  val loginLogDao: LoginLogDao = new LoginLogDaoImpl
  val loginLogService: LoginLogService = new LoginLogServiceImpl
}
