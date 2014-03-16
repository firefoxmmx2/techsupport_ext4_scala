package models


/**
 * 机构
 * @param departid 机构id 唯一
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

case class Department(departid: Long, departcode: String, departname: String,
                      departlevel: Int, departfullcode: String,
                      parentDepartid: Long,
                      nodeOrder: Int = 0, isLeaf: String = "Y",
                      departsimplepin: Option[String], departallpin: Option[String],
                      departbrevitycode: Option[String])




/**
 * 用户
 * @param userid 用户id
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
case class User(userid: Long, departid: Long, useraccount: String, username: String,
                password: String, idnum: String, mobilePhone: Option[String], userorder: Int = 1,
                isVaild: String = "Y", userType: Option[String], jzlbdm: Option[String], jzlbmc: Option[String],
                email: Option[String])



/**
 * 角色
 * @param roleid 角色id
 * @param departid 机构id
 * @param rolename 角色名称
 * @param roleDescription 角色描述
 * @param roleType 角色类型
 * @param jzlbdm
 * @param jzlbmc
 */
case class Role(roleid: Long, departid: Long, rolename: String, roleDescription: Option[String],
                roleType: Option[String], jzlbdm: Option[String], jzlbmc: Option[String])



/**
 * 系统
 * @param systemcode 系统代码
 * @param systemname 系统名称
 * @param systemdefine 系统定义
 * @param picturepath 系统图标路径
 * @param parentsystemcode 父系统代码
 * @param nodeorder 节点排序号
 * @param isleaf 是否为叶子节点 默认Y
 * @param fullcode 系统全码 如techsupport.systemmanage.
 */
case class System(systemcode: String, systemname: String, systemdefine: String, picturepath: String,
                  parentsystemcode: String, nodeorder: Int, isleaf: String, fullcode: String)


/**
 * 菜单
 * @param menucode 菜单代码 唯一
 * @param menuname 菜单名称
 * @param funcentry 菜单地址
 * @param menulevel 菜单
 * @param parentmenucode 父菜单代码
 * @param menufullcode 菜单全码
 * @param nodeorder 排序号
 * @param isleaf 是否为叶子节点 默认Y
 * @param systemcode 系统代码 关联 System.systemcode
 */
case class Menu(menucode: String, menuname: String, funcentry: String, menulevel: Int,
                parentmenucode: String, menufullcode: String, nodeorder: Int,
                isleaf: String, systemcode: String)



