package models

import org.joda.time.DateTime
import org.squeryl.annotations.Column
import org.squeryl.{KeyedEntity, Schema}
import CommonTypeMode._

object SystemManage extends Schema {
  val departments = table[Department]("t_department")
  on(departments)(d => declare(d.id is(autoIncremented("departid"), primaryKey)))
  val users = table[User]("t_user")
  on(users)(u => declare(u.id is(autoIncremented("userid"), primaryKey)))
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
                       parentDepartid: Long,
                       nodeOrder: Int = 0,
                       isLeaf: String = "Y",
                       departsimplepin: Option[String] = None,
                       departallpin: Option[String] = None,
                       departbrevitycode: Option[String] = None,
                       @Column("departid")
                       id: Option[Long]) extends KeyedEntity[Option[Long]] {

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
 * @param isVaild 是否有效 默认Y
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
                 idnum: String,
                 @Column("MOBILE_PHONE")
                 mobilePhone: Option[String],
                 userorder: Int = 1,
                 isVaild: String = "Y",
                 userType: Option[String],
                 jzlbdm: Option[String],
                 jzlbmc: Option[String],
                 email: Option[String],
                 @Column("USERID")
                 id: Option[Long]) extends KeyedEntity[Option[Long]] {
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
                               email: Option[String] = None) extends QueryCondition

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
                 roleType: Option[String] = None,
                 jzlbdm: Option[String] = None,
                 jzlbmc: Option[String] = None,
                 departid: Option[Long] = None,
                 @Column("ROLEID")
                 id: Option[Long] = None) extends KeyedEntity[Option[Long]] {
}

case class RoleQueryCondition(id: Option[Long] = None,
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
                                fullcode: Option[String] = None) extends QueryCondition

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
                              systemcode: Option[String] = None) extends QueryCondition

/**
 * 全局参数
 * @param id 参数代码 唯一
 * @param globalparname 参数名称
 * @param globalparvalue 参数值
 */
case class GlobalParam(
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
                              createTime: Option[DateTime] = None)

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
                     superItemId: Long = 0,
                     @Column("SIB_ORDER")
                     sibOrder: Int = 0,
                     @Column("LEAF_FLAG")
                     isleaf: String = "Y",
                     @Column("DISPLAY_FLAG")
                     displayFlag: String = "Y",
                     @Column("VAILD_FLAG")
                     isValid: String = "Y",
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
                                  displayFlag: Option[Boolean] = None) extends QueryCondition

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



