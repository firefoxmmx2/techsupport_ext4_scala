package models

import org.joda.time.DateTime
import org.squeryl.annotations._
import org.squeryl.{KeyedEntity, Schema}
import CommonTypeMode._

object SystemManage extends Schema {
  val departments = table[Department]("T_DEPARTMENT")
  on(departments)(d => declare(d.id is(autoIncremented("DEPARTID"), primaryKey)))
  val users = table[User]("T_USER")
  on(users)(u => declare(u.id is(autoIncremented("USERID"), primaryKey)))
  val roles = table[Role]("T_ROLE")
  on(roles)(r => declare(r.id is(autoIncremented("ROLEID"), primaryKey)))
  val systems = table[System]("T_SYSTEM")
  on(systems)(s => declare(s.id is (primaryKey)))
  val menus = table[Menu]("T_MENU")
  on(menus)(m => declare(m.id is (primaryKey)))
  val globalParams = table[GlobalParam]("T_GLOBALPAR")
  on(globalParams)(g => declare(g.id is primaryKey))
  val versions = table[Version]("T_VERSION")
  on(versions)(v => declare(v.id is(autoIncremented("VERSIONID"), primaryKey)))
  val dicts = table[Dict]("T_DICT")
  on(dicts)(d => declare(d.id is (autoIncremented("DICT_ID"))))
  val dictItems = table[DictItem]("T_DICT_ITEM")
  on(dictItems)(d => declare(d.id is(autoIncremented("ITEM_ID"), primaryKey)))
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
                       @ColumnBase("PARENT_DEPARTID")
                       parentDepartid: Long,
                       @ColumnBase("NODE_ORDER")
                       nodeOrder: Int = 0,
                       @ColumnBase("ISLEAF")
                       isLeaf: String = "Y",
                       departsimplepin: Option[String] = None,
                       departallpin: Option[String] = None,
                       departbrevitycode: Option[String] = None,
                       departid: Option[Long]) extends KeyedEntity[Option[Long]] {

  def id: Option[Long] = departid
}


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
                 @ColumnBase("MOBILE_PHONE")
                 mobilePhone: Option[String],
                 userorder: Int = 1,
                 @ColumnBase("ISVAILD")
                 isVaild: String = "Y",
                 @ColumnBase("USERTYPE")
                 userType: Option[String],
                 jzlbdm: Option[String],
                 jzlbmc: Option[String],
                 @ColumnBase
                 email: Option[String],
                 userid: Option[Long]) extends KeyedEntity[Option[Long]] {
  def id: Option[Long] = userid
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
                 @ColumnBase("ROLEDESCRIPTION")
                 roleDescription: Option[String] = None,
                 @ColumnBase("ROLETYPE")
                 roleType: Option[String] = None,
                 jzlbdm: Option[String] = None,
                 jzlbmc: Option[String] = None,
                 departid: Option[Long] = None,
                 roleid: Option[Long] = None) extends KeyedEntity[Option[Long]] {
  def id: Option[Long] = roleid
}


/**
 * 系统
 * @param systemname 系统名称
 * @param systemdefine 系统定义
 * @param picturepath 系统图标路径
 * @param parentsystemcode 父系统代码
 * @param nodeorder 节点排序号
 * @param isleaf 是否为叶子节点 默认Y
 * @param fullcode 系统全码 如techsupport.systemmanage.
 */
case class System(
                   systemcode: String,
                   systemname: String,
                   systemdefine: String,
                   picturepath: String,
                   parentsystemcode: String,
                   nodeorder: Int,
                   isleaf: String,
                   fullcode: String) extends KeyedEntity[String] {

  def id: String = systemcode
}


/**
 * 菜单
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
                 menucode: String,
                 menuname: String,
                 funcentry: String,
                 menulevel: Int,
                 parentmenucode: String,
                 menufullcode: String,
                 nodeorder: Int,
                 isleaf: String,
                 systemcode: String) extends KeyedEntity[String] {
  def id: String = menucode
}

/**
 * 全局参数
 * @param globalparcode 参数代码 唯一
 * @param globalparname 参数名称
 * @param globalparvalue 参数值
 */
case class GlobalParam(
                        globalparcode: String,
                        globalparname: String,
                        globalparvalue: String) extends KeyedEntity[String] {
  def id: String = globalparcode
}

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
                 @ColumnBase("DICT_CODE")
                 dictcode: String,
                 @ColumnBase("DICT_NAME")
                 dictname: String,
                 @ColumnBase("SUPER_DICT_CODE")
                 superDictcode: String = "0",
                 @ColumnBase("SIB_ORDER")
                 sibOrder: Int = 0,
                 @ColumnBase("LEAF_FLAG")
                 isleaf: Boolean = true,
                 @ColumnBase("MAINT_FLAG")
                 maintFlag: Int = 0,
                 @ColumnBase("DICT_TYPE")
                 dictType: String,
                 @ColumnBase("DICT_SIMPLEPIN")
                 dictSimplePin: Option[String] = None,
                 @ColumnBase("DICT_ALLPIN")
                 dictAllPin: Option[String] = None,
                 @ColumnBase("DICT_ITEMTABLENAME")
                 dictItemTableName: Option[String] = None,
                 @ColumnBase("DICT_VERSIONID")
                 dictVersion: Option[String] = Option("0"),
                 @ColumnBase("TXSJ")
                 createTime: Option[DateTime] = None,
                 @ColumnBase("DICT_ID")
                 dictId: Option[Long] = None) extends KeyedEntity[Option[Long]] {
  def id: Option[Long] = dictId
}

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
                     @ColumnBase("DICT_CODE")
                     dictcode: String,
                     @ColumnBase("DISPLAY_NAME")
                     displayName: String,
                     @ColumnBase("FACT_VALUE")
                     factValue: String,
                     @ColumnBase("APPEND_VALUE")
                     appendValue: Option[String] = None,
                     @ColumnBase("SUPER_ITEM_ID")
                     superItemId: Long = 0,
                     @ColumnBase("SIB_ORDER")
                     sibOrder: Int = 0,
                     @ColumnBase("LEAF_FLAG")
                     isleaf: Boolean = true,
                     @ColumnBase("DISPLAY_FLAG")
                     displayFlag: Boolean = true,
                     @ColumnBase("VAILD_FLAG")
                     isValid: Boolean = true,
                     @ColumnBase("ITEM_SIMPLEPIN")
                     itemSimplePin: Option[String] = None,
                     @ColumnBase("ITEM_ALLPIN")
                     itemAllPin: Option[String] = None,
                     @ColumnBase("ITEM_ID")
                     itemId: Option[Long] = None) extends KeyedEntity[Option[Long]] {
  def id: Option[Long] = itemId
}


/**
 * 版本
 * @param versionNum
 * @param versionInfo
 * @param versionDate
 * @param updateTime
 */
case class Version(@ColumnBase("VERSIONNUM")
                   versionNum: String,
                   @ColumnBase("VERSIONINFO")
                   versionInfo: String,
                   @ColumnBase("VERSIONDATE")
                   versionDate: DateTime,
                   @ColumnBase("UPDATETIME")
                   updateTime: DateTime,
                   @ColumnBase("VERSIONID")
                   versionId: Option[Long]) extends KeyedEntity[Option[Long]] {
  def id: Option[Long] = versionId
}



