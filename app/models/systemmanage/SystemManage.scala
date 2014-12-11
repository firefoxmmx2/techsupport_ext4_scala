package models.systemmanage

import models.CommonTypeMode._
import models.QueryCondition
import org.joda.time.DateTime
import org.squeryl.annotations._
import org.squeryl.dsl.CompositeKey2
import org.squeryl.{KeyedEntity, Schema}

object SystemManage extends Schema {
  val departments = table[Department]("t_department")
  on(departments)(d => declare(d.id is(autoIncremented("departid"), primaryKey)
  ))
  //  val parentDepartmentToSubDepartments = oneToManyRelation(departments, departments)
  //    .via((pd, d) => d.parentDepartid === pd.id)
  val users = table[User]("t_user")
  on(users)(u => declare(u.id is(autoIncremented("userid"), primaryKey)))
  val departmentToUsers = oneToManyRelation(departments, users).via(
    (d, u) => d.id === u.departid
  )
  val roles = table[Role]("t_role")
  on(roles)(r => declare(r.id is(autoIncremented("roleid"), primaryKey)))
  val systems = table[System]("t_system")
  on(systems)(s => declare(s.id is (primaryKey)))
  val menus = table[Menu]("t_menu")
  on(menus)(m => declare(m.id is (primaryKey)))
  val globalParams = table[GlobalParam]("t_globalpar")
  on(globalParams)(g => declare(g.id is primaryKey))
  val versions = table[Version]("t_version")
  on(versions)(v => declare(v.id is(autoIncremented("versionid"), primaryKey)))
  val dicts = table[Dict]("t_dict")
  on(dicts)(d => declare(d.id is (autoIncremented("dict_id"))))
  val dictItems = table[DictItem]("t_dict_item")
  on(dictItems)(d => declare(d.id is(autoIncremented("item_id"), primaryKey)))
  val userRoles = table[UserRole]("t_user_role")
  val roleMenus = table[RoleMenu]("t_role_menu")
  val loginLogs = table[LoginLog]("t_loginlog")
  on(loginLogs)(l => declare(l.id is(autoIncremented("loginlogid"), primaryKey)))
  val roleFuncs = table[RoleFunc]("t_role_func")
  val functions = table[Function]("t_function")
}

/**
 * 机构
 * @param departcode 机构代码 唯一
 * @param departname 机构名称
 * @param departlevel 机构级别
 * @param departfullcode 机构全码
 * @param parentDepartid 父机构id
 * @param nodeOrder 排序号
 * @param isLeaf 是否为叶子节点 默认Y
 * @param departsimplepin 机构简拼 搜索用
 * @param departallpin 机构全拼 搜索用
 * @param departbrevitycode 机构省级代码
 */

case class Department(
                       departcode: String,
                       departname: String,
                       departlevel: Int,
                       departfullcode: String,
                       parentDepartid: Option[Long] = None,
                       nodeOrder: Int = 0,
                       isLeaf: String = "Y",
                       departsimplepin: Option[String] = None,
                       departallpin: Option[String] = None,
                       departbrevitycode: Option[String] = None,
                       @Column("departid")
                       id: Option[Long]) extends KeyedEntity[Option[Long]] {
  override def toString = s"id=$id,departcode=$departcode,departname=$departname," +
    s"departlevel=$departlevel,departfullcode=$departfullcode,parentDepartid=$parentDepartid}"
}

case class DepartmentQueryCondition(
                                     id: Option[Long] = None,
                                     departcode: Option[String] = None,
                                     departname: Option[String] = None,
                                     departlevel: Option[Int] = None,
                                     departfullcode: Option[String] = None,
                                     departsimplepin: Option[String] = None,
                                     departallpin: Option[String] = None,
                                     isLeaf: Option[String] = None,
                                     parentDepartid: Option[Long] = None
                                     ) extends QueryCondition

/**
 * 用户
 * @param departid 机构id
 * @param useraccount 用户帐号
 * @param username 用户名称
 * @param password 密码 MD5加密
 * @param idnum 身份证号码
 * @param mobilePhone 手机号码
 * @param userorder 排序号
 * @param isValid 是否有效 默认Y
 * @param userType 用户类别
 * @param jzlbdm
 * @param jzlbmc
 * @param email 邮箱
 */
