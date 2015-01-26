package service.techsupport

import dao.systemmanage.{DepartmentDaoComponent, UserDaoComponent, DictItemDaoComponent}
import dao.techsupport._
import models.slick.techsupport._
import models.squeryl.CommonTypeMode
import CommonTypeMode._
import models.systemmanage.{Department, User, DictItemQueryCondition}
import models.techsupport._
import org.jbpm.api.{Configuration, _}
import org.jbpm.pvm.internal.task.TaskImpl
import util.Page

trait SupportTicketServiceComponentImpl extends SupportTicketServiceComponent {
  this: SupportTicketDaoComponent =>

  class SupportTicketServiceImpl extends SupportTicketService {
    def insert(e: SupportTicket): SupportTicket = inTransaction {
      supportTicketDao.insert(e)
    }

    def update(e: SupportTicket): Unit = inTransaction {
      supportTicketDao.update(e)
    }

    def deleteById(id: Long): Unit = inTransaction {
      supportTicketDao.deleteById(id)
    }

    def page(pageno: Int, pagesize: Int, params: SupportTicketQueryCondition, sort: String, dir: String): Page[SupportTicket] =
      inTransaction {
        supportTicketDao.page(pageno, pagesize, params, sort, dir)
      }

    def getById(id: Long): Option[SupportTicket] = inTransaction {
      supportTicketDao.getById(id)
    }

    def list(params: SupportTicketQueryCondition): List[SupportTicket] = inTransaction {
      supportTicketDao.list(params)
    }
  }

}

trait SupervisionServiceComponentImpl extends SupervisionServiceComponent {
  this: SupervisionDaoComponent =>

  class SupervionServiceImpl extends SupervisionService {
    def insert(e: Supervision): Supervision = inTransaction {
      supervisionDao.insert(e)
    }

    def update(e: Supervision): Unit = inTransaction {
      supervisionDao.update(e)
    }

    def deleteById(id: Long): Unit = inTransaction {
      supervisionDao.deleteById(id)
    }

    def page(pageno: Int, pagesize: Int, params: SupervisionQuery, sort: String, dir: String): Page[Supervision] =
      inTransaction {
        supervisionDao.page(pageno, pagesize, params, sort, dir)
      }

    def getById(id: Long): Option[Supervision] = inTransaction {
      supervisionDao.getById(id)
    }

    def list(params: SupervisionQuery): List[Supervision] = inTransaction {
      supervisionDao.list(params)
    }
  }

}

trait TrackingServiceComponentImpl extends TrackingServiceComponent {
  this: TrackingDaoComponent =>

  class TrackingServiceImpl extends TrackingService {
    def insert(e: Tracking): Tracking = inTransaction {
      trackingDao.insert(e)
    }

    def update(e: Tracking): Unit = inTransaction {
      trackingDao.update(e)
    }

    def deleteById(id: Long): Unit = inTransaction {
      trackingDao.deleteById(id)
    }

    def page(pageno: Int, pagesize: Int, params: TrackingQueryCondition, sort: String, dir: String): Page[Tracking] =
      inTransaction {
        trackingDao.page(pageno, pagesize, params, sort, dir)
      }

    def getById(id: Long): Option[Tracking] = inTransaction {
      trackingDao.getById(id)
    }

    def list(params: TrackingQueryCondition): List[Tracking] = inTransaction {
      trackingDao.list(params)
    }
  }

}

trait TimeChangeServiceComponentImpl extends TimeChangeServiceComponent {
  this: TimeChangeDaoComponent =>

  class TimeChangeServiceImpl extends TimeChangeService {
    def insert(e: TimeChange): TimeChange = inTransaction {
      timeChangeDao.insert(e)
    }

    def update(e: TimeChange): Unit = inTransaction {
      timeChangeDao.update(e)
    }

    def deleteById(id: Long): Unit = inTransaction {
      timeChangeDao.deleteById(id)
    }

    def page(pageno: Int, pagesize: Int, params: TimeChangeQuery, sort: String, dir: String): Page[TimeChange] =
      inTransaction {
        timeChangeDao.page(pageno, pagesize, params, sort, dir)
      }

