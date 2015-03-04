package test

import models.squeryl.techsupport.{Techsupport, SupportTicketP}
import models.squeryl.CommonTypeMode
import models.squeryl.systemmanage.SystemManage
import models.techsupport._
import org.joda.time.DateTime
import play.api.test._
import play.api.test.Helpers._
import org.specs2.mutable._
import util.ComponentRegister
import CommonTypeMode._

/**
 * 技术支持单测试用力.
 */
class TechsupportSpec extends Specification with ComponentRegister {
  "techsupport system" should {
    "query a SupportTickets list" in {
      running(FakeApplication()) {
        val slist = inTransaction {
          val subquery = from(Techsupport.supportTickets)(
            s =>
              select(s.id)
          ).page(1, 5)
          subquery.toList
        }
        val list = inTransaction {
          val subQuery = from(Techsupport.supportTickets)(
            s =>
              select(s)
          ).page(1, 5)
          val stList = subQuery.toList
          val stIdLst = stList.map(_.id.get)
          val query = from(
            Techsupport.supportDepartments,
            SystemManage.departments
          )((sd, d) =>
            where(sd.deptId === d.id and (sd.stId in (stIdLst)))
              select(sd, d)
            )
          val sdAndDepartments = query.toList
          val querySupportLeader = from(Techsupport.supportLeaders,
            SystemManage.users)(
              (sl, u) =>
                where(sl.stId in (stIdLst) and sl.slId === u.id)
                  select(sl, u)
            )
          val slAndUsers = querySupportLeader.toList
          val applicantUsers = from(SystemManage.users)(
            u =>
              where(u.id in stList.map(_.applicantId))
                select (u)
          ).toList
          val archiveUsers = from(SystemManage.users)(
            u =>
              where(u.id in stList.map(_.archiveUserId.getOrElse(0)))
                select (u)
          ).toList
          stList.map {
            st =>
              val departments = sdAndDepartments.filter(sdd => sdd._1.stId == st.id.get)
                .map(sdd => sdd._2.convertTo)
              val supportLeaders = slAndUsers.filter(slu => slu._1.stId == st.id.get)
                .map(slu => slu._2.convertTo)
              val applicantPerson = applicantUsers.filter(_.id.get == st.applicantId) match {
                case lUsers if (lUsers.size > 0) =>
                  Some(lUsers(0).convertTo)
                case _ =>
                  None
              }
              val archivePerson = archiveUsers.filter(_.id == st.archiveUserId) match {
                case lUsers if (lUsers.size > 0) =>
                  Some(lUsers(0).convertTo)
                case _ =>
                  None
              }
              SupportTicketP(st,
                applicant = applicantPerson,
                archivePerson = archivePerson,
                lSupportDepartments = departments,
                lSupportLeaders = supportLeaders)
          }
        }
        println("=" * 13 + "slist.size" + slist.size + "=" * 13)
        println("=" * 13 + "supportTickets list.size is " + list.size + "=" * 13)
        println("=" * 13 + "supportTickets list(0)" + list(0) + "=" * 13)
        list.size must be > 0
      }
    }
    "test SupportTicketDao " in {
      running(FakeApplication()) {
        inTransaction {
          val stList = supportTicketDao.list(SupportTicketQueryCondition())
          stList.size must be > 0
          println("=" * 13 + "newSupportTicket.supportContent = " + stList(0).supportContent + "=" * 13)
          val newSupportTicket = stList(0).copy(id = None,
            psgScheDate = None,
            devScheDate = None,
            supportContent = Some("测试"),
            stNo = "重庆-20150114-2"
          )
          val insertedSupportTicket = supportTicketDao.insert(newSupportTicket)
          stList.size must be < supportTicketDao.list(SupportTicketQueryCondition()).size
          supportTicketDao.deleteById(insertedSupportTicket.id.get)
          supportTicketDao.getById(insertedSupportTicket.id.get) must be empty
        }
      }
    }

    "test WorksheetDao " in {
      running(FakeApplication()) {
        inTransaction {
          val worksheets = worksheetDao.page(1, 5, WorksheetQuery(st = Some(SupportTicketQuery(stStatus = Some("going")))))
          worksheets.total must be > 0
          println("=" * 13 + "[worksheet]=" + worksheets.data.map(worksheet => {
            (worksheet.taskId,
              worksheet.activityName,
              worksheet.st.stNo,
              worksheet.applicantName,
              worksheet.regionName,
              worksheet.stStatusName,
              worksheet.supportLeaderName,
              worksheet.supportDeptName
              )
          }) + "=" * 13)
          println("=" * 13 + "[worksheet.size]=" + worksheets.data.size + "=" * 13)
          worksheets.data.size must be be_=== 5
        }
      }
    }

    "test supportticket apply" in {
      running(FakeApplication()) {
        inTransaction {
          val bst = BaseSupportTicket(
            601,
            "重庆-20150304-1",
            "测试",
            "待公司审批",
            "chongqing",
            1,
            DateTime.now()
          )
          println("="*13+"+"*13+"="*13)
          worksheetService.applySupportTicket(bst)
          println("="*13+"-"*13+"="*13)
          val workflowProcessName=globalParamService.getById(Constants.GlobalParams.GlobalCodes.TECHSUPPORT_WORKFLOW)
            .map(_.globalparvalue)
          println("="*13+"*"*13+"="*13)
          val worksheets=worksheetService.page(1,20,WorksheetQuery(activity = workflowProcessName,
            st = Some(SupportTicketQuery(
            stNo = Some("重庆-20150304-1")
            ))))

          worksheets.total must be be_=== 1
        }
      }
    }
  }
}
