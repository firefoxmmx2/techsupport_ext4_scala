package models.techsupport

import models.{User, Department}
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
                    )

/**
 * 技术支持单
 * @param baseTicket
 * @param departmentApproval
 * @param trackingApproval
 * @param feedbackApproval
 * @param archive
 */
case class SupportTicket(
                          baseTicket: BaseSupportTicket,
                          departmentApproval: Option[DepartmentApproval],
                          trackingApproval: Option[TrackingApproval],
                          feedbackApproval: Option[Feedback],
                          archive: Option[Archive],
                          lTrackings: Option[List[Tracking]],
                          lSupportDepartments: Option[Department],
                          lSupportLeaders: Option[User]
                          )

class SupportTicketTable(
                          @Column("applicant")
                          val applicantId: Long,
                          @Column("support_content")
                          val supportContent: String,
                          @Column("st_status")
                          val stStatus: String,
                          val region: String,
                          @Column("serial_number")
                          val serialNumber: Int,
                          @Column("dev_sche_date")
                          val devScheDate: Option[DateTime],
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
                          @Column("applying_feedback_date")
                          val applyingFeedbackDate: Option[DateTime],
                          @Column("psg_comp_date")
                          val psgCompDate: Option[DateTime],
                          @Column("dev_comp_date")
                          val devCompDate: Option[DateTime],
                          @Column("psg_ds_comp_date")
                          val psgDsCompDate: Option[DateTime],
                          @Column("psg_is_comp_date")
                          val psgIsCompDate: Option[DateTime],
                          @Column("dev_ds_comp_date")
                          val devDsCompDate: Option[DateTime],
                          @Column("dev_dd_comp_date")
                          val devDdCompDate: Option[DateTime],
                          @Column("dev_dt_comp_date")
                          val devDtCompDate: Option[DateTime],
                          @Column("feedback_confirm_date")
                          val feedbackConfirmDate: Option[DateTime],
                          val comments: Option[String],
                          @Column("archive_code")
                          val archiveCode: Option[String],
                          @Column("archive_date")
                          val archiveDate: Option[DateTime],
                          @Column("archive_userid")
                          val archiveUserId: Option[Long],
                          @Column("last_update_date")
                          val lastUpdateDate: DateTime,
                          val id: Option[Long] = None
                          ) extends KeyedEntity[Option[Long]] {
}

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
  def id(stId: Long, slId: Long) = compositeKey(stId, slId)
}

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

case object Techsupport extends Schema {
  val supportTickets = table[SupportTicketTable]("t_ts_tech_support")
  on(supportTickets)(s => declare(s.id is(autoIncremented("SEQ_TS_ST"), primaryKey)))
  val trackings = table[Tracking]("t_ts_tracking")
  on(trackings)(t => declare(t.id is(autoIncremented("seq_ts_track"), primaryKey)))
  val supportDepartments = table[SupportDepartment]("T_TS_STDEPT_MAPPING")
  on(supportDepartments)(d => declare(d.id is(autoIncremented("SEQ_TS_STDEPT"), primaryKey)))
  val supportLeaders = table[SupportLeader]("T_TS_STSL_MAP")
  val attachments = table[Attachment]("T_TS_SUPPORT_ATTACHMENT")
  on(attachments)(a => declare(a.id is(autoIncremented("SEQ_TS_ATTACH"), primaryKey)))
}