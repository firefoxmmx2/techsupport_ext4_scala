package service.techsupport

import models.techsupport.{TrackingQueryCondition, Tracking, SupportTicketQueryCondition, SupportTicket}
import service.BaseService

/**
 * 支持单
 */
trait SupportTicketServiceComponent {
  trait SupportTicketService extends BaseService[SupportTicket,Long,SupportTicketQueryCondition]{

  }

  val supportTicketService:SupportTicketService
}

/**
 * 进展情况
 */
trait TrackingServiceComponent {
  trait TrackingService extends BaseService[Tracking,Long,TrackingQueryCondition]{

  }

  val trackingService:TrackingService
}

/**
 * 流程工作单
 */
trait WorksheetServiceComponent {
  trait WorksheetService {

  }
  val worksheetService:WorksheetService
}

