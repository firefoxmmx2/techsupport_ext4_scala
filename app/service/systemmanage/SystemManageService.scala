package service.systemmanage

import models._
import dao.systemmanage._
import models.Department
import models.Menu
import util.Page
import models.Role
import org.apache.commons.lang3.StringUtils
import util.Utils

/**
 * Created by hooxin on 14-3-10.
 */

import models.CommonTypeMode._

/**
 * 机构服务组件实现
 */
trait DepartmentServiceComponentImpl extends DepartmentServiceComponent {
  this: DepartmentDaoComponent =>

  /**
   * 机构服务
   */
  class DepartmentServiceImpl extends DepartmentService {
    /**
     * 获取机构通过ID
     * @param id
     * @return
     */
    def getById(id: Long): Department = inTransaction(departmentDao.getById(id))

    /**
     * 获取机构列表不分页
     * @param params
     * @return
     */
    def list(params: DepartmentQueryCondition): List[Department] = inTransaction(departmentDao.list(params))

    /**
     * 获取机构列表分页
     * @param pageno
     * @param pagesize
     * @param params
     * @param sort
     * @param dir
     * @return
     */
    def page(pageno: Int = 1, pagesize: Int = 20, params: DepartmentQueryCondition, sort: String, dir: String): Page[Department] =
      inTransaction(departmentDao.page(pageno, pagesize, params, sort, dir))

    /**
     * 删除机构通过id
     * @param id
     */
    def deleteById(id: Long): Unit = inTransaction {
      departmentDao.deleteById(id)
    }

    /**
     * 修改机构
     * @param e
     */
    def update(e: Department): Unit = inTransaction {
      departmentDao.update(e)
    }

    /**
     * 新增机构
     * @param e
     * @return
     */
    def insert(e: Department): Department = inTransaction {
      departmentDao.insert(e)
    }

    /**
     * 机构代码重复验证
     * @param departcode
     * @return
     */
    def checkDepartcodeAvailable(departcode: String): Boolean = inTransaction {
      if (StringUtils.isEmpty(departcode))
        throw new RuntimeException("机构代码重复验证的机构代码为空")
      val resultcode = departmentDao.count(DepartmentQueryCondition(departcode = Some(departcode)))
      resultcode == 0
    }

    /**
     * 获取制定上级机构id下的最大序号
     * @param parentDepartid
     * @return
     */
    def maxDepartmentOrder(parentDepartid: Long): Int = inTransaction {
      if (parentDepartid == null || parentDepartid == 0)
        throw new RuntimeException("获取最大序号的上级机构ID为空")
      departmentDao.maxDepartmentOrder(parentDepartid)
    }
  }

}

/**
 *
 * 全局参数服务组件实现
 */
trait GlobalParamServiceComponentImpl extends GlobalParamServiceComponent {
  this: GlobalParamDaoComponent =>

  /**
   * 全局参数服务
   */
  class GlobalParamServiceImpl extends GlobalParamService {
    /**
     * 获取单个通过id
     * @param id
     * @return
     */
    def getById(id: String): models.GlobalParam = inTransaction(globalParamDao.getById(id))

    /**
     * 获取列表不分页
     * @param params
     * @return
     */
    def list(params: GlobalParamQueryCondition): List[models.GlobalParam] = inTransaction(globalParamDao.list(params))

    /**
     * 获取列表分页
     * @param pageno
     * @param pagesize
     * @param params
     * @param sort
     * @param dir
     * @return
     */
    def page(pageno: Int, pagesize: Int, params: GlobalParamQueryCondition, sort: String, dir: String): Page[models.GlobalParam] =
      inTransaction(globalParamDao.page(pageno, pagesize, params, sort, dir))

    /**
     * 删除通过id
     * @param id
     */
    def deleteById(id: String): Unit = inTransaction(globalParamDao.deleteById(id))

    /**
     * 修改
     * @param e
     */
    def update(e: models.GlobalParam): Unit = inTransaction(globalParamDao.update(e))

    /**
     * 新增
     * @param e
     * @return
     */
    def insert(e: models.GlobalParam): models.GlobalParam = inTransaction(globalParamDao.insert(e))
  }

}

/**
 * 菜单服务组件实现
 */