case class User(
                 departid: Long,
                 useraccount: String,
                 username: String,
                 password: String,
                 idnum: Option[String],
                 @Column("MOBILEPHONE")
                 mobilePhone: Option[String],
                 userorder: Int = 1,
                 isValid: String = "Y",
                 userType: Option[String],
                 jzlbdm: Option[String],
                 jzlbmc: Option[String],
                 email: Option[String],
                 @Column("USERID")
                 id: Option[Long]) extends KeyedEntity[Option[Long]] {
  override def toString: String = s"{id:$id,departid=$departid,useraccount=$useraccount," +
    s"username=$username,password=$password,idnum=$idnum," +
    s"mobilePhone=$mobilePhone,...}"

  lazy val department = inTransaction {
    SystemManage.departmentToUsers.right(this).single
  }
}

case class UserQueryCondition(
                               id: Option[Long] = None,
                               departid: Option[Long] = None,
                               useraccount: Option[String] = None,
                               password: Option[String] = None,
                               idnum: Option[String] = None,
                               mobilePhone: Option[String] = None,
                               userorder: Option[Int] = None,
                               isValid: Option[String] = None,
                               userType: Option[String] = None,
                               email: Option[String] = None) extends QueryCondition {
  override def toString: String = "[id:" + id + ",departid:" + departid + ",useraccount:" + useraccount + "," +
    "password:" + password + ",idnum:" + idnum + ",mobilePhone:" + mobilePhone + ",userorder:" + userorder + "," +
    "isValid:" + isValid + ",userType:" + userType + ",email:" + email + "]";
}

/**
 * 角色
 * @param departid 机构id
 * @param rolename 角色名称
 * @param roleDescription 角色描述
 * @param roleType 角色类型
 * @param jzlbdm
 * @param jzlbmc
 */
case class Role(
                 rolename: String,
                 roleDescription: Option[String] = None,
                 roleType: String = "0",
                 jzlbdm: Option[String] = None,
                 jzlbmc: Option[String] = None,
                 departid: Long = 0,
                 @Column("ROLEID")
                 id: Option[Long] = None) extends KeyedEntity[Option[Long]] {
}

case class RoleQueryCondition(
                               id: Option[Long] = None,
                               rolename: Option[String] = None,
                               roleDescript: Option[String] = None,
                               roleType: Option[String] = None,
                               departid: Option[Long] = None) extends QueryCondition

/**
 * 系统
 * @param id 系统代码 唯一 可以自行输入
 * @param systemname 系统名称
 * @param systemdefine 系统定义
 * @param picturepath 系统图标路径
 * @param parentsystemcode 父系统代码
 * @param nodeorder 节点排序号
 * @param isleaf 是否为叶子节点 默认Y
 * @param fullcode 系统全码 如techsupport.systemmanage.
 */
case class System(
                   @Column("systemcode")
                   id: String,
                   systemname: String,
                   systemdefine: String,
                   picturepath: String,
                   parentsystemcode: String,
                   nodeorder: Int,
                   isleaf: String,
                   fullcode: String) extends KeyedEntity[String] {

}

case class SystemQueryCondition(id: Option[String] = None,
                                systemname: Option[String] = None,
                                systemdefine: Option[String] = None,
                                parentsystemcode: Option[String] = None,
                                isleaf: Option[String] = None,
                                fullcode: Option[String] = None) extends QueryCondition {

}

/**
 * 菜单
 * @param id 菜单代码 唯一 可以自行输入
 * @param menuname 菜单名称
 * @param funcentry 菜单地址
 * @param menulevel 菜单
 * @param parentmenucode 父菜单代码
 * @param menufullcode 菜单全码
 * @param nodeorder 排序号
 * @param isleaf 是否为叶子节点 默认Y
 * @param systemcode 系统代码 关联 System.systemcode
 */
case class Menu(
                 @Column("menucode")
                 id: String,
                 menuname: String,
                 funcentry: String,
                 menulevel: Int,
                 parentmenucode: String,
                 menufullcode: String,
                 nodeorder: Int,
                 isleaf: String,
                 systemcode: String) extends KeyedEntity[String] {
}

