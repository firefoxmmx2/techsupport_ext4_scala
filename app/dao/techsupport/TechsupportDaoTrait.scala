package dao.techsupport

import dao.BaseDao
import models.QueryCondition
import models.techsupport._
import util.Page

/**
 * 技术支持单主要表单dao
 */
trait SupportTicketDaoComponent {
  trait SupportTicketDao extends BaseDao[SupportTicket,Long,SupportTicketQueryCondition]
  val supportTicketDao:SupportTicketDao
}

/**
 * 追踪批复DAO
 */
trait TrackingDaoComponent {
  trait TrackingDao extends BaseDao[Tracking,Long,TrackingQueryCondition]
  val trackingDao:TrackingDao
}

/**
 * 督办DAO
 */
trait SupervisionDaoComponent {
  trait SupervisionDao extends BaseDao[Supervision,Long, SupervisionQuery]
  val supervisionDao:SupervisionDao
}

/**
 * 支持部门DAO
 */
trait SupportDepartmentDaoComponent{
  trait SupportDepartmentDao extends BaseDao[SupportDepartment,Long,SupportDepartmentQuery]
  val supportDepartmentDao:SupportDepartmentDao
}

/**
 * 支持负责人DAO
 */
trait SupportLeaderDaoComponent {
  trait SupportLeaderDao extends BaseDao[SupportLeader,(Long,Long),SupportLeaderQuery]
  val supportLeaderDao:SupportLeaderDao
}

/**
 * 附件DAO
 */
trait AttachmentDaoComponent{
  trait AttachmentDao extends BaseDao[Attachment,Long,AttachmentQuery]
  val attachmentDao:AttachmentDao
}

/**
 * 计划时间改变
 */
trait TimeChangeDaoComponent{
  trait TimeChangeDao extends BaseDao[TimeChange,Long,TimeChangeQuery]
  val timeChangeDao:TimeChangeDao
}

/**
 * 工作单
 */
trait WorksheetDaoComponent {
  trait WorksheetDao {
    def page(pageno:Int,pagesize:Int,worksheetQuery: WorksheetQuery,sort:String,dir:String):Page[Worksheet]
  }
  val worksheetDao:WorksheetDao
}