    def getById(id: Long): Option[TimeChange] = inTransaction {
      timeChangeDao.getById(id)
    }

    def list(params: TimeChangeQuery): List[TimeChange] = inTransaction {
      timeChangeDao.list(params)
    }
  }

}

trait WorksheetServiceComponentImpl extends WorksheetServiceComponent {
  this: SupportTicketDaoComponent
    with DictItemDaoComponent
    with UserDaoComponent
    with SupportLeaderDaoComponent
    with SupportDepartmentDaoComponent
    with DepartmentDaoComponent
    with WorksheetDaoComponent
    with JbpmTaskDaoComponent =>

  import scala.collection.JavaConversions._

  class WorksheetServiceImpl extends WorksheetService {
    private val procoessEngine = Configuration.getProcessEngine
    val repositoryService = procoessEngine.getRepositoryService
    val executionService = procoessEngine.getExecutionService
    val taskService = procoessEngine.getTaskService
    val identityService = procoessEngine.getIdentityService
    val historyService = procoessEngine.getHistoryService

    def getWorksheet(taskId: String): Option[Worksheet] = {
      val task = jbpmTaskDao.getById(taskId.toLong)  match {
        case Some(t) =>
          t
        case None =>
          throw new RuntimeException("编号为"+taskId+"的任务在系统中不存在")
      }
      val worksheetno = executionService.getVariable(task.executionId_, "worksheetno").asInstanceOf[Long]
      val st = supportTicketDao.getById(worksheetno).get
      val regionDictItems = dictItemDao.list(DictItemQueryCondition(dictcode = Some("dm_ts_regin")))
      val stStatusDictItems = dictItemDao.list(DictItemQueryCondition(dictcode = Some("dm_ts_status")))
      val applicantUser = userDao.getById(st.applicantId)
      val supportLeaderStr = supportLeaderDao.list(SupportLeaderQuery(st.id, None, None))
        .map(sl => userDao.getById(sl.slId).getOrElse(User(
        0,
        "",
        "",
        "",
        None,
        None,
        1,
        "Y",
        None,
        None,
        None,
        None,
        None
      )).username).filter("" != _).mkString(",")

      val supportDepartmentStr = supportDepartmentDao.list(SupportDepartmentQuery(st.id, None, None))
        .map(sd => departmentDao.getById(sd.deptId).getOrElse(
        Department(
          "",
          "",
          0,
          "",
          None,
          0,
          "Y",
          None,
          None,
          None,
          None
        )
      ).departname)
        .filter(_ != "").mkString(",")

      Option(Worksheet(st, task, taskId, task.activity_name_.getOrElse(""), task.name_,
        regionDictItems.filter(_.factValue == st.region).map(_.displayName).get(0),
        applicantUser match {
          case Some(u) => u.username
          case None => ""
        },
        stStatusDictItems.filter(_.factValue == st.stStatus).map(_.displayName).get(0),
        Option(supportLeaderStr),
        Option(supportDepartmentStr)
      ))
    }

    def next(taskId: String, params: Map[String, Any]): Unit = {
      taskService.completeTask(taskId, params)
    }

    def page(pageno: Int, pageSize: Int, params: WorksheetQuery, sort: String, dir: String): Page[Worksheet] = inTransaction {
      worksheetDao.page(pageno,pageSize,params,sort,dir)
    }

    def next(taskId: String, transition: String, params: Map[String, Any]): Unit = {
      taskService.completeTask(taskId, transition, params)
    }

    def start(processName: String, params: Map[String, Any]): Unit = inTransaction {
      val pdList = repositoryService.createProcessDefinitionQuery()
        .processDefinitionName(processName)
        .orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION).list()
      if (pdList.size() > 0)
        executionService.startProcessInstanceById(pdList(0).getId, params)
    }

    def deploy(processDeclareXmlPath: String): String = inTransaction {
      val deployment = repositoryService.createDeployment().addResourceFromClasspath(processDeclareXmlPath)
      deployment.deploy()
    }

  }

}
