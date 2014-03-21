package models.slick


import scala.slick.lifted._
import com.typesafe.slick.driver.oracle.OracleDriver.simple._
import models._
import models.Role
import models.Department
import models.Menu
import models.User
import models.System
import java.sql.{Timestamp, Date}
import org.joda.time.DateTime
import scala.slick.collection.heterogenous._
import syntax._

/**
 * Created by hooxin on 14-3-16.
 */

object MapperTypeImplicits {
  implicit val DATETIME_COLUMN = MappedColumnType.base[DateTime, Timestamp](
    dt => new Timestamp(dt.getMillis),
    ts => new DateTime(ts)
  )

  implicit val javaUtilDateTypeMapper = MappedColumnType.base[java.util.Date, Timestamp](
    dt => new Timestamp(dt.getTime),
    ts => new java.util.Date(ts.getTime)
  )
}

import MapperTypeImplicits._

/**
 * 机构表
 * @param tag 别名
 */
class DepartmentTable(tag: Tag) extends Table[Department](tag, "T_DEPARTMENT") {

  def departid = column[Long]("DEPARTID", O.PrimaryKey)

  def departcode = column[String]("DEPARTCODE")

  def departname = column[String]("DEPARTNAME")

  def departlevel = column[Int]("DEPARTLEVEL")

  def departfullcode = column[String]("DEPARTFULLCODE")

  def parentDepartid = column[Long]("PARENTDEPARTID")

  def nodeOrder = column[Int]("NODEORDER")

  def isLeaf = column[String]("ISLEAF")

  def departsimplepin = column[Option[String]]("DEPARTSIMPLEPIN", O.Nullable)

  def departallpin = column[Option[String]]("DEPARTALLPIN", O.Nullable)

  def departbrevitycode = column[Option[String]]("DEPARTBREVITYCODE", O.Nullable)

  def * = (departid, departcode, departname, departlevel, departfullcode, parentDepartid,
    nodeOrder, isLeaf, departsimplepin, departallpin, departbrevitycode) <>(Department.tupled, Department.unapply)

  val sequence = Sequence[Long]("DEPARTID")

}

/**
 * 菜单表
 * @param tag 别名
 */
class MenuTable(tag: Tag) extends Table[Menu](tag, "T_MENU") {
  def menucode = column[String]("MENUCODE", O.PrimaryKey)

  def menuname = column[String]("MENUNAME")

  def funcentry = column[String]("FUNCENTRY")

  def menulevel = column[Int]("MENULEVEL")

  def parentmenucode = column[String]("PARENTMENUCODE")

  def menufullcode = column[String]("MENUFULLCODE")

  def nodeorder = column[Int]("NODEORDER")

  def isleaf = column[String]("ISLEAF")

  def systemcode = column[String]("systemcode", O.NotNull)

  def * = (menucode,
    menuname,
    funcentry,
    menulevel,
    parentmenucode,
    menufullcode,
    nodeorder,
    isleaf,
    systemcode) <>(Menu.tupled, Menu.unapply)
}

/**
 * 角色表
 * @param tag 别名
 */
class RoleTable(tag: Tag) extends Table[Role](tag, "T_ROLE") {
  val sequence = Sequence[Long]("ROLEID")

  def roleid = column[Long]("ROLEID", O.PrimaryKey)

  def departid = column[Long]("DEPARTID")

  def rolename = column[String]("ROLENAME")

  def roleDescription = column[Option[String]]("ROLEDESCRIPTION")

  def roleType = column[Option[String]]("ROLETYPE")

  def jzlbdm = column[Option[String]]("JZLBDM")

  def jzlbmc = column[Option[String]]("JZLBMC")

  def * = (roleid, departid, rolename, roleDescription, roleType,
    jzlbdm, jzlbmc) <>(Role.tupled, Role.unapply)
}

/**
 * 系统表
 * @param tag 别名
 */
class SystemTable(tag: Tag) extends Table[System](tag, "T_SYSTEM") {
  def systemcode = column[String]("SYSTEMCODE", O.PrimaryKey)

  def systemname = column[String]("SYSTEMNAME")

  def systemdefine = column[String]("SYSTEMDEFINE")

  def picturepath = column[String]("PICTUREPATH")

  def parentsystemcode = column[String]("PARENTSYSTEMCODE")

  def nodeorder = column[Int]("NODEORDER")

  def isleaf = column[String]("ISLEAF")

  def fullcode = column[String]("FULLCODE")

  def * = (systemcode, systemname, systemdefine, picturepath, parentsystemcode,
    nodeorder, isleaf, fullcode) <>(System.tupled, System.unapply)
}

/**
 * 用户表
 * @param tag 别名
 */
