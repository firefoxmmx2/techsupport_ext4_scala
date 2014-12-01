package test

import models.techsupport._
import play.api.test._
import play.api.test.Helpers._
import org.specs2.mutable._
import util.ComponentRegister
import models.CommonTypeMode._

/**
 * 技术支持单测试用力.
 */
class TechsupportSpec extends Specification with ComponentRegister {
  "techsupport system" should {
    "query a SupportTickets list" in {
      running(FakeApplication()) {
        val list = inTransaction {
          //           from(Techsupport.baseSupportTickets,
          //             Techsupport.departmentApprovals,
          //             Techsupport.trackingApprovals,
          //             Techsupport.feedbacks,
          //             Techsupport.archives)(
          //               (b,d,r,f,a) =>
          //                 where(b.id === d.id and r.id === b.id and f.id === b.id and a.id === b.id)
          //                   select(SupportTicket(b,Option(d),Option(r),Option(f),Option(a),None,None,None))
          //             ).toList
          join(Techsupport.supportTickets, Techsupport.supportDepartments, Techsupport.supportLeaders)(
            (s, sd, sl) =>

              select(SupportTicket(
                BaseSupportTicket(s.applicantId,
                  s.supportContent,
                  s.stStatus,
                  s.region,
                  s.serialNumber,
                  s.lastUpdateDate,
                  s.id),
                s.id match {
                  case Some(x) => Some(DepartmentApproval(
                    s.id.getOrElse(0),
                    s.devScheDate,
                    s.psgScheDate,
                    s.devDsScheDate,
                    s.devDdScheDate,
                    s.devDtScheDate,
                    s.psgDsScheDate,
                    s.psgIsScheDate
                  ))
                  case None =>
                    None
                },
                !s.id.isEmpty && !s.applyingFeedbackDate.isEmpty match {
                  case true =>
                    Some(TrackingApproval(
                      s.id.get,
                      s.applyingFeedbackDate.get,
                      s.psgCompDate,
                      s.devCompDate,
                      s.psgDsCompDate,
                      s.psgIsCompDate,
                      s.devDsCompDate,
                      s.devDdCompDate,
                      s.devDtCompDate
                    ))
                  case false =>
                    None
                },
                !s.id.isEmpty && !s.feedbackConfirmDate.isEmpty match {
                  case true =>
                    Some(Feedback(s.id.get,
                      s.feedbackConfirmDate.get))
                  case false =>
                    None
                },

                !s.id.isEmpty &&
                  !s.comments.isEmpty &&
                  !s.archiveCode.isEmpty &&
                  !s.archiveDate.isEmpty &&
                  !s.archiveUserId.isEmpty match {
                  case true =>
                    Some(Archive(
                      s.id.get,
                      s.comments.get,
                      s.archiveCode.get,
                      s.archiveDate.get,
                      s.archiveUserId.get
                    ))
                  case false =>
                    None
                },
                //               None,
                //              None,
                //              None,
                None,
                None,
                None
              ))
                on (s.id === sd.stId and s.id === sl.stId)
          ).toList
        }
        println("=" * 13 + "supportTickets list.size is " + list.size + "=" * 13)
        println("=" * 13 + "supportTickets list(0).support_content" + list(0).supportContent + "=" * 13)
        list.size must be > 0
      }
    }
  }
}
