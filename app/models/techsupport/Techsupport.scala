package models.techsupport

import models.QueryCondition
import models.systemmanage.{User, Department}
import org.joda.time.DateTime
import org.squeryl._
import org.squeryl.annotations._
import models.CommonTypeMode._

/**
 * 支持单基本信息
 * @param applicantId
 * @param supportContent
 * @param stStatus
 * @param region
 * @param serialNumber
 * @param id
 */
case class BaseSupportTicket(
                              applicantId: Long,
                              supportContent: String,
                              stStatus: String,
                              region: String,
                              serialNumber: Int,
                              lastUpdateDate: DateTime,
                              id: Option[Long] = None
                              )


/**
 * 部门审批
 * @param id
 * @param devScheDate
 * @param psgScheDate
 * @param devDsScheDate
 * @param devDdScheDate
 * @param devDtScheDate
 * @param psgDsScheDate
 * @param psgIsScheDate
 */
case class DepartmentApproval(
                               id: Long,
                               devScheDate: Option[DateTime],
                               psgScheDate: Option[DateTime],
                               devDsScheDate: Option[DateTime],
                               devDdScheDate: Option[DateTime],
                               devDtScheDate: Option[DateTime],
                               psgDsScheDate: Option[DateTime],
                               psgIsScheDate: Option[DateTime]
                               )

/**
 * 追踪批复
 */
case class TrackingApproval(
                             id: Long,
                             applyingFeedbackDate: DateTime,
                             psgCompDate: Option[DateTime],
                             devCompDate: Option[DateTime],
                             psgDsCompDate: Option[DateTime],
                             psgIsCompDate: Option[DateTime],
                             devDsCompDate: Option[DateTime],
                             devDdCompDate: Option[DateTime],
                             devDtCompDate: Option[DateTime]
                             )

/**
 * 反馈确认
 * @param id
 * @param feedbackConfirmDate
 */
case class Feedback(
                     id: Long,
                     feedbackConfirmDate: DateTime
                     )

/**
 * 归档
 * @param id
 * @param comments
 * @param archiveCode
 * @param archiveDate
 * @param archiveUserId
 */
case class Archive(
                    id: Long,
                    comments: String,
                    archiveCode: String,
                    archiveDate: DateTime,
                    archiveUserId: Long
                    ) {
}

/**
 * 技术支持单结构化
 * @param supportTicket
 * @param applicant
 * @param archivePerson
 * @param lTrackings
 * @param lSupportDepartments
 * @param lSupportLeaders
 */
case class SupportTicketP(
                           supportTicket: SupportTicket,
                           applicant: Option[User],
                           archivePerson: Option[User],
                           lTrackings: List[Tracking] = List(),
                           lSupportDepartments: List[Department] = List(),
                           lSupportLeaders: List[User] = List(),
                           lSupervision:List[Supervision]=List()
                           )

/**
 * 技术支持单表对象
 * @param applicantId
 * @param supportContent
 * @param stStatus
 * @param region
 * @param serialNumber
 * @param devScheDate
 * @param psgScheDate
 * @param devDsScheDate
 * @param devDdScheDate
 * @param devDtScheDate
 * @param psgDsScheDate
 * @param psgIsScheDate
 * @param applyingFeedbackDate
 * @param psgCompDate
 * @param devCompDate
 * @param psgDsCompDate
 * @param psgIsCompDate
 * @param devDsCompDate
 * @param devDdCompDate
 * @param devDtCompDate
 * @param feedbackConfirmDate
 * @param comments
 * @param archiveCode
 * @param archiveDate
 * @param archiveUserId
 * @param lastUpdateDate
 * @param id
 */
