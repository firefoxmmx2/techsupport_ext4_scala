package dao.techsupport

import dao.BaseDao
import models.QueryCondition
import models.techsupport.{SupportTicketQueryCondition, Tracking, SupportTicket}

/**
 *
 */
trait SupportTicketDaoComponent {
  trait SupportTicketDao extends BaseDao[SupportTicket,Long,SupportTicketQueryCondition]
  val supportTicketDao:SupportTicketDao
}

trait TrackingDaoComponent {
  trait TrackingDao extends BaseDao[Tracking,Long,QueryCondition]
  val trackingDao:TrackingDao
}

trait
