package models

import org.squeryl.annotations._
import org.joda.time.DateTime
import org.squeryl.{KeyedEntity, Schema}
import CommonTypeMode._

/**
 * Created by hooxin on 14-3-23.
 */
object Techsupport extends Schema {
  val supportTickets = table[SupportTicket]("T_TS_TECH_SUPPORT")
  on(supportTickets)(st => declare(st.id is(autoIncremented("SEQ_TS_ST"), primaryKey)))
  val supervision = table[Supervision]("T_TS_SUPERVISION")
  on(supervision)(s => declare(s.id is(autoIncremented("SEQ_TS_SUPERVISION"), primaryKey)))
  val timeChanges = table[TimeChange]("T_TS_TIMECHANGE")
  on(timeChanges)(t => declare(t.id is (autoIncremented("TIMECHANGE_ID"))))
  val trackings = table[Tracking]("T_TS_TRACKNG")
  on(trackings)(t => declare(t.id is(autoIncremented("SEQ_TS_TRACK"), primaryKey)))

}


class SupportTicket(
                     //基本信息
                     @Column("ST_NO")
                     stNo: String,
                     @Column("APPLICANT")
                     applicant: Long,
                     @Column("SUPPORT_CONTENT")
                     supportContent: String,
                     @ColumnBase("ST_STATUS")
                     stStatus: String,
                     @ColumnBase("REGION")
                     region: String,
                     @ColumnBase("SERIAL_NUMBER")
                     serialNumber: Int,
                     @ColumnBase("APPLY_DATE")
                     applyDate: DateTime = DateTime.now(),
                     //部门审批
                     @ColumnBase("DEV_SCHE_DATE")
                     devScheDate: Option[DateTime] = None,
                     @ColumnBase("DEV_DS_SCHE_DATE")
                     devDsScheDate: Option[DateTime] = None,
                     @ColumnBase("DEV_DD_SCHE_DATE")
                     devDdScheDate: Option[DateTime] = None,
                     @ColumnBase("DEV_DT_SCHE_DATE")
                     devDtScheDate: Option[DateTime] = None,
                     @ColumnBase("PSG_SCHE_DATE")
                     psgScheDate: Option[DateTime] = None,
                     @ColumnBase("PSG_DS_SCHE_DATE")
                     psgDsScheDate: Option[DateTime] = None,
                     @ColumnBase("PSG_IS_SCHE_DATE")
                     psgIsScheDate: Option[DateTime] = None,
                     @ColumnBase("SUPPORT_LEADERS")
                     supportLeaders: Option[List[User]] = None,
                     // 进展提示
                     @ColumnBase("APPLYING_FEEDBACK_DATE")
                     applyingFeedbackDate: Option[DateTime] = None,
                     @ColumnBase("PSG_COMP_DATE")
                     psgCompDate: Option[DateTime] = None,
                     @ColumnBase("DEV_COMP_DATE")
                     devCompDate: Option[DateTime] = None,
                     @ColumnBase("PSG_DS_COMP_DATE")
                     psgDsCompDate: Option[DateTime] = None,
                     @ColumnBase("PSG_IS_COMP_DATE")
                     psgIsCompDate: Option[DateTime] = None,
                     @ColumnBase("DEV_DS_COMP_DATE")
                     devDsCompDate: Option[DateTime] = None,
                     @ColumnBase("DEV_DD_COMP_DATE")
                     devDdCompDate: Option[DateTime] = None,
                     @ColumnBase("DEV_DT_COMP_DATE")
                     devDtCompDate: Option[DateTime] = None,
                     //反馈确认
                     @ColumnBase("FEEDBACK_CONFIRM_DATE")
                     feedbackConfirmDate: Option[DateTime] = None,
                     //归档
                     @ColumnBase("ARCHIVE_CODE")
                     archiveCode: Option[String] = None,
                     @ColumnBase("COMMENTS")
                     comments: Option[String] = None,
                     @ColumnBase("ARCHIVE_DATE")
                     archiveDate: Option[DateTime] = None,
                     @ColumnBase("ARCHIVE_USERID")
                     archiveUserid: Option[Long] = None,
                     supportDepartments: Option[List[Department]] = None,
                     @ColumnBase("LAST_UPDATE_DATE")
                     lastUpdateDate: Option[DateTime] = Option(DateTime.now()),
                     @ColumnBase("ID")
                     stId: Option[Long] = Option(0)) extends KeyedEntity[Option[Long]] {
  def id: Option[Long] = stId
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
            stId: Option[Long] = Option(0)
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
    stId
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
case class TimeChange(@ColumnBase("TRACKING_ID")
                      trackingId: Option[Long] = None,
                      @ColumnBase("DEV_SCHE_DATE")
                      devScheDate: Option[DateTime] = None,
                      @ColumnBase("PSG_SCHE_DATE")
                      psgScheDate: Option[DateTime] = None,
                      @ColumnBase("DEV_DS_SCHE_DATE")
                      devDsScheDate: Option[DateTime] = None,
                      @ColumnBase("DEV_DD_SCHE_DATE")
                      devDdScheDate: Option[DateTime] = None,
                      @ColumnBase("DEV_DT_SCHE_DATE")
                      devDtScheDate: Option[DateTime] = None,
                      @ColumnBase("PSG_DS_SCHE_DATE")
                      psgDsScheDate: Option[DateTime] = None,
                      @ColumnBase("PSG_ID_SCHE_DATE")
                      psgIdScheDate: Option[DateTime] = None,
                      @ColumnBase("TYPE")
                      _type: Option[String] = None,
                      id: Option[Long] = None) extends KeyedEntity[Option[Long]]

/**
 * 进展提示
 * @param trackingId
 * @param trackingDate
 * @param newProcess
 * @param stId
 * @param approvalCode
 * @param _type
 */
case class Tracking(
                     @ColumnBase("TRACKING_DATE")
                     trackingDate: DateTime,
                     @ColumnBase("NEW_PROCESS")
                     newProcess: String,
                     @ColumnBase("ST_ID")
                     stId: Long,
                     @ColumnBase("APPROVAL_CODE")
                     approvalCode: String,
                     @ColumnBase("TYPE")
                     _type: String,
                     @ColumnBase("ID")
                     trackingId: Option[Long] = None) extends KeyedEntity[Option[Long]] {
  def id: Option[Long] = trackingId
}

/**
 * 督办
 * @param supervisionSuggestion
 * @param supervisionPerson
 * @param supervisionDate
 * @param stId
 * @param id
 */
case class Supervision(
                        @ColumnBase("SUPERVISION_SUGGESTION")
                        supervisionSuggestion: String,
                        @ColumnBase("SUPERVISION_PERSON")
                        supervisionPerson: Long,
                        @ColumnBase("SUPERVISION_DATE")
                        supervisionDate: DateTime,
                        @ColumnBase("ST_ID")
                        stId: Option[Long] = None,
                        id: Option[Long] = None) extends KeyedEntity[Option[Long]]