class SupportTicket(
                     @Column("applicant")
                     val applicantId: Long,
                     @Column("st_no")
                     val stNo: String,
                     @Column("support_content")
                     val supportContent: Option[String]=None,
                     @Column("st_status")
                     val stStatus: String,
                     val region: String,
                     @Column("serial_number")
                     val serialNumber: Int,
                     @Column("dev_sche_date")
                     val devScheDate: Option[DateTime]=None,
                     @Column("psg_sche_date")
                     val psgScheDate: Option[DateTime]=None,
                     @Column("dev_ds_sche_date")
                     val devDsScheDate: Option[DateTime]=None,
                     @Column("dev_dd_sche_date")
                     val devDdScheDate: Option[DateTime]=None,
                     @Column("dev_dt_sche_date")
                     val devDtScheDate: Option[DateTime]=None,
                     @Column("psg_ds_sche_date")
                     val psgDsScheDate: Option[DateTime]=None,
                     @Column("psg_Is_sche_date")
                     val psgIsScheDate: Option[DateTime]=None,
                     @Column("applying_feedback_date")
                     val applyingFeedbackDate: Option[DateTime]=None,
                     @Column("psg_comp_date")
                     val psgCompDate: Option[DateTime]=None,
                     @Column("dev_comp_date")
                     val devCompDate: Option[DateTime]=None,
                     @Column("psg_ds_comp_date")
                     val psgDsCompDate: Option[DateTime]=None,
                     @Column("psg_is_comp_date")
                     val psgIsCompDate: Option[DateTime]=None,
                     @Column("dev_ds_comp_date")
                     val devDsCompDate: Option[DateTime]=None,
                     @Column("dev_dd_comp_date")
                     val devDdCompDate: Option[DateTime]=None,
                     @Column("dev_dt_comp_date")
                     val devDtCompDate: Option[DateTime]=None,
                     @Column("feedback_confirm_date")
                     val feedbackConfirmDate: Option[DateTime]=None,
                     val comments: Option[String]=None,
                     @Column("archive_code")
                     val archiveCode: Option[String]=None,
                     @Column("archive_date")
                     val archiveDate: Option[DateTime]=None,
                     @Column("archive_userid")
                     val archiveUserId: Option[Long]=None,
                     @Column("last_update_date")
                     val lastUpdateDate: DateTime,
                     val id: Option[Long] = None
                     ) extends KeyedEntity[Option[Long]] {
  def copy(
            applicantId: Long = this.applicantId,
            stNo:String = this.stNo,
            supportContent: Option[String] = this.supportContent,
            stStatus: String = this.stStatus,
            region: String = this.region,
            serialNumber: Int = this.serialNumber,
            devScheDate: Option[DateTime] = this.devScheDate,
            psgScheDate: Option[DateTime] = this.psgScheDate,
            devDsScheDate: Option[DateTime] = this.devDsScheDate,
            devDdScheDate: Option[DateTime] = this.devDdScheDate,
            devDtScheDate: Option[DateTime] = this.devDtScheDate,
            psgDsScheDate: Option[DateTime] = this.psgDsScheDate,
            psgIsScheDate: Option[DateTime] = this.psgIsScheDate,
            applyingFeedbackDate: Option[DateTime] = this.applyingFeedbackDate,
            psgCompDate: Option[DateTime] = this.psgCompDate,
            devCompDate: Option[DateTime] = this.devCompDate,
            psgDsCompDate: Option[DateTime] = this.psgDsCompDate,
            psgIsCompDate: Option[DateTime] = this.psgIsCompDate,
            devDsCompDate: Option[DateTime] = this.devDsCompDate,
            devDdCompDate: Option[DateTime] = this.devDdCompDate,
            devDtCompDate: Option[DateTime] = this.devDtCompDate,
            feedbackConfirmDate: Option[DateTime] = this.feedbackConfirmDate,
            comments: Option[String] = this.comments,
            archiveCode: Option[String] = this.archiveCode,
            archiveDate: Option[DateTime] = this.archiveDate,
            archiveUserId: Option[Long] = this.archiveUserId,
            lastUpdateDate: DateTime = this.lastUpdateDate,
            id: Option[Long] = this.id
            ) = new SupportTicket(
    applicantId,
    stNo,
    supportContent,
    stStatus,
    region,
    serialNumber,
    devScheDate,
    psgScheDate,
    devDsScheDate,
    devDdScheDate,
    devDtScheDate,
    psgDsScheDate,
    psgIsScheDate,
    applyingFeedbackDate,
    psgCompDate,
    devCompDate,
    psgDsCompDate,
    psgIsCompDate,
    devDsCompDate,
    devDdCompDate,
    devDtCompDate,
    feedbackConfirmDate,
    comments,
    archiveCode,
    archiveDate,
    archiveUserId,
    lastUpdateDate,
    id)

  def canEqual(other: Any): Boolean = other.isInstanceOf[SupportTicket]

  override def toString: String = s"SupportTicket(" +
    s"$applicantId," +
    s"$stNo,"+
    s"$supportContent," +
    s"$stStatus," +
    s"$region," +
    s"$serialNumber," +
    s"$devScheDate," +
    s"$psgScheDate," +
    s"$devDsScheDate," +
    s"$devDdScheDate," +
    s"$devDtScheDate," +
    s"$psgDsScheDate," +
    s"$psgIsScheDate," +
    s"$applyingFeedbackDate," +
    s"$psgCompDate," +
    s"$devCompDate," +
    s"$psgDsCompDate," +
    s"$psgIsCompDate," +
    s"$devDsCompDate," +
    s"$devDdCompDate," +
    s"$devDtCompDate," +
    s"$feedbackConfirmDate," +
    s"$comments," +
    s"$archiveCode," +
    s"$archiveDate," +
    s"$archiveUserId," +
    s"$lastUpdateDate," +
    s"$id," + s")"


  override def equals(other: Any): Boolean = other match {
    case that: SupportTicket =>
      super.equals(that) &&
        (that canEqual this) &&
        applicantId == that.applicantId &&
        stNo == that.stNo &&
        supportContent == that.supportContent &&
        stStatus == that.stStatus &&
        region == that.region &&
        serialNumber == that.serialNumber &&
        devScheDate == that.devScheDate &&
        psgScheDate == that.psgScheDate &&
        devDsScheDate == that.devDsScheDate &&
        devDdScheDate == that.devDdScheDate &&
        devDtScheDate == that.devDtScheDate &&
        psgDsScheDate == that.psgDsScheDate &&
        psgIsScheDate == that.psgIsScheDate &&
        applyingFeedbackDate == that.applyingFeedbackDate &&
        psgCompDate == that.psgCompDate &&
        devCompDate == that.devCompDate &&
        psgDsCompDate == that.psgDsCompDate &&
        psgIsCompDate == that.psgIsCompDate &&
        devDsCompDate == that.devDsCompDate &&
        devDdCompDate == that.devDdCompDate &&
        devDtCompDate == that.devDtCompDate &&
        feedbackConfirmDate == that.feedbackConfirmDate &&
        comments == that.comments &&
        archiveCode == that.archiveCode &&
        archiveDate == that.archiveDate &&
        archiveUserId == that.archiveUserId &&
        lastUpdateDate == that.lastUpdateDate &&
  id == that.id
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(super.hashCode(), applicantId, stNo, supportContent, stStatus, region, serialNumber, devScheDate, psgScheDate, devDsScheDate, devDdScheDate, devDtScheDate, psgDsScheDate, psgIsScheDate, applyingFeedbackDate, psgCompDate, devCompDate, psgDsCompDate, psgIsCompDate, devDsCompDate, devDdCompDate, devDtCompDate, feedbackConfirmDate, comments, archiveCode, archiveDate, archiveUserId, lastUpdateDate, id)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

/**
 * 支持单查询结构
 * @param id
 * @param applicantId
 * @param stStatus
 * @param region
 * @param devScheDate
 * @param psgScheDate
 * @param psgCompDate
 * @param devCompDate
 */
case class SupportTicketQueryCondition(
                                        id: Option[Long] = None,
                                        applicantId: Option[Long] = None,
                                        stStatus: Option[String] = None,
                                        region: Option[String] = None,
                                        devScheDate: Option[DateTime] = None,
                                        psgScheDate: Option[DateTime] = None,
                                        psgCompDate: Option[DateTime] = None,
                                        devCompDate: Option[DateTime] = None
                                        ) extends QueryCondition

/**
 * 追踪批复或者进展情况
 * @param stId
 * @param trackingDate
 * @param newProcess
 * @param type_
 * @param approvalCode
 * @param processorId
 * @param id
 */
case class Tracking(
                     @Column("st_id")
                     stId: Long,
                     @Column("tracking_date")
                     trackingDate: DateTime,
                     @Column("new_process")
                     newProcess: String,
                     @Column("type")
                     type_ : String,
                     @Column("approval_code")
                     approvalCode: String,
                     @Column("processor_id")
                     processorId: Long,
                     id: Option[Long]
                     ) extends KeyedEntity[Option[Long]]

case class TrackingQueryCondition(
                                   stId: Option[Long],
                                   trackingDate: Option[DateTime],
                                   type_ : Option[String],
                                   approvalCode: Option[String],
                                   processorId: Option[Long],
                                   id: Option[Long]
                                   ) extends QueryCondition

/**
 * 支持部门
 * @param stId
 * @param deptId
 * @param id
 */
case class SupportDepartment(
                              @Column("st_id")
                              stId: Long,
                              @Column("dept_id")
                              deptId: Long,
                              id: Option[Long]
                              ) extends KeyedEntity[Option[Long]]

case class SupportDepartmentQuery(
                                   stId: Option[Long],
                                   deptId: Option[Long],
                                   id: Option[Long]
                                   ) extends QueryCondition

/**
 * 支持单负责人
 * @param stId
 * @param slId
 * @param slDepartId
 */
case class SupportLeader(
                          @Column("st_id")
                          stId: Long,
                          @Column("sl_id")
                          slId: Long,
                          @Column("sl_departid")
                          slDepartId: Long
                          ) {
  def id = compositeKey(stId, slId)
}

case class SupportLeaderQuery(
                               stId: Option[Long],
                               slId: Option[Long],
                               slDepartId: Option[Long]
                               ) extends QueryCondition

/**
 * 附件
 * @param attachmentName
 * @param attachmentComment
 * @param attachmentSize
 * @param stId
 * @param attachmentContent
 * @param attachmentContentType
 * @param attachmentPath
 * @param uploadTime
 * @param batchNumber
 * @param id
 */
case class Attachment(
                       @Column("attachment_name")
                       attachmentName: String,
                       @Column("attachment_comment")
                       attachmentComment: Option[String],
                       @Column("attachment_size")
                       attachmentSize: String,
                       @Column("st_id")
                       stId: Long,
                       @Column("attachment_content")
                       attachmentContent: Array[Byte],
                       @Column("attachment_content_type")
                       attachmentContentType: String,
                       @Column("attachment_path")
                       attachmentPath: String,
                       @Column("update_time")
                       uploadTime: DateTime,
                       @Column("batch_number")
                       batchNumber: String,
                       id: Option[Long] = None
                       ) extends KeyedEntity[Option[Long]]

case class AttachmentQuery(
                            attachmentName: Option[String],
                            stId: Option[Long],
                            attachmentContentType: Option[String],
                            id: Option[Long]
                            ) extends QueryCondition

/**
 * 督办
 * @param supervisionSuggetion 督办建议
 * @param supervisionPersonId 督办人
 * @param supervisionDate 督办时间
 * @param stId 支持单ID
 * @param id 主键
 */
case class Supervision(
                        @Column("supervision_suggestion")
                        supervisionSuggetion: String,
                        @Column("supervision_person")
                        supervisionPersonId: Long,
                        @Column("supervision_date")
                        supervisionDate: DateTime,
                        @Column("st_id")
                        stId: Long,
                        id: Option[Long]
                        ) extends KeyedEntity[Option[Long]]

case class SupervisionQuery(
                             stId: Option[Long],
                             id: Option[Long]
                             ) extends QueryCondition

/**
 * 修改计划时间的轨迹
 * @param trackingId
 * @param devScheDate
 * @param psgScheDate
 * @param devDsScheDate
 * @param devDdScheDate
 * @param devDtScheDate
 * @param psgDsScheDate
 * @param psgIsScheDate
 * @param type_ 改变时间类型: 0 产品 1 技术
 * @param id
 */
case class TimeChange(
                       @Column("tracking_id")
                       trackingId: Long,
                       @Column("dev_sche_date")
                       devScheDate: Option[DateTime],
                       @Column("psg_sche_date")
                       val psgScheDate: Option[DateTime],
                       @Column("dev_ds_sche_date")
                       val devDsScheDate: Option[DateTime],
                       @Column("dev_dd_sche_date")
                       val devDdScheDate: Option[DateTime],
                       @Column("dev_dt_sche_date")
                       val devDtScheDate: Option[DateTime],
                       @Column("psg_ds_sche_date")
                       val psgDsScheDate: Option[DateTime],
                       @Column("psg_Is_sche_date")
                       val psgIsScheDate: Option[DateTime],
                       @Column("type")
                       type_ : String,
                       id: Option[Long]
                       ) extends KeyedEntity[Option[Long]]

case class TimeChangeQuery(
                            trackingId: Option[Long],
                            type_ : Option[String],
                            id: Option[Long]
                            ) extends QueryCondition

/**
 * JBPM流程指派候选控制表
 * @param id
 * @param dbversion_
 * @param groupid_
 * @param userid_
 * @param type_
 * @param task_
 * @param swimlane_
 */
case class JbpmParticipation(
                            @Column("dbid_")
                            id : Option[Long],
                            dbversion_ : Int,
                            groupid_ : Option[String],
                            userid_ : Option[String],
                            type_ : String,
                            task_ : Long,
                            swimlane_ : Option[String]
                              ) extends KeyedEntity[Option[Long]]

/**
 * JBPM流程任务表
 * @param id
 * @param class_
 * @param dbversion_
 * @param name_
 * @param descr_
 * @param state_
 * @param supsphiststate_
 * @param assignee_
 * @param form_
 * @param priority_
 * @param creaet_
 * @param duedate_
 * @param progress_
 * @param signalling_
 * @param executionId_
 * @param activityName_
 * @param hasvars_
 * @param supertask_
 * @param execution_
 * @param procinst_
 * @param swimlane_
 * @param taskdefname_
 */
case class JbpmTask(
                   @Column("dbid_")
                   id: Option[Long],
                   class_ : Long,
                   dbversion_ : Int,
                   name_ : String,
                   descr_ : Option[String],
                   state_ : String,
                   supsphiststate_ : Option[String],
                   assignee_ : Option[String],
                   form_ : Option[String],
                   priority_ : Int,
                   creaet_ : DateTime,
                   duedate_ : Option[DateTime],
                   progress_ : Option[String],
                   signalling_ : Int,
                   @Column("execution_id_")
                   executionId_ : String,
                   activityName_ : Option[String],
                   hasvars_ : Int,
                   supertask_ : Option[Long],
                   execution_ : Long,
                   procinst_ : Long,
                   swimlane_ : Option[String],
                   taskdefname_ : String
                     ) extends KeyedEntity[Option[Long]]

/**
 * JBPM流程变量表,里面存放流程里面说设置的变量值
 * @param id
 * @param class_
 * @param dbversion_
 * @param key_
 * @param converter_
 * @param hist_
 * @param execution_
 * @param task_
 * @param lob_
 * @param date_value_
 * @param double_value_
 * @param classname_
 * @param long_value_
 * @param string_value_
 * @param text_value_
 * @param exesys_
 */
case class JbpmVariable(
                       @Column("dbid_")
                       id:Option[String],
                       class_ :String,
                       dbversion_ : Int,
                       key_ : String,
                       converter_ : Option[String],
                       hist_ : Int,
                       execution_ : Long,
                       task_ : Option[Long],
                       lob_ : Option[Array[Byte]],
                       date_value_ : Option[DateTime],
                       double_value_ : Option[Double],
                       classname_ : Option[String],
                       long_value_ : Option[Long],
                       string_value_ : Option[String],
                       text_value_ : Option[String],
                       exesys_ : Option[String]
                         )
case object Techsupport extends Schema {
  val supportTickets = table[SupportTicket]("t_ts_tech_support")
  on(supportTickets)(s => declare(s.id is(autoIncremented("SEQ_TS_ST"), primaryKey)))
  val trackings = table[Tracking]("t_ts_tracking")
  on(trackings)(t => declare(t.id is(autoIncremented("seq_ts_track"), primaryKey)))
  val supportDepartments = table[SupportDepartment]("T_TS_STDEPT_MAPPING")
  on(supportDepartments)(d => declare(d.id is(autoIncremented("SEQ_TS_STDEPT"), primaryKey)))
  val supportLeaders = table[SupportLeader]("T_TS_STSL_MAP")
  val attachments = table[Attachment]("T_TS_SUPPORT_ATTACHMENT")
  on(attachments)(a => declare(a.id is(autoIncremented("SEQ_TS_ATTACH"), primaryKey)))
  val supervisions = table[Supervision]("t_ts_supervision")
  on(supervisions)(s => declare(s.id is(autoIncremented("SEQ_TS_SUPERVISION"), primaryKey)))
  val timeChanges = table[TimeChange]("t_ts_timechange")
  on(timeChanges)(t => declare(t.id is(autoIncremented("timechange_id"), primaryKey)))
  val jbpmParticipations = table[JbpmParticipation]("Jbpm4_Participation")
  val jbpmTasks = table[JbpmTask]("Jbpm4_Task")
  val jbpmVariables = table[JbpmVariable]("Jbpm4_Variable")
}