trait MenuServiceComponentImpl extends MenuServiceComponent {
  this: MenuDaoComponent =>

  /**
   * 菜单服务
   */
  class MenuSerivceImpl extends MenuService {
    /**
     * 获取单个通过id
     * @param id
     * @return
     */
    def getById(id: String): Menu = inTransaction(menuDao.getById(id))

    /**
     * 获取列表不分页
     * @param params
     * @return
     */
    def list(params: MenuQueryCondition): List[Menu] = inTransaction(menuDao.list(params))

    /**
     * 获取列表分页
     * @param pageno
     * @param pagesize
     * @param params
     * @param sort
     * @param dir
     * @return
     */
    def page(pageno: Int, pagesize: Int, params: MenuQueryCondition, sort: String, dir: String): Page[Menu] =
      inTransaction(menuDao.page(pageno, pagesize, params, sort, dir))

    /**
     * 删除通过id
     * @param id
     */
    def deleteById(id: String): Unit = inTransaction(menuDao.deleteById(id))

    /**
     * 修改
     * @param e
     */
    def update(e: Menu): Unit = inTransaction(menuDao.update(e))

    /**
     * 新增
     * @param e
     * @return
     */
    def insert(e: Menu): Menu = inTransaction(menuDao.insert(e))

    /**
     * 获取用户菜单
     * @param userid 用户id
     * @param parentmenucode 上级菜单代码
     * @param systemcode 系统代码
     * @return 菜单列表
     */
    def getUserMenus(userid: Long, parentmenucode: String, systemcode: Option[String] = None): List[Menu] = inTransaction(
      menuDao.getUserMenus(userid, parentmenucode, systemcode)
    )

    /**
     * 菜单代码可用性验证
     * @param menucode 菜单代码
     * @return
     */
    def checkMenucodeAvaliable(menucode: String): Boolean = inTransaction(
      !menuDao.checkMenucodeRepeat(menucode)
    )
  }

}

/**
 * 角色服务组件实现
 */
trait RoleServiceComponentImpl extends RoleServiceComponent {
  this: RoleDaoComponent =>

  class RoleServiceImpl extends RoleService {
    /**
     * 获取单个通过id
     * @param id
     * @return
     */
    def getById(id: Long): Role = inTransaction(roleDao.getById(id))

    /**
     * 获取列表不分页
     * @param params
     * @return
     */
    def list(params: RoleQueryCondition): List[Role] = inTransaction(roleDao.list(params))

    /**
     * 获取列表分页
     * @param pageno
     * @param pagesize
     * @param params
     * @param sort
     * @param dir
     * @return
     */
    def page(pageno: Int, pagesize: Int, params: RoleQueryCondition, sort: String, dir: String): Page[Role] =
      inTransaction(roleDao.page(pageno, pagesize, params, sort, dir))

    /**
     * 删除通过id
     * @param id
     */
    def deleteById(id: Long): Unit = inTransaction(roleDao.deleteById(id))

    /**
     * 修改
     * @param e
     */
    def update(e: Role): Unit = inTransaction(roleDao.update(e))

    /**
     * 新增
     * @param e
     * @return
     */
    def insert(e: Role): Role = inTransaction(roleDao.insert(e))
  }

}

/**
 * 系统服务组件
 */
trait SystemServiceComponentImpl extends SystemServiceComponent {
  this: SystemDaoComponent =>

  /**
   * 系统服务
   */
  class SystemServiceImpl extends SystemService {
    /**
     * 获取用户系统
     * @param userid
     * @return
     */

    def getUserSystems(userid: Long): List[System] = inTransaction(systemDao.getUserSystem(userid))

    /**
     * 获取单个通过id
     * @param id
     * @return
     */
    def getById(id: String): models.System = inTransaction(systemDao.getById(id))

    /**
     * 获取列表不分页
     * @param params
     * @return
     */
    def list(params: SystemQueryCondition): List[models.System] = inTransaction(systemDao.list(params))

    /**
     * 获取列表分页
     * @param pageno
     * @param pagesize
     * @param params
     * @param sort
     * @param dir
     * @return
     */
    def page(pageno: Int, pagesize: Int, params: SystemQueryCondition, sort: String, dir: String): Page[models.System] =
      inTransaction(systemDao.page(pageno, pagesize, params, sort, dir))