class UserTable(tag: Tag) extends Table[User](tag, "T_USER") {
  val sequence = Sequence[Long]("USERID")

  def userid = column[Long]("USERID", O.PrimaryKey)

  def departid = column[Long]("DEPARTID")

  def useraccount = column[String]("USERACCOUNT")

  def username = column[String]("USERNAME")

  def password = column[String]("PASSWORD")

  def idnum = column[String]("IDNUM")

  def mobilePhone = column[Option[String]]("MOBILEPHONE")

  def userorder = column[Int]("USERORDER")

  def isVaild = column[String]("ISVAILD")

  def userType = column[Option[String]]("USERTYPE")

  def jzlbdm = column[Option[String]]("JZLBDM")

  def jzlbmc = column[Option[String]]("JZLBMC")

  def email = column[Option[String]]("EMAIL")

  def * = (userid, departid, useraccount, username, password, idnum, mobilePhone, userorder,
    isVaild, userType, jzlbdm, jzlbmc, email) <>(User.tupled, User.unapply)

}

/**
 * 用户角色
 * @param tag 别名
 */
class UserRoleTable(tag: Tag) extends Table[(Long, Long)](tag, "T_USER_ROLE") {
  def roleid = column[Long]("ROLEID")

  def userid = column[Long]("USERID")

  def * = (roleid, userid)
}

/**
 * 角色菜单
 * @param tag 别名
 */
class RoleMenuTable(tag: Tag) extends Table[(Long, String)](tag, "T_ROLE_MENU") {
  def roleid = column[Long]("ROLEID")

  def menucode = column[String]("MENUCODE")

  def * = (roleid, menucode)
}


class SupportTicketTable(tag: Tag) extends Table[SupportTicket](tag, "T_TS_TECH_SUPPORT") {
  val sequence = Sequence[Long]("SEQ_TS_ST")

  def stNo = column[String]("ST_NO")

  def applicant = column[Long]("APPLICANT")

  def supportContent = column[String]("SUPPORT_CONTENT")

  def stStatus = column[String]("ST_STATUS")

  def region = column[String]("REGION")

  def serialNumber = column[Int]("SERIAL_NUMBER")

  def id = column[Long]("ID", O.PrimaryKey)

  def devScheDate = column[DateTime]("DEV_SCHE_DATE", O.Nullable)

  def psgScheDate = column[DateTime]("PSG_SCHE_DATE", O.Nullable)

  def devDsScheDate = column[DateTime]("DEV_DS_SCHE_DATE", O.Nullable)

  def devDdScheDate = column[DateTime]("DEV_DD_SCHE_DATE", O.Nullable)

  def psgDsScheDate = column[DateTime]("PSG_DS_SCHE_DATE", O.Nullable)

  def psgIsScheDate = column[DateTime]("PSG_IS_SCHE_DATE", O.Nullable)

  def psgCompDate = column[DateTime]("PSG_COMP_DATE", O.Nullable)

  def devCompDate = column[DateTime]("DEV_COMP_DATE", O.Nullable)

  def applyingFeedbackDate = column[DateTime]("APPLYING_FEEDBACK_DATE", O.Nullable)

  def psgDsCompDate = column[DateTime]("PSG_DS_COMP_DATE", O.Nullable)

  def psgIsCompDate = column[DateTime]("PSG_IS_COMP_DATE", O.Nullable)

  def devDsCompDate = column[DateTime]("DEV_DS_COMP_DATE", O.Nullable)

  def devDdCompDate = column[DateTime]("DEV_DD_COMP_DATE", O.Nullable)

  def feedbackConfirmDate = column[DateTime]("FEEDBACK_CONFIRM_DATE", O.Nullable)

  def comments = column[String]("COMMENTS", O.Nullable)

  def archiveDate = column[DateTime]("ARCHIVE_DATE", O.Nullable)

  def archiveUserid = column[Long]("ARCHIVE_USERID", O.Nullable)

  def devDtScheDate = column[DateTime]("DEV_DT_SCHE_DATE", O.Nullable)

  def devDtCompDate = column[DateTime]("DEV_DT_COMP_DATE", O.Nullable)

  def lastUpdateDate = column[DateTime]("LAST_UPDATE_DATE", O.Nullable)

  def archiveCode = column[String]("ARCHIVE_CODE", O.Nullable)

  def applyDate = column[DateTime]("APPLY_DATE", O.Nullable)

