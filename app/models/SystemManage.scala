package models

import java.sql.Date
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
                dictVersion: Option[String] = Option("0"),
                id: Option[Long] = Option(0),
                createTime: Option[Date] = None)

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

/**
 * 技术支持单
 * @param stNo
 * @param applicant
 * @param supportContent
 * @param stStatus
 * @param region
 * @param serialNumber
 * @param id
 * @param devScheDate
 * @param psgScheDate
 * @param devDsScheDate
 * @param devDdScheDate
 * @param psgDsScheDate
 * @param psgIsScheDate
 * @param psgCompDate
 * @param devCompDate
 * @param applyingFeedbackDate
 * @param psgDsCompDate
 * @param psgIsCompDate
 * @param devDsCompDate
 * @param devDdCompDate
 * @param feedbackConfirmDate
 * @param comments
 * @param archiveDate
 * @param archiveUserid
 * @param devDtScheDate
 * @param devDtCompDate
 * @param lastUpdateDate
 * @param archiveCode
 * @param applyDate
 */
case class SupportTicket(
                          stNo: String,
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


/**
 * 时间变更
 * @param id
 * @param trackingId
 * @param devScheDate
 * @param psgScheDate
 * @param devDsScheDate
 * @param devDdScheDate
 * @param devDtScheDate
 * @param psgDsScheDate
 * @param psgIdScheDate
 * @param _type
 */
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

/**
 * 进展提示
 * @param id
 * @param trackingDate
 * @param newProcess
 * @param stId
 * @param approvalCode
 * @param _type
 */
case class Tracking(id: Option[Long] = None,
                    trackingDate: Date,
                    newProcess: String,
                    stId: Long,
                    approvalCode: String,
                    _type: String)

/**
 * 督办
 * @param supervisionSuggestion
 * @param supervisionPersion
 * @param supervisionDate
 * @param stId
 * @param id
 */
case class Supervision(supervisionSuggestion: String,
                       supervisionPersion: Long,
                       supervisionDate: Date,
                       stId: Option[Long] = None,
                       id: Option[Long] = None)

/**
 * 版本
 * @param versionNum
 * @param versionInfo
 * @param versionDate
 * @param updateTime
 * @param versionId
 */
case class Version(versionNum: String,
                   versionInfo: String,
                   versionDate: Date,
                   updateTime: Date,
                   versionId: Option[Long] = None)