case class MenuQueryCondition(id: Option[String] = None,
                              menuname: Option[String] = None,
                              funcentry: Option[String] = None,
                              menulevel: Option[Int] = None,
                              parentmenucode: Option[String] = None,
                              menufullcode: Option[String] = None,
                              isleaf: Option[String] = None,
                              systemcode: Option[String] = None) extends QueryCondition {
  var userid: Option[Long] = _

}

/**
 * 全局参数
 * @param id 参数代码 唯一
 * @param globalparname 参数名称
 * @param globalparvalue 参数值
 */
case class GlobalParam(
                        @Column("globalparcode")
                        id: String,
                        globalparname: String,
                        globalparvalue: String) extends KeyedEntity[String] {
}

case class GlobalParamQueryCondition(id: Option[String] = None,
                                     globalparname: Option[String] = None,
                                     globalparvalue: Option[String] = None) extends QueryCondition

/**
 * 字典
 * @param dictcode
 * @param dictname
 * @param superDictcode
 * @param sibOrder
 * @param isleaf
 * @param maintFlag
 * @param dictType
 * @param dictSimplePin
 * @param dictAllPin
 * @param dictItemTableName
 * @param dictVersion
 * @param createTime
 */
case class Dict(
                 @Column("DICT_CODE")
                 dictcode: String,
                 @Column("DICT_NAME")
                 dictname: String,
                 @Column("SUPER_DICT_CODE")
                 superDictcode: String = "0",
                 @Column("SIB_ORDER")
                 sibOrder: Int = 0,
                 @Column("LEAF_FLAG")
                 isleaf: String = "Y",
                 @Column("MAINT_FLAG")
                 maintFlag: Int = 0,
                 @Column("DICT_TYPE")
                 dictType: String,
                 @Column("DICT_SIMPLEPIN")
                 dictSimplePin: Option[String] = None,
                 @Column("DICT_ALLPIN")
                 dictAllPin: Option[String] = None,
                 @Column("DICT_ITEMTABLENAME")
                 dictItemTableName: Option[String] = None,
                 @Column("DICT_VERSIONID")
                 dictVersion: Option[String] = Option("0"),
                 @Column("TXSJ")
                 createTime: Option[DateTime] = None,
                 @Column("DICT_ID")
                 id: Option[Long] = None) extends KeyedEntity[Option[Long]] {
}

case class DictQueryCondition(id: Option[Long] = None,
                              dictcode: Option[String] = None,
                              dictname: Option[String] = None,
                              superDictcode: Option[String] = None,
                              createTime: Option[DateTime] = None,
                              queryfield: Option[String] = None) extends QueryCondition

/**
 * 字典项
 * @param dictcode
 * @param displayName
 * @param factValue
 * @param appendValue
 * @param superItemId
 * @param sibOrder
 * @param isleaf
 * @param displayFlag
 * @param isValid
 * @param itemSimplePin
 * @param itemAllPin
 */
case class DictItem(
                     @Column("DICT_CODE")
                     dictcode: String,
                     @Column("DISPLAY_NAME")
                     displayName: String,
                     @Column("FACT_VALUE")
                     factValue: String,
                     @Column("APPEND_VALUE")
                     appendValue: Option[String] = None,
                     @Column("SUPER_ITEM_ID")
                     superItemId: Option[Long] = None,
                     @Column("SIB_ORDER")
                     sibOrder: Int = 0,
                     @Column("LEAF_FLAG")
                     isleaf: String = "Y",
                     @Column("DISPLAY_FLAG")
                     displayFlag: Int = 0,
                     @Column("VALID_FLAG")
                     isValid: Int = 0,
                     @Column("ITEM_SIMPLEPIN")
                     itemSimplePin: Option[String] = None,
                     @Column("ITEM_ALLPIN")
                     itemAllPin: Option[String] = None,
                     @Column("ITEM_ID")
                     id: Option[Long] = None) extends KeyedEntity[Option[Long]] {
}

