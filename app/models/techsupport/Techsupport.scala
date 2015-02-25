package models.techsupport

import models.TQueryable
import models.systemmanage.{Department, User}
import org.joda.time.DateTime


case class SupportTicketQuery(
                               region: Option[String] = None,
                               id: Option[Long] = None,
                               stStatus: Option[String] = None
                               )

case class AttachmentQuery(
                            attachmentName: Option[String],
                            stId: Option[Long],
                            attachmentContentType: Option[String],
                            id: Option[Long]
                            ) extends TQueryable

case class SupervisionQuery(
                             stId: Option[Long],
                             id: Option[Long]
                             ) extends TQueryable

case class SupportDepartmentQuery(
                                   stId: Option[Long],
                                   deptId: Option[Long],
                                   id: Option[Long]
                                   ) extends TQueryable

case class SupportLeaderQuery(
                               stId: Option[Long],
                               slId: Option[Long],
                               slDepartId: Option[Long]
                               ) extends TQueryable

case class SupportTicketQueryCondition(
                                        id: Option[Long] = None,
                                        applicantId: Option[Long] = None,
                                        stStatus: Option[String] = None,
                                        region: Option[String] = None,
                                        devScheDate: Option[DateTime] = None,
                                        psgScheDate: Option[DateTime] = None,
                                        psgCompDate: Option[DateTime] = None,
                                        devCompDate: Option[DateTime] = None
                                        ) extends TQueryable

case class TimeChangeQuery(
                            trackingId: Option[Long],
                            type_ : Option[String],
                            id: Option[Long]
                            ) extends TQueryable

case class TrackingQueryCondition(
                                   stId: Option[Long],
                                   trackingDate: Option[DateTime],
                                   type_ : Option[String],
                                   approvalCode: Option[String],
                                   processorId: Option[Long],
                                   id: Option[Long]
                                   ) extends TQueryable

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
                       attachmentName: String,
                       attachmentComment: Option[String],
                       attachmentSize: String,
                       stId: Long,
                       attachmentContent: Array[Byte],
                       attachmentContentType: String,
                       attachmentPath: String,
                       uploadTime: DateTime,
                       batchNumber: String,
                       id: Option[Long] = None
                       )

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
                              stNo:String,
                              supportContent: String,
                              stStatus: String,
                              region: String,
                              serialNumber: Int,
                              lastUpdateDate: DateTime,
                              id: Option[Long] = None,
                              attachments: List[Attachment] = Nil
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
                               tracking: Tracking,
                               devScheDate: Option[DateTime],
                               psgScheDate: Option[DateTime],
                               devDsScheDate: Option[DateTime],
                               devDdScheDate: Option[DateTime],
                               devDtScheDate: Option[DateTime],
                               psgDsScheDate: Option[DateTime],
                               psgIsScheDate: Option[DateTime]
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
 * 追踪批复
 */
case class TrackingApproval(
                             id: Long,
                             applyingFeedbackDate: DateTime,
                             tracking: Tracking,
                             psgCompDate: Option[DateTime],
                             devCompDate: Option[DateTime],
                             psgDsCompDate: Option[DateTime],
                             psgIsCompDate: Option[DateTime],
                             devDsCompDate: Option[DateTime],
                             devDdCompDate: Option[DateTime],
                             devDtCompDate: Option[DateTime]
                             )

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
                     stId: Long,
                     trackingDate: DateTime,
                     newProcess: String,
                     type_ : String,
                     approvalCode: String,
                     processorId: Long,
                     id: Option[Long]
                     )

/**
 * 反馈确认
 * @param id
 * @param feedbackConfirmDate
 */
case class Feedback(
                     id: Long,
                     tracking: Tracking,
                     feedbackConfirmDate: DateTime
                     )

/**
 * 督办
 * @param supervisionSuggetion 督办建议
 * @param supervisionPersonId 督办人
 * @param supervisionDate 督办时间
 * @param stId 支持单ID
 * @param id 主键
 */
case class Supervision(
                        supervisionSuggetion: String,
                        supervisionPersonId: Long,
                        supervisionDate: DateTime,
                        stId: Long,
                        id: Option[Long]
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
                     val applicantId: Long,
                     val stNo: String,
                     val supportContent: Option[String] = None,
                     val stStatus: String,
                     val region: String,
                     val serialNumber: Int,
                     val devScheDate: Option[DateTime] = None,
                     val psgScheDate: Option[DateTime] = None,
                     val devDsScheDate: Option[DateTime] = None,
                     val devDdScheDate: Option[DateTime] = None,
                     val devDtScheDate: Option[DateTime] = None,
                     val psgDsScheDate: Option[DateTime] = None,
                     val psgIsScheDate: Option[DateTime] = None,
                     val applyingFeedbackDate: Option[DateTime] = None,
                     val psgCompDate: Option[DateTime] = None,
                     val devCompDate: Option[DateTime] = None,
                     val psgDsCompDate: Option[DateTime] = None,
                     val psgIsCompDate: Option[DateTime] = None,
                     val devDsCompDate: Option[DateTime] = None,
                     val devDdCompDate: Option[DateTime] = None,
                     val devDtCompDate: Option[DateTime] = None,
                     val feedbackConfirmDate: Option[DateTime] = None,
                     val comments: Option[String] = None,
                     val archiveCode: Option[String] = None,
                     val archiveDate: Option[DateTime] = None,
                     val archiveUserId: Option[Long] = None,
                     val lastUpdateDate: DateTime,
                     val id: Option[Long] = None,
                     val applicant: Option[User]=None,
                     val archivePerson: Option[User]=None,
                     val lTrackings: List[Tracking] = List(),
                     val lSupportDepartments: List[Department] = List(),
                     val lSupportLeaders: List[User] = List(),
                     val lSupervision: List[Supervision] = List(),
                     val lAttachments:List[Attachment] = List()
                     ) {
  def copy(
            applicantId: Long = this.applicantId,
            stNo: String = this.stNo,
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
            id: Option[Long] = this.id,
            applicant: Option[User] = this.applicant,
            archivePerson: Option[User] = this.archivePerson,
            lTrackings: List[Tracking] = this.lTrackings,
            lSupportDepartments: List[Department] = this.lSupportDepartments,
            lSupportLeaders: List[User] = this.lSupportLeaders,
            lSupervision: List[Supervision] = this.lSupervision,
            lAttachments:List[Attachment] = List()
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
    id,
    applicant,
    archivePerson,
    lTrackings,
    lSupportDepartments,
    lSupportLeaders,
    lSupervision,
    lAttachments
  )

  def canEqual(other: Any): Boolean = other.isInstanceOf[SupportTicket]

  override def toString: String = s"SupportTicket(" +
    s"$applicantId," +
    s"$stNo," +
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
                       trackingId: Long,
                       devScheDate: Option[DateTime],
                       psgScheDate: Option[DateTime],
                       devDsScheDate: Option[DateTime],
                       devDdScheDate: Option[DateTime],
                       devDtScheDate: Option[DateTime],
                       psgDsScheDate: Option[DateTime],
                       psgIsScheDate: Option[DateTime],
                       type_ : String,
                       id: Option[Long]
                       )


/**
 * 支持部门
 * @param stId
 * @param deptId
 * @param id
 */
case class SupportDepartment(
                              stId: Long,
                              deptId: Long,
                              id: Option[Long]
                              )

/**
 * 支持单负责人
 * @param stId
 * @param slId
 * @param slDepartId
 */
case class SupportLeader(
                          stId: Long,
                          slId: Long,
                          slDepartId: Long
                          )