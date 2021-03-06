package dao.systemmanage

import java.lang.System

import dao.BaseDao
import models._
import models.systemmanage._
import org.squeryl.dsl.CompositeKey2

/**
 * Created by hooxin on 14-3-10.
 */

/**
 * 机构
 */
trait DepartmentDaoComponent {
  val departmentDao: DepartmentDao

  trait DepartmentDao extends BaseDao[Department, Long, DepartmentQueryCondition] {
    def maxDepartmentOrder(parentdepartid: Long): Int
  }

}

/**
 * 用户
 */
trait UserDaoComponent {
  val userDao: UserDao

  trait UserDao extends BaseDao[User, Long, UserQueryCondition] {
    /**
     * 获取指定机构上最大的用户序列
     * @param departid
     * @return
     */
    def maxUserOrder(departid: Long): Int

    /**
     * 通过用户角色ID或者角色名称,获取用户ID序列
     * @param roleId
     * @param rolename
     * @return
     */
    def userIdsByRole(roleId:Option[Long],rolename:Option[String]):Seq[Long]
  }

}

/**
 * 系统
 */
trait SystemDaoComponent {
  val systemDao: SystemDao

  trait SystemDao extends BaseDao[systemmanage.System, String, SystemQueryCondition] {
    /**
     * 获取用户所使用的系统
     * @param userid
     * @return
     */
    def getUserSystem(userid: Long): List[systemmanage.System]

    /**
     * 最大序列
     * @return
     */
    def maxOrder: Int

    /**
     * 验证系统代码是否重复
     * @return
     */
    def checkSystemcodeRepeat(systemcode: String): Boolean
  }

}

/**
 * 菜单
 */
trait MenuDaoComponent {

  trait MenuDao extends BaseDao[Menu, String, MenuQueryCondition] {
    def getUserMenus(userid: Long, parentmenucode: String = "0", systemcode: Option[String] = None): List[Menu]

    /**
     * 验证菜单代码重复
     * @param menucode
     * @return
     */
    def checkMenucodeRepeat(menucode: String): Boolean

    /**
     * 获取指定父菜单id当前的最大序列
     * @param parentId 父菜单id
     * @return
     */
    def maxOrder(parentId: String): Int

    /**
     *
     * @param roleId
     * @return
     */
    def getRelatedMenusByRoleId(roleId:Long):List[Menu]
  }

  val menuDao: MenuDao
}

/**
 * 角色
 */
trait RoleDaoComponent {

  trait RoleDao extends BaseDao[Role, Long, RoleQueryCondition] {
    /**
     * 角色名称重复验证
     * @param rolename
     * @return
     */
    def checkRolenameRepeat(rolename:String):Boolean
  }

  val roleDao: RoleDao
}

/**
 * 全局参数
 */
trait GlobalParamDaoComponent {

  trait GlobalParamDao extends BaseDao[GlobalParam, String, GlobalParamQueryCondition] {
    /**
     * 全局参数代码重复性验证
     * @param globalParamCode
     * @return
     */
    def checkGlobalParamRepeat(globalParamCode: String): Boolean
  }

  val globalParamDao: GlobalParamDao
}

/**
 * 用户角色映射
 */
trait UserRoleDaoComponent {

  trait UserRoleDao extends BaseDao[UserRole, (Long, Long), UserRoleQueryCondition] {
  }

  val userRoleDao: UserRoleDao
}

/**
 * 角色菜单映射
 */
trait RoleMenuDaoComponent {

  trait RoleMenuDao extends BaseDao[RoleMenu, (String, Long), RoleMenuQueryCondition]

  val roleMenuDao: RoleMenuDao
}

/**
 * 字典项数据访问声明
 */
trait DictItemDaoComponent {

  trait DictItemDao extends BaseDao[DictItem, Long, DictItemQueryCondition] {
    /**
     * 获取指定ID下的最大序列
     * @param dictcode
     * @param id
     * @return
     */
    def maxOrder(dictcode: String, id: Long): Int

    /**
     * 吃否有子字典项
     * @param id
     * @return
     */
    def hasChildren(id: Long): Boolean
  }

  val dictItemDao: DictItemDao
}

/**
 * 字典数据访问声明
 */
trait DictDaoComponent {

  trait DictDao extends BaseDao[Dict, Long, DictQueryCondition] {
    /**
     * 验证字典代码是否重复
     * @param dictcode
     * @return
     */
    def checkDictcodeRepeat(dictcode: String): Boolean

    /**
     * 获取最大序号(不分级)
     * @return
     */
    def maxOrder: Int
  }

  val dictDao: DictDao
}

/**
 * 登录日志组件
 */
trait LoginLogDaoComponent {

  /**
   * 登录日志dao
   */
  trait LoginLogDao extends BaseDao[LoginLog, Long, LoginLogQueryCondition] {

  }

  val loginLogDao: LoginLogDao
}

/**
 * 角色功能组件
 */
trait RoleFuncDaoComponent {

  /**
   * 角色功能dao
   */
  trait RoleFuncDao extends BaseDao[RoleFunc, (Long, String), RoleFuncQueryCondition]

  val roleFuncDao: RoleFuncDao
}

/**
 * 功能组件
 */
trait FunctionDaoComponent {

  /**
   * 功能组件dao
   */
  trait FunctionDao extends BaseDao[Function, String, FunctionQueryCondition] {
    /**
     * 验证ID是否重复
     * @param id
     */
    def checkIDRepeat(id:String):Boolean

    /**
     * 通过角色ID获取关联的功能信息
     */
    def getRelatedFunctionsByRoleid(roleId:Long):List[Function]
  }

  val functionDao: FunctionDao
}