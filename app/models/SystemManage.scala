package models

import java.util.Date
import scala.slick.model.codegen.StringGeneratorHelpers


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

case class Department(departid: Long = 0,
                      departcode: String,
                      departname: String,
                      departlevel: Int,
                      departfullcode: String,
                      parentDepartid: Long,
                      nodeOrder: Int = 0,
                      isLeaf: String = "Y",
                      departsimplepin: Option[String] = None,
                      departallpin: Option[String] = None,
                      departbrevitycode: Option[String] = None)


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
case class User(userid: Long = 0,
                departid: Long,
                useraccount: String,
                username: String,
                password: String,
                idnum: String,
                mobilePhone: Option[String],
                userorder: Int = 1,
                isVaild: String = "Y",
                userType: Option[String],
                jzlbdm: Option[String],
                jzlbmc: Option[String],
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
case class Role(roleid: Long = 0,
                departid: Long,
                rolename: String,
                roleDescription: Option[String],
                roleType: Option[String],
                jzlbdm: Option[String],
                jzlbmc: Option[String])


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
case class System(systemcode: String,
                  systemname: String,
                  systemdefine: String,
                  picturepath: String,
                  parentsystemcode: String,
                  nodeorder: Int,
                  isleaf: String,
                  fullcode: String)


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
case class Menu(menucode: String,
                menuname: String,
                funcentry: String,
                menulevel: Int,
                parentmenucode: String,
                menufullcode: String,
                nodeorder: Int,
                isleaf: String,
                systemcode: String)

/**
 * 全局参数
 * @param globalparcode 参数代码 唯一
 * @param globalparname 参数名称
 * @param globalparvalue 参数值
 */
case class GlobalParam(globalparcode: String,
                       globalparname: String,
                       globalparvalue: String)

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
 * @param id
 * @param createTime
 */
case class Dict(dictcode: String,
                dictname: String,
                superDictcode: String = "0",
                sibOrder: Int = 0,
                isleaf: Boolean = true,
                maintFlag: Int = 0,
                dictType: String,
                dictSimplePin: Option[String] = None,
                dictAllPin: Option[String] = None,
                dictItemTableName: Option[String] = None,
                dictVersion: Option[String] = "0",
                id: Long = 0, createTime: Date)

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
case class DictItem(dictcode: String,
                    displayName: String,
                    factValue: String,
                    appendValue: Option[String] = None,
                    superItemId: Long = 0,
                    sibOrder: Int = 0,
                    isleaf: Boolean = true,
                    displayFlag: Boolean = true,
                    isValid: Boolean = true,
                    itemSimplePin: Option[String] = None,
                    itemAllPin: Option[String] = None)

case class SupportTicket(stNo: String,
                         applicant: Long,
                         supportContent: String,
                         stStatus: String,
                         region: String,
                         serialNumber: Int,
                         id: Option[Long] = None,
                         devScheDate: Option[Date] = None,
                         psgScheDate: Option[Date] = None,
                         devDsScheDate: Option[Date] = None,
                         devDdScheDate: Option[Date] = None,
                         psgDsScheDate: Option[Date] = None,
                         psgIsScheDate: Option[Date] = None,
                         psgCompDate: Option[Date] = None,
                         devCompDate: Option[Date] = None,
                         applyingFeedbackDate: Option[Date] = None,
                         psgDsCompDate: Option[Date] = None,
                         psgIsCompDate: Option[Date] = None,
                         devDsCompDate: Option[Date] = None,
                         devDdCompDate: Option[Date] = None,
                         feedbackConfirmDate: Option[Date] = None,
                         comments: Option[String] = None,
                         archiveDate: Option[Date] = None,
                         archiveUserid: Option[Long] = None,
                         devDtScheDate: Option[Date] = None,
                         devDtCompDate: Option[Date] = None,
                         lastUpdateDate: Option[Date] = None,
                         archiveCode: Option[String] = None,
                         applyDate: Option[Date] = None)

case class TimeChange(id: Option[Long] = None,
                      trackingId: Option[Long] = None,
                      devScheDate: Option[Date] = None,
                      psgScheDate: Option[Date] = None,
                      devDsScheDate: Option[Date] = None,
                      devDdScheDate: Option[Date] = None,
                      devDtScheDate: Option[Date] = None,
                      psgDsScheDate: Option[Date] = None,
                      psgIdScheDate: Option[Date] = None,
                      _type: Option[String] = None)

case class Tracking(id: Option[Long] = None,
                    trackingDate: Date,
                    newProcess: String,
                    stId: Long,
                    approvalCode: String,
                    _type: String)

case class Supervision(supervisionSuggestion: String,
                       supervisionPersion: Long,
                       supervisionDate: Date,
                       stId: Option[Long] = None,
                       id: Option[Long] = None)



