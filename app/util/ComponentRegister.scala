package util

import dao.systemmanage._
import service.systemmanage._

/**
 * Created by hooxin on 14-3-16.
 */
object ComponentRegister extends DepartmentDaoComponentImpl
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
with DictItemServiceComponentImpl{

  val departmentService: DepartmentService = new DepartmentServiceImpl
  val departmentDao: DepartmentDao = new DepartmentDaoImpl
  val globalParamService: ComponentRegister.GlobalParamService = new GlobalParamServiceImpl
  val menuService: ComponentRegister.MenuService = new MenuSerivceImpl
  val roleDao: ComponentRegister.RoleDao = new RoleDaoImpl
  val systemService: ComponentRegister.SystemService = new SystemServiceImpl
  val roleService: ComponentRegister.RoleService = new RoleServiceImpl
  val systemDao: ComponentRegister.SystemDao = new SystemDaoImpl
  val userDao: ComponentRegister.UserDao = new UserDaoImpl
  val userService: ComponentRegister.UserService = new UserServiceImpl
  val globalParamDao: ComponentRegister.GlobalParamDao = new GlobalParamDaoImpl
  val menuDao: ComponentRegister.MenuDao = new MenuDaoImpl
  val dictItemDao: ComponentRegister.DictItemDao = new DictItemDaoImpl
  val dictItemService: ComponentRegister.DictItemService = new DictItemServiceImpl
}
