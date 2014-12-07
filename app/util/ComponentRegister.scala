package util

import dao.systemmanage._
import dao.techsupport.SupportTicketDaoComponentImpl
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
with LoginLogDaoComponentImpl
with FunctionDaoComponentImpl
with FunctionServiceComponentImpl
with RoleFuncDaoComponentImpl
with RoleFuncServiceComponentImpl
with RoleMenuServiceComponentImpl
with RoleMenuDaoComponentImpl
with SupportTicketDaoComponentImpl{

  val functionService: FunctionService = new FunctionServiceImpl
  val roleFuncService: RoleFuncService = new RoleFuncServiceImpl
  val functionDao: FunctionDao = new FunctionDaoImpl
  val roleMenuDao: RoleMenuDao = new RoleMenuDaoImpl
  val roleMenuService: RoleMenuService = new RoleMeneServiceImpl
  val roleFuncDao: RoleFuncDao = new RoleFuncDaoImpl
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
  val supportTicketDao: SupportTicketDao = new SupportTicketDaoImpl
}