case class DictItemQueryCondition(id: Option[Long] = None,
                                  dictcode: Option[String] = None,
                                  displayName: Option[String] = None,
                                  factValue: Option[String] = None,
                                  appendValue: Option[String] = None,
                                  superItemId: Option[Long] = None,
                                  displayFlag: Option[Int] = None,
                                  queryfield: Option[String] = None) extends QueryCondition

/**
 * 版本
 * @param versionNum
 * @param versionInfo
 * @param versionDate
 * @param updateTime
 */
case class Version(@Column("VERSIONNUM")
                   versionNum: String,
                   @Column("VERSIONINFO")
                   versionInfo: String,
                   @Column("VERSIONDATE")
                   versionDate: DateTime,
                   @Column("UPDATETIME")
                   updateTime: DateTime,
                   @Column("VERSIONID")
                   id: Option[Long]) extends KeyedEntity[Option[Long]] {
}

/**
 * 角色菜单
 * @param menucode
 * @param roleid
 */
case class RoleMenu(
                     menucode: String,
                     roleid: Long
                     ) extends KeyedEntity[CompositeKey2[String, Long]] {
  def id = compositeKey(menucode, roleid)
}

case class RoleMenuQueryCondition(menucode: Option[String], roleid: Option[Long]) extends QueryCondition

/**
 * 用户角色
 * @param roleid
 * @param userid
 */
case class UserRole(roleid: Long, userid: Long) extends KeyedEntity[CompositeKey2[Long, Long]] {
  def id = compositeKey(roleid, userid)
}

case class UserRoleQueryCondition(roleid: Option[Long], userid: Option[Long]) extends QueryCondition

/**
 * 登录日志
 * @param id
 * @param loginuserid
 * @param useraccount
 * @param username
 * @param loginunitcode
 * @param loginunitname
 * @param loginip
 * @param loginmac
 * @param logintiime
 * @param quittime
 */
case class LoginLog(
                     @Column("loginlogid")
                     id: Option[Long] = None,
                     loginuserid: Long,
                     useraccount: String,
                     username: String,
                     loginunitcode: String,
                     loginunitname: String,
                     loginip: String,
                     loginmac: Option[String] = None,
                     logintiime: DateTime,
                     quittime: Option[DateTime] = None
                     ) extends KeyedEntity[Option[Long]]

case class LoginLogQueryCondition(
                                   id: Option[Long] = None,
                                   loginuserid: Option[Long] = None,
                                   useraccount: Option[String] = None,
                                   username: Option[String] = None,
                                   loginunitcode: Option[String] = None,
                                   loginunitname: Option[String] = None,
                                   loginip: Option[String] = None,
                                   loginmac: Option[String] = None,
                                   logintiimeStart: Option[DateTime] = None,
                                   logintiimeEnd: Option[DateTime] = None,
                                   quittimeStart: Option[DateTime] = None,
                                   quittimeEnd: Option[DateTime] = None
                                   ) extends QueryCondition

/**
 * 角色功能
 * @param roleId
 * @param funccode
 * @param param
 */
case class RoleFunc(
                     roleId: Long,
                     funccode: String,
                     param: Option[String] = None
                     ) extends KeyedEntity[CompositeKey2[Long, String]] {
  def id = compositeKey(roleId, funccode)
}

/**
 * 角色功能查询
 * @param roleId
 * @param funccode
 */
case class RoleFuncQueryCondition(
                                   roleId: Option[Long] = None,
                                   funccode: Option[String] = None
                                   ) extends QueryCondition

/**
 * 功能
 * @param id
 * @param systemcode
 * @param funcname
 * @param funcdefine
 * @param functype
 */
case class Function(
                     @Column("funccode")
                     id: Option[String] = None,
                     systemcode: String,
                     funcname: Option[String] = None,
                     funcdefine: Option[String] = None,
                     functype: Long = 0
                     ) extends KeyedEntity[Option[String]]

case class FunctionQueryCondition(
                                   id: Option[String] = None,
                                   systemcode: Option[String] = None,
                                   funcname: Option[String] = None,
                                   funcdefine: Option[String] = None,
                                   functype: Option[Long] = None
                                   ) extends QueryCondition