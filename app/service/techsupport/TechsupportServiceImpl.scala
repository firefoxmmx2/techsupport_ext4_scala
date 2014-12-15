package service.techsupport

import dao.techsupport.{SupervisionDaoComponent, SupportTicketDaoComponent, TimeChangeDaoComponent, TrackingDaoComponent}
import models.CommonTypeMode._
import models.techsupport._
import org.jbpm.api.{Configuration, _}
import util.Page

trait SupportTicketServiceComponentImpl extends SupportTicketServiceComponent{
  this: SupportTicketDaoComponent=>
  class SupportTicketServiceImpl extends SupportTicketService {
    def insert(e: SupportTicket): SupportTicket = inTransaction{
      supportTicketDao.insert(e)
    }

    def update(e: SupportTicket): Unit = inTransaction{
      supportTicketDao.update(e)
    }

    def deleteById(id: Long): Unit = inTransaction{
      supportTicketDao.deleteById(id)
    }

    def page(pageno: Int, pagesize: Int, params: SupportTicketQueryCondition, sort: String, dir: String): Page[SupportTicket] =
    inTransaction{
      supportTicketDao.page(pageno,pagesize,params,sort,dir)
    }

    def getById(id: Long): Option[SupportTicket] = inTransaction{
      supportTicketDao.getById(id)
    }

    def list(params: SupportTicketQueryCondition): List[SupportTicket] = inTransaction{
      supportTicketDao.list(params)
    }
  }
}

trait SupervisionServiceComponentImpl extends SupervisionServiceComponent{
  this:SupervisionDaoComponent=>
  class SupervionServiceImpl extends SupervisionService {
    def insert(e: Supervision): Supervision = inTransaction{
      supervisionDao.insert(e)
    }

    def update(e: Supervision): Unit = inTransaction{
      supervisionDao.update(e)
    }

    def deleteById(id: Long): Unit = inTransaction{
      supervisionDao.deleteById(id)
    }

    def page(pageno: Int, pagesize: Int, params: SupervisionQuery, sort: String, dir: String): Page[Supervision] =
    inTransaction{
      supervisionDao.page(pageno,pagesize,params,sort,dir)
    }

    def getById(id: Long): Option[Supervision] = inTransaction{
      supervisionDao.getById(id)
    }

    def list(params: SupervisionQuery): List[Supervision] = inTransaction{
      supervisionDao.list(params)
    }
  }
}

trait TrackingServiceComponentImpl extends TrackingServiceComponent{
  this: TrackingDaoComponent=>
  class TrackingServiceImpl extends TrackingService {
    def insert(e: Tracking): Tracking = inTransaction{
      trackingDao.insert(e)
    }

    def update(e: Tracking): Unit = inTransaction{
      trackingDao.update(e)
    }

    def deleteById(id: Long): Unit = inTransaction{
      trackingDao.deleteById(id)
    }

    def page(pageno: Int, pagesize: Int, params: TrackingQueryCondition, sort: String, dir: String): Page[Tracking] =
    inTransaction{
      trackingDao.page(pageno,pagesize,params,sort,dir)
    }

    def getById(id: Long): Option[Tracking] = inTransaction{
      trackingDao.getById(id)
    }

    def list(params: TrackingQueryCondition): List[Tracking] = inTransaction{
      trackingDao.list(params)
    }
  }
}

trait TimeChangeServiceComponentImpl extends TimeChangeServiceComponent{
  this:TimeChangeDaoComponent=>
  class TimeChangeServiceImpl extends TimeChangeService {
    def insert(e: TimeChange): TimeChange = inTransaction{
      timeChangeDao.insert(e)
    }

    def update(e: TimeChange): Unit = inTransaction{
      timeChangeDao.update(e)
    }

    def deleteById(id: Long): Unit = inTransaction{
      timeChangeDao.deleteById(id)
    }

    def page(pageno: Int, pagesize: Int, params: TimeChangeQuery, sort: String, dir: String): Page[TimeChange] =
    inTransaction{
      timeChangeDao.page(pageno,pagesize,params,sort,dir)
    }

    def getById(id: Long): Option[TimeChange] = inTransaction{
      timeChangeDao.getById(id)
    }

    def list(params: TimeChangeQuery): List[TimeChange] = inTransaction{
      timeChangeDao.list(params)
    }
  }
}

trait WorksheetServiceComponentImpl extends WorksheetServiceComponent{
  this : SupportTicketDaoComponent=>
  import scala.collection.JavaConversions._
  class WorksheetServiceImpl extends WorksheetService {
    private val procoessEngine = Configuration.getProcessEngine
     val repositoryService=procoessEngine.getRepositoryService
     val executionService=procoessEngine.getExecutionService
     val taskService=procoessEngine.getTaskService
     val identityService=procoessEngine.getIdentityService
     val historyService=procoessEngine.getHistoryService

    def getWorksheet(taskId: String): Option[Worksheet] = inTransaction{
      val task = taskService.getTask(taskId)

      val worksheetno=executionService.getVariable(task.getExecutionId,"worksheetno").asInstanceOf[Long]
      val st=supportTicketDao.getById(worksheetno)

    }

    def next(taskId: String, params: Map[String, Any]): Unit = {
      taskService.completeTask(taskId,params)
    }

    def page(pageno: Int, pageSize: Int, params: WorksheetQuery, sort: String, dir: String): Page[Worksheet] = ???

    def next(taskId: String, transition: String, params: Map[String, Any]): Unit = {
      taskService.completeTask(taskId,transition,params)
    }

    def start(processName: String,params:Map[String,Any]): Unit = inTransaction{
      val pdList=repositoryService.createProcessDefinitionQuery()
        .processDefinitionName(processName)
        .orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION).list()
      if(pdList.size() > 0)
        executionService.startProcessInstanceById(pdList(0).getId,params)
    }

    def deploy(processDeclareXmlPath: String): String = inTransaction{
      val deployment=repositoryService.createDeployment().addResourceFromClasspath(processDeclareXmlPath)
      deployment.deploy()
    }
  }
}
