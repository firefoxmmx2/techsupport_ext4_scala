package service.techsupport

import models.techsupport._
import service.BaseService
import util.Page

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
    def getWorksheet(taskId:String):Option[Worksheet]
    def goNext(taskId:String,params:Map[String,Any])
    def page(pageno:Int,pageSize:Int,params:WorksheetQuery,sort:String,dir:String):Page[Worksheet]

  }
  val worksheetService:WorksheetService
}

/**
 * 督办
 */
trait SupervisionServiceComponent {
  trait SupervisionService extends BaseService[Supervision,Long,SupervisionQuery]{
  }

  val supervisionService:SupervisionService
}

/**
 * 改变计划时间
 */
trait TimeChangeServiceComponent {
  trait TimeChangeService extends BaseService[TimeChange,Long,TimeChangeQuery] {

  }

  val timeChangeService:TimeChangeService
}