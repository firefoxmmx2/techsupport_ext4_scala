package test

import models.SystemManage
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
          println(stIdLst)
          val query = from(
            Techsupport.supportDepartments,
            SystemManage.departments
          )((sd, d) =>
            where(sd.deptId === d.id and (sd.stId in(stIdLst)))
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
          val archiveUsers=from(SystemManage.users)(
            u=>
              where(u.id in stList.map(_.archiveUserId.get))
            select(u)
          ).toList
          stList.map {
            st =>
              val departments = sdAndDepartments.filter(sdd => Option(sdd._1.stId)  == st.id)
                .map(sdd => sdd._2)
//              val supportLeaders = slAndUsers.filter(slu => slu._1.stId == st.id.get)
//                .map(slu => slu._2)
//              val applicantPerson= applicantUsers.filter(_.id.get == st.applicantId) match {
//                case lUsers if(lUsers.size > 0)  =>
//                  Some(lUsers(0))
//                case _ =>
//                  None
//              }
//              val archivePerson=archiveUsers.filter(_.id.get == st.archiveUserId) match {
//                case lUsers if(lUsers.size >0)=>
//                  Some(lUsers(0))
//                case _=>
//                  None
//              }
                        st
//              SupportTicketP(st,
//                applicant=applicantPerson,
//                archivePerson=archivePerson,
//                lSupportDepartments = departments,
//                lSupportLeaders = supportLeaders)
          }
        }
        println("=" * 13 + "slist.size" + slist.size + "=" * 13)
        println("=" * 13 + "supportTickets list.size is " + list.size + "=" * 13)
        println("=" * 13 + "supportTickets list(0)" + list(0) + "=" * 13)
        list.size must be > 0
      }
    }
  }
}