    /**
     * 删除通过id
     * @param id
     */
    def deleteById(id: String): Unit = inTransaction(systemDao.deleteById(id))

    /**
     * 修改
     * @param e
     */
    def update(e: models.System): Unit = inTransaction(systemDao.update(e))

    /**
     * 新增
     * @param e
     * @return
     */
    def insert(e: models.System): models.System = inTransaction(systemDao.insert(e))
  }

}

/**
 * 用户服务组件
 */
trait UserServiceComponentImpl extends UserServiceComponent {
  this: UserDaoComponent =>

  /**
   * 用户服务
   */
  class UserServiceImpl extends UserService {
    /**
     * 获取单个通过id
     * @param id
     * @return
     */
    def getById(id: Long): User = inTransaction(userDao.getById(id))

    /**
     * 获取列表不分页
     * @param params
     * @return
     */
    def list(params: UserQueryCondition): List[User] = inTransaction(userDao.list(params))

    /**
     * 获取列表分页
     * @param pageno
     * @param pagesize
     * @param params
     * @param sort
     * @param dir
     * @return
     */
    def page(pageno: Int, pagesize: Int, params: UserQueryCondition, sort: String, dir: String): Page[User] =
      inTransaction(userDao.page(pageno, pagesize, params, sort, dir))

    /**
     * 删除通过id
     * @param id
     */
    def deleteById(id: Long): Unit = inTransaction(userDao.deleteById(id))

    /**
     * 修改
     * @param e
     */
    def update(e: User): Unit = inTransaction(userDao.update(e))

    /**
     * 新增
     * @param e
     * @return
     */
    def insert(e: User): User = inTransaction(userDao.insert(e))

    /**
     * 通过用户帐号和密码获取用户信息
     * @param useraccount
     * @param password
     * @return
     */
    def getByUseraccountPassword(useraccount: String, password: String): User = inTransaction {
      require(StringUtils.isNotEmpty(useraccount))
      require(StringUtils.isNotEmpty(password))


      val users = userDao.list(UserQueryCondition(useraccount = Some(useraccount), password = Some(Utils.md5(password))))
      if (users.size > 0)
        users(0)
      else
        null
    }

    /**
     * 获取指定机构上的最大用户序列
     * @param departid 机构ID
     * @return
     */
    def getMaxUserOrder(departid: Long): Int = inTransaction {
      require(departid != 0l)
      userDao.maxUserOrder(departid)
    }

    /**
     * 检查用户帐号是否重复
     * @param useraccount
     * @return
     */
    def checkUseraccountAvailable(useraccount: String): Boolean = inTransaction {
      require(StringUtils.isNotEmpty(useraccount))
      val count = userDao.count(UserQueryCondition(useraccount = Option(useraccount)))
      count == 0
    }
  }

}

/**
 * 字典项服务组件实现
 */
trait DictItemServiceComponentImpl extends DictItemServiceComponent {
  // 混入字典项数据访问组件
  this: DictItemDaoComponent =>

  /**
   * 字典项服务
   */
  class DictItemServiceImpl extends DictItemService {
    /**
     * 新增
     * @param e
     * @return
     */
    def insert(e: DictItem): DictItem = inTransaction(dictItemDao.insert(e))

    /**
     * 修改
     * @param e
     */
    def update(e: DictItem): Unit = inTransaction(dictItemDao.update(e))

    /**
     * 删除通过id
     * @param id
     */
    def deleteById(id: Long): Unit = inTransaction(dictItemDao.deleteById(id))

    /**
     * 获取列表分页
     * @param pageno
     * @param pagesize
     * @param params
     * @param sort
     * @param dir
     * @return
     */
    def page(pageno: Int, pagesize: Int, params: DictItemQueryCondition, sort: String = null, dir: String = null): Page[DictItem] =
      inTransaction(dictItemDao.page(pageno, pagesize, params, sort, dir))

    /**
     * 获取单个通过id
     * @param id
     * @return
     */
    def getById(id: Long): DictItem = inTransaction(dictItemDao.getById(id))

    /**
     * 获取列表不分页
     * @param params
     * @return
     */
    def list(params: DictItemQueryCondition): List[DictItem] = inTransaction(dictItemDao.list(params))
  }

}

