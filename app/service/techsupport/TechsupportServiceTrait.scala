package service.techsupport

import models.slick.techsupport._
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
    val DEPLOY_XML_PATH_TECHSUPPORT="util.workflow.techsupport.jpdl.xml"

    def getWorksheet(taskId:String):Option[Worksheet]
    def next(taskId:String,params:Map[String,Any])
    def page(pageno:Int,pageSize:Int,params:WorksheetQuery,sort:String,dir:String):Page[Worksheet]
    def next(taskId:String,transition:String,params:Map[String,Any])
    def start(processName:String,params:Map[String,Any])
    def deploy(processDeclareXmlPath:String):String
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