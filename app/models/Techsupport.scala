package models

import org.squeryl.annotations._
import org.joda.time.DateTime
import org.squeryl.{KeyedEntity, Schema}
import CommonTypeMode._

/**
 * Created by hooxin on 14-3-23.
 */
object Techsupport extends Schema {
  val supportTickets = table[SupportTicket]("t_ts_tech_support")
  on(supportTickets)(st => declare(st.id is(autoIncremented("seq_ts_st"), primaryKey)))
  val supervision = table[Supervision]("t_ts_supervision")
  on(supervision)(s => declare(s.id is(autoIncremented("seq_ts_supervision"), primaryKey)))
  val timeChanges = table[TimeChange]("t_ts_timechange")
  on(timeChanges)(t => declare(t.id is (autoIncremented("timechange_id"))))
  val trackings = table[Tracking]("t_ts_trackng")
  on(trackings)(t => declare(t.id is(autoIncremented("seq_ts_track"), primaryKey)))

}


class SupportTicket(
                     //基本信息
                     @Column("ST_NO")
                     val stNo: String,
                     @Column("APPLICANT")
                     val applicant: Long,
                     @Column("SUPPORT_CONTENT")
                     val supportContent: String,
                     @Column("ST_STATUS")
                     val stStatus: String,
                     @Column("REGION")
                     val region: String,
                     @Column("SERIAL_NUMBER")
                     val serialNumber: Int,
                     @Column("APPLY_DATE")
                     val applyDate: DateTime = DateTime.now(),
                     //部门审批
                     @Column("DEV_SCHE_DATE")
                     val devScheDate: Option[DateTime] = None,
                     @Column("DEV_DS_SCHE_DATE")
                     val devDsScheDate: Option[DateTime] = None,
                     @Column("DEV_DD_SCHE_DATE")
                     val devDdScheDate: Option[DateTime] = None,
                     @Column("DEV_DT_SCHE_DATE")
                     val devDtScheDate: Option[DateTime] = None,
                     @Column("PSG_SCHE_DATE")
                     val psgScheDate: Option[DateTime] = None,
                     @Column("PSG_DS_SCHE_DATE")
                     val psgDsScheDate: Option[DateTime] = None,
                     @Column("PSG_IS_SCHE_DATE")
                     val psgIsScheDate: Option[DateTime] = None,
                     @Column("SUPPORT_LEADERS")
                     val supportLeaders: Option[List[User]] = None,
                     // 进展提示
                     @Column("APPLYING_FEEDBACK_DATE")
                     val applyingFeedbackDate: Option[DateTime] = None,
                     @Column("PSG_COMP_DATE")
                     val psgCompDate: Option[DateTime] = None,
                     @Column("DEV_COMP_DATE")
                     val devCompDate: Option[DateTime] = None,
                     @Column("PSG_DS_COMP_DATE")
                     val psgDsCompDate: Option[DateTime] = None,
                     @Column("PSG_IS_COMP_DATE")
                     val psgIsCompDate: Option[DateTime] = None,
                     @Column("DEV_DS_COMP_DATE")
                     val devDsCompDate: Option[DateTime] = None,
                     @Column("DEV_DD_COMP_DATE")
                     val devDdCompDate: Option[DateTime] = None,
                     @Column("DEV_DT_COMP_DATE")
                     val devDtCompDate: Option[DateTime] = None,
                     //反馈确认
                     @Column("FEEDBACK_CONFIRM_DATE")
                     val feedbackConfirmDate: Option[DateTime] = None,
                     //归档
                     @Column("ARCHIVE_CODE")
                     val archiveCode: Option[String] = None,
                     @Column("COMMENTS")
                     val comments: Option[String] = None,
                     @Column("ARCHIVE_DATE")
                     val archiveDate: Option[DateTime] = None,
                     @Column("ARCHIVE_USERID")
                     val archiveUserid: Option[Long] = None,
                     val supportDepartments: Option[List[Department]] = None,
                     @Column("LAST_UPDATE_DATE")
                     val lastUpdateDate: Option[DateTime] = Option(DateTime.now()),
                     val id: Option[Long] = None) extends KeyedEntity[Option[Long]] {
  def copy(//基本信息
           stNo: String = stNo,
           applicant: Long = applicant,
           supportContent: String = supportContent,
           stStatus: String = stStatus,
           region: String = region,
           serialNumber: Int = serialNumber,
           applyDate: DateTime = applyDate,
           //部门审批
           devScheDate: Option[DateTime] = devScheDate,
           devDsScheDate: Option[DateTime] = devDsScheDate,
           devDdScheDate: Option[DateTime] = devDdScheDate,
           devDtScheDate: Option[DateTime] = devDtScheDate,
           psgScheDate: Option[DateTime] = psgScheDate,
           psgDsScheDate: Option[DateTime] = psgDsScheDate,
           psgIsScheDate: Option[DateTime] = psgIsScheDate,
           supportLeaders: Option[List[User]] = supportLeaders,
           // 进展提示
           applyingFeedbackDate: Option[DateTime] = applyingFeedbackDate,
           psgCompDate: Option[DateTime] = psgCompDate,
           devCompDate: Option[DateTime] = devCompDate,
           psgDsCompDate: Option[DateTime] = psgDsCompDate,
           psgIsCompDate: Option[DateTime] = psgIsCompDate,
           devDsCompDate: Option[DateTime] = devDsCompDate,
           devDdCompDate: Option[DateTime] = devDdCompDate,
           devDtCompDate: Option[DateTime] = devDtCompDate,
           //反馈确认
           feedbackConfirmDate: Option[DateTime] = feedbackConfirmDate,
           //归档
           archiveCode: Option[String] = archiveCode,
           comments: Option[String] = comments,
           archiveDate: Option[DateTime] = archiveDate,
           archiveUserid: Option[Long] = archiveUserid,
           supportDepartments: Option[List[Department]] = supportDepartments,
           lastUpdateDate: Option[DateTime] = lastUpdateDate,
           id: Option[Long] = id) =
    new SupportTicket(
      stNo,
      applicant,
      supportContent,
      stStatus,
      region,
      serialNumber,
      applyDate,
      //部门审批
      devScheDate,
      devDsScheDate,
      devDdScheDate,
      devDtScheDate,
      psgScheDate,
      psgDsScheDate,
      psgIsScheDate,
      supportLeaders,
      // 进展提示
      applyingFeedbackDate,
      psgCompDate,
      devCompDate,
      psgDsCompDate,
      psgIsCompDate,
      devDsCompDate,
      devDdCompDate,
      devDtCompDate,
      //反馈确认
      feedbackConfirmDate,
      //归档
      archiveCode,
      comments,
      archiveDate,
      archiveUserid,
      supportDepartments,
      lastUpdateDate,
      id
    )
}

object SupportTicket {
  def apply(//基本信息
            stNo: String,
            applicant: Long,
            supportContent: String,
            stStatus: String,
            region: String,
            serialNumber: Int,
            applyDate: DateTime = DateTime.now(),
            //部门审批
            devScheDate: Option[DateTime] = None,
            devDsScheDate: Option[DateTime] = None,
            devDdScheDate: Option[DateTime] = None,
            devDtScheDate: Option[DateTime] = None,
            psgScheDate: Option[DateTime] = None,
            psgDsScheDate: Option[DateTime] = None,
            psgIsScheDate: Option[DateTime] = None,
            supportLeaders: Option[List[User]] = None,
            // 进展提示
            applyingFeedbackDate: Option[DateTime] = None,
            psgCompDate: Option[DateTime] = None,
            devCompDate: Option[DateTime] = None,
            psgDsCompDate: Option[DateTime] = None,
            psgIsCompDate: Option[DateTime] = None,
            devDsCompDate: Option[DateTime] = None,
            devDdCompDate: Option[DateTime] = None,
            devDtCompDate: Option[DateTime] = None,
            //反馈确认
            feedbackConfirmDate: Option[DateTime] = None,
            //归档
            archiveCode: Option[String] = None,
            comments: Option[String] = None,
            archiveDate: Option[DateTime] = None,
            archiveUserid: Option[Long] = None,
            supportDepartments: Option[List[Department]] = None,
            lastUpdateDate: Option[DateTime] = Option(DateTime.now()),
            id: Option[Long] = None
             ) = new SupportTicket(
    stNo,
    applicant,
    supportContent,
    stStatus,
    region,
    serialNumber,
    applyDate,
    //部门审批
    devScheDate,
    devDsScheDate,
    devDdScheDate,
    devDtScheDate,
    psgScheDate,
    psgDsScheDate,
    psgIsScheDate,
    supportLeaders,
    // 进展提示
    applyingFeedbackDate,
    psgCompDate,
    devCompDate,
    psgDsCompDate,
    psgIsCompDate,
    devDsCompDate,
    devDdCompDate,
    devDtCompDate,
    //反馈确认
    feedbackConfirmDate,
    //归档
    archiveCode,
    comments,
    archiveDate,
    archiveUserid,
    supportDepartments,
    lastUpdateDate,
    id
  )


}


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
case class TimeChange(@Column("TRACKING_ID")
                      trackingId: Option[Long] = None,
                      @Column("DEV_SCHE_DATE")
                      devScheDate: Option[DateTime] = None,
                      @Column("PSG_SCHE_DATE")
                      psgScheDate: Option[DateTime] = None,
                      @Column("DEV_DS_SCHE_DATE")
                      devDsScheDate: Option[DateTime] = None,
                      @Column("DEV_DD_SCHE_DATE")
                      devDdScheDate: Option[DateTime] = None,
                      @Column("DEV_DT_SCHE_DATE")
                      devDtScheDate: Option[DateTime] = None,
                      @Column("PSG_DS_SCHE_DATE")
                      psgDsScheDate: Option[DateTime] = None,
                      @Column("PSG_ID_SCHE_DATE")
                      psgIdScheDate: Option[DateTime] = None,
                      @Column("TYPE")
                      _type: Option[String] = None,
                      id: Option[Long] = None) extends KeyedEntity[Option[Long]]

/**
 * 进展提示
 * @param trackingDate
 * @param newProcess
 * @param stId
 * @param approvalCode
 * @param _type
 * @param id
 */
case class Tracking(
                     @Column("TRACKING_DATE")
                     trackingDate: DateTime,
                     @Column("NEW_PROCESS")
                     newProcess: String,
                     @Column("ST_ID")
                     stId: Long,
                     @Column("APPROVAL_CODE")
                     approvalCode: String,
                     @Column("TYPE")
                     _type: String,
                     @Column("ID")
                     id: Option[Long] = None) extends KeyedEntity[Option[Long]]

/**
 * 督办
 * @param supervisionSuggestion
 * @param supervisionPerson
 * @param supervisionDate
 * @param stId
 * @param id
 */
case class Supervision(
                        @Column("SUPERVISION_SUGGESTION")
                        supervisionSuggestion: String,
                        @Column("SUPERVISION_PERSON")
                        supervisionPerson: Long,
                        @Column("SUPERVISION_DATE")
                        supervisionDate: DateTime,
                        @Column("ST_ID")
                        stId: Option[Long] = None,
                        id: Option[Long] = None) extends KeyedEntity[Option[Long]]