package service.systemmanage

import models._
import dao.systemmanage._
import models.Department
import models.Menu
import util.Page
import models.Role

/**
 * Created by hooxin on 14-3-10.
 */

import models.CommonTypeMode._

trait DepartmentServiceComponentImpl extends DepartmentServiceComponent {
  this: DepartmentDaoComponent =>

  class DepartmentServiceImpl extends DepartmentService {
    def getById(id: Long): Department = inTransaction(departmentDao.getById(id))

    def list(params: DepartmentQueryCondition): List[Department] = inTransaction(departmentDao.list(params))

    def page(pageno: Int = 1, pagesize: Int = 20, params: DepartmentQueryCondition, sort: String, dir: String): Page[Department] =
      inTransaction(departmentDao.page(pageno, pagesize, params, sort, dir))

    def deleteById(id: Long): Unit = inTransaction {
      departmentDao.deleteById(id)
    }

    def update(e: Department): Unit = inTransaction {
      departmentDao.update(e)
    }

    def insert(e: Department): Department = inTransaction {
      departmentDao.insert(e)
    }
  }

}

trait GlobalParamServiceComponentImpl extends GlobalParamServiceComponent {
  this: GlobalParamDaoComponent =>

  class GlobalParamServiceImpl extends GlobalParamService {
    def getById(id: String): models.GlobalParam = inTransaction(globalParamDao.getById(id))

    def list(params: GlobalParamQueryCondition): List[models.GlobalParam] = inTransaction(globalParamDao.list(params))

    def page(pageno: Int, pagesize: Int, params: GlobalParamQueryCondition, sort: String, dir: String): Page[models.GlobalParam] =
      inTransaction(globalParamDao.page(pageno, pagesize, params, sort, dir))

    def deleteById(id: String): Unit = inTransaction(globalParamDao.deleteById(id))

    def update(e: models.GlobalParam): Unit = inTransaction(globalParamDao.update(e))

    def insert(e: models.GlobalParam): models.GlobalParam = inTransaction(globalParamDao.insert(e))
  }

}

trait MenuServiceComponentImpl extends MenuServiceComponent {
  this: MenuDaoComponent =>

  class MenuSerivceImpl extends MenuService {
    def getById(id: String): Menu = inTransaction(menuDao.getById(id))

    def list(params: MenuQueryCondition): List[Menu] = inTransaction(menuDao.list(params))

    def page(pageno: Int, pagesize: Int, params: MenuQueryCondition, sort: String, dir: String): Page[Menu] =
      inTransaction(menuDao.page(pageno, pagesize, params, sort, dir))

    def deleteById(id: String): Unit = inTransaction(menuDao.deleteById(id))

    def update(e: Menu): Unit = inTransaction(menuDao.update(e))

    def insert(e: Menu): Menu = inTransaction(menuDao.insert(e))
  }

}

trait RoleServiceComponentImpl extends RoleServiceComponent {
  this: RoleDaoComponent =>

  class RoleServiceImpl extends RoleService {
    def getById(id: Long): Role = inTransaction(roleDao.getById(id))

    def list(params: RoleQueryCondition): List[Role] = inTransaction(roleDao.list(params))

    def page(pageno: Int, pagesize: Int, params: RoleQueryCondition, sort: String, dir: String): Page[Role] =
      inTransaction(roleDao.page(pageno, pagesize, params, sort, dir))

    def deleteById(id: Long): Unit = inTransaction(roleDao.deleteById(id))

    def update(e: Role): Unit = inTransaction(roleDao.update(e))

    def insert(e: Role): Role = inTransaction(roleDao.insert(e))
  }

}

trait SystemServiceComponentImpl extends SystemServiceComponent {
  this: SystemDaoComponent =>

  class SystemServiceImpl extends SystemService {
    def getById(id: String): models.System = inTransaction(systemDao.getById(id))

    def list(params: SystemQueryCondition): List[models.System] = inTransaction(systemDao.list(params))

    def page(pageno: Int, pagesize: Int, params: SystemQueryCondition, sort: String, dir: String): Page[models.System] =
      inTransaction(systemDao.page(pageno, pagesize, params, sort, dir))

    def deleteById(id: String): Unit = inTransaction(systemDao.deleteById(id))

    def update(e: models.System): Unit = inTransaction(systemDao.update(e))

    def insert(e: models.System): models.System = inTransaction(systemDao.insert(e))
  }

}

trait UserServiceComponentImpl extends UserServiceComponent {
  this: UserDaoComponent =>

  class UserServiceImpl extends UserService {
    def getById(id: Long): User = inTransaction(userDao.getById(id))

    def list(params: UserQueryCondition): List[User] = inTransaction(userDao.list(params))

    def page(pageno: Int, pagesize: Int, params: UserQueryCondition, sort: String, dir: String): Page[User] =
      inTransaction(userDao.page(pageno, pagesize, params, sort, dir))

    def deleteById(id: Long): Unit = inTransaction(userDao.deleteById(id))

    def update(e: User): Unit = inTransaction(userDao.update(e))

    def insert(e: User): User = inTransaction(userDao.insert(e))
  }

}