  def * = List(
//    基本信息
    stNo,
    applicant,
    supportContent,
    stStatus,
    region,
    serialNumber,
    applyDate,
//    技术部门审批
    devScheDate,
    devDsScheDate,
    devDdScheDate,
    devDtScheDate,
//    产品部门审批
    psgScheDate,
    psgDsScheDate,
    psgIsScheDate,
//    提请反馈与提示
    applyingFeedbackDate,
    psgCompDate,
    devCompDate,
    psgDsCompDate,
    psgIsCompDate,
    devDsCompDate,
    devDdCompDate,
    devDtCompDate,
//    反馈确认
    feedbackConfirmDate,
//    归档
    archiveCode,
    comments,
    archiveDate,
    archiveUserid,
    lastUpdateDate.?,
    id.?
    )  <> (st => SupportTicket(
    SupportTicketBaseContent(st._1,st._2,st._3,st._4,st._5,st._6,st._7),
    Option(SupportTicketCompanyApproval()),
    Option(SupportTicketTechDepartmentApproval(st._8,st._9,st._10,st._11)),
    Option(SupportTicketProductDepartmentApproval(st._12,st._13,st._14)),
  Option(SupportTicketTracking(st._15,st._16,st._17,st._18,st._19,st._20,st._21,st._22)),
  Option(SupportTicketFeedback(st._23)),
  Option(SupportTicketArchive(st._24,st._25,st._26,st._27)),
  st._28,
  st._29
  ),st => Some(//    基本信息
    st._1,
    st._2,
    st._3,
    st._4,
    st._5,
    st._6,
    st._7,
    //    技术部门审批
    st._8,
    st._9,
    st._10,
    st._11,
    //    产品部门审批
    st._12,
    st._13,
    st._14,
    //    提请反馈与提示
    st._15,
    st._16,
    st._17,
    st._18,
    st._19,
    st._20,
    st._21,
    st._22,
    //    反馈确认
    st._23,
    //    归档
    st._24,
    st._25,
    st._26,
    st._27,
    st._28,
    st._29))
}

class TimeChangeTable(tag: Tag) extends Table[TimeChange](tag, "T_TS_TIMECHANGE") {
  val sequence = Sequence[Long]("TIMECHANGE_ID")

  def id = column[Long]("ID", O.PrimaryKey)

  def trackingId = column[Long]("TRACKING_ID")

  def devScheDate = column[DateTime]("DEV_SCHE_DATE", O.Nullable)

  def psgScheDate = column[DateTime]("PSG_SCHE_DATE", O.Nullable)

  def devDsScheDate = column[DateTime]("DEV_DS_SCHE_DATE", O.Nullable)

  def devDdScheDate = column[DateTime]("dev_Dd_Sche_Date", O.Nullable)

  def devDtScheDate = column[DateTime]("DEV_DT_SCHE_DATE", O.Nullable)

  def psgDsScheDate = column[DateTime]("PSG_DS_SCHE_DATE", O.Nullable)

  def psgIdScheDate = column[DateTime]("PSG_ID_SCHE_DATE", O.Nullable)

  def _type = column[String]("TYPE", O.Nullable)

  def * : ProvenShape[TimeChange] = (id.?,
    trackingId.?,
    devScheDate.?,
    psgScheDate.?,
    devDsScheDate.?,
    devDdScheDate.?,
    devDtScheDate.?,
    psgDsScheDate.?,
    psgIdScheDate.?,
    _type.?) <>(TimeChange.tupled, TimeChange.unapply)
}

class TrackingTable(tag: Tag) extends Table[Tracking](tag, "T_TS_TRACKING") {
  def sequence = Sequence[Long]("SEQ_TS_TRACK")

  def id = column[Long]("ID", O.PrimaryKey)

  def trackingDate = column[DateTime]("TRACKING_DATE")

  def newProcess = column[String]("NEW_PROCESS")

  def stId = column[Long]("ST_ID")

  def approvalCode = column[String]("APPROVAL_CODE")

  def _type = column[String]("TYPE")

  def * : ProvenShape[Tracking] = (id.?, trackingDate, newProcess, stId,
    approvalCode, _type) <>(Tracking.tupled, Tracking.unapply)
}

class SupervisionTable(tag: Tag) extends Table[Supervision](tag, "T_TS_SUPERVISION") {
  def sequence = Sequence[Long]("SEQ_TS_SUPERVISION")

  def supervisionSuggestion = column[String]("SUPERVISION_SUGGESTION")

  def supervisionPersion = column[Long]("SUPERVISION_PERSION")

  def supervisionDate = column[DateTime]("SUPERVISION_DATE")

  def stId = column[Long]("ST_ID")

  def id = column[Long]("ID")

  def * : ProvenShape[Supervision] = (supervisionSuggestion,
    supervisionPersion,
    supervisionDate,
    stId.?,
    id.?
    ) <>(Supervision.tupled, Supervision.unapply)
}
