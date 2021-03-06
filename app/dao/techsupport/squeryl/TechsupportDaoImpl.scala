package dao.techsupport.squeryl

import dao.techsupport._
import models.squeryl.techsupport.{JbpmTask, Techsupport}
import models.squeryl.CommonTypeMode._
import models.squeryl.systemmanage.SystemManage
import models.techsupport.Constants.DictItems
import models.techsupport._
import util.Page

trait SupportTicketDaoComponentImpl extends SupportTicketDaoComponent {

  class SupportTicketDaoImpl extends SupportTicketDao {
    def selectForPage(params: SupportTicketQueryCondition, sort: String = "id", dir: String = "asc") = {
      from(Techsupport.supportTickets)(
        s =>
          where(s.id === params.id.?
            and params.applicantId.? === s.applicantId
            and params.region.? === s.region
            and params.stStatus.? === s.stStatus)
            select (s.copy(supportContent = None).convertTo)
            orderBy {
            if (sort == "id")
              if (dir == "asc")
                s.id asc
              else
                s.id desc
            else
              s.id asc
          }
      )
    }

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: SupportTicket): SupportTicket = Techsupport.supportTickets.insert(models.squeryl.techsupport.SupportTicket.convertFrom(m)).convertTo

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: SupportTicketQueryCondition): Int = selectForPage(params).Count.toInt

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: SupportTicket): Unit = Techsupport.supportTickets.update(models.squeryl.techsupport.SupportTicket.convertFrom(m))

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: SupportTicket): Unit = Techsupport.supportTickets.deleteWhere(_.id === m.id)

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: Long): Unit = Techsupport.supportTickets.deleteWhere(_.id === id)

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: SupportTicketQueryCondition, sort: String, dir: String): Page[SupportTicket] = {
      val page = Page[SupportTicket](pageno, pagesize, count(params))
      if (page.total == 0)
        page
      else
        page.copy(data = selectForPage(params, sort, dir).page(page.start, page.total).toList)
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: SupportTicketQueryCondition): List[SupportTicket] = {
      //      println("="*13+"supportTicketDao.list is "+selectForPage(params).statement+"="*13)
      selectForPage(params).toList
    }

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: Long): Option[SupportTicket] = from(Techsupport.supportTickets)(
      st =>
        where(st.id === id)
        select(st.convertTo)
    ).singleOption
  }

}

/**
 * 进展审批提示
 */
trait TrackingDaoComponentImpl extends TrackingDaoComponent {

  class TrackingDaoImpl extends TrackingDao {
    def selectForPage(params: TrackingQueryCondition, sort: String = "id", dir: String = "asc") = {
      from(Techsupport.trackings)(
        t =>
          where(params.id.? === t.id
            and params.approvalCode.? === t.approvalCode
            and params.processorId.? === t.processorId
            and params.stId.? === t.processorId
            and params.type_.? === t.type_)
            select (t.convertTo)
            orderBy {
            if (sort == "id")
              if (dir == "asc")
                t.id asc
              else
                t.id desc
            else
              t.id asc
          }
      )
    }

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: Tracking): Tracking = Techsupport.trackings.insert(models.squeryl.techsupport.Tracking.convertFrom(m)).convertTo

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: TrackingQueryCondition): Int = selectForPage(params).Count.toInt

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: Tracking): Unit = Techsupport.trackings.update(models.squeryl.techsupport.Tracking.convertFrom(m))

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: Tracking): Unit = Techsupport.trackings.deleteWhere(_.id === m.id)

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: Long): Unit = Techsupport.trackings.deleteWhere(_.id === id)

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: TrackingQueryCondition, sort: String, dir: String): Page[Tracking] = {
      val page = Page[Tracking](pageno, pagesize, count(params))
      if (page.total == 0)
        page
      else
        page.copy(data = selectForPage(params, sort, dir).page(page.start, page.limit).toList)
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: TrackingQueryCondition): List[Tracking] = selectForPage(params).toList

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: Long): Option[Tracking] = from(Techsupport.trackings)(
      t =>
        where(t.id === id)
        select(t.convertTo)
    ).singleOption
  }

}

trait SupportDepartmentDaoComponentImpl extends SupportDepartmentDaoComponent {

  class SupportDepartmentDaoImpl extends SupportDepartmentDao {
    def selectForPage(params: SupportDepartmentQuery, sort: String = "id", dir: String = "asc") = {
      from(Techsupport.supportDepartments)(
        sd =>
          where(params.deptId.? === sd.deptId
            and params.id.? === sd.id
            and params.stId.? === sd.stId)
            select (sd.convertTo)
            orderBy {
            if (sort == "id")
              if (dir == "asc")
                sd.id asc
              else
                sd.id desc
            else
              sd.id asc
          }
      )
    }

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: SupportDepartment): SupportDepartment = Techsupport.supportDepartments.insert(models.squeryl.techsupport.SupportDepartment.convertFrom(m)).convertTo

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: SupportDepartmentQuery): Int = selectForPage(params).Count.toInt

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: SupportDepartment): Unit = Techsupport.supportDepartments.update(models.squeryl.techsupport.SupportDepartment.convertFrom(m))

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: SupportDepartment): Unit = Techsupport.supportDepartments.deleteWhere(_.id === m.id)

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: Long): Unit = Techsupport.supportDepartments.deleteWhere(_.id === id)

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: SupportDepartmentQuery, sort: String, dir: String): Page[SupportDepartment] = {
      val page = Page[SupportDepartment](pageno, pagesize, count(params))
      if (page.total == 0)
        page
      else
        page.copy(data = selectForPage(params, sort, dir).page(page.start, page.limit).toList)
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: SupportDepartmentQuery): List[SupportDepartment] = selectForPage(params).toList

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: Long): Option[SupportDepartment] = from(Techsupport.supportDepartments)(
      sd =>
        where(sd.id === id)
        select(sd.convertTo)
    ).singleOption
  }

}

trait SupportLeaderDaoComponentImpl extends SupportLeaderDaoComponent {

  class SupportLeaderDaoImpl extends SupportLeaderDao {
    def selectForList(params: SupportLeaderQuery, sort: String = "slId", dir: String = "asc") = {
      from(Techsupport.supportLeaders)(
        sl =>
          where(params.slDepartId.? === sl.slDepartId
            and params.slId.? === sl.slId
            and params.stId.? === sl.stId)
            select (sl.convertTo)
            orderBy {
            if (sort == "slId")
              if (dir == "asc")
                sl.slId asc
              else
                sl.slId desc
            else
              sl.slId asc
          }
      )
    }

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: SupportLeader): SupportLeader = Techsupport.supportLeaders.insert(models.squeryl.techsupport.SupportLeader.convertFrom(m)).convertTo

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: SupportLeaderQuery): Int = selectForList(params).Count.toInt

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: SupportLeader): Unit = Techsupport.supportLeaders.update(models.squeryl.techsupport.SupportLeader.convertFrom(m))

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: SupportLeader): Unit = Techsupport.supportLeaders.deleteWhere(_.id === (m.stId,m.slId))

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: (Long, Long)): Unit = Techsupport.supportLeaders.deleteWhere(_.id === id)

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: SupportLeaderQuery, sort: String, dir: String): Page[SupportLeader] = {
      val page = Page[SupportLeader](pageno, pagesize, count(params))
      if (page.total == 0)
        page
      else
        page.copy(data = selectForList(params, sort, dir).page(page.start, page.limit).toList)
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: SupportLeaderQuery): List[SupportLeader] = selectForList(params).toList

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: (Long, Long)): Option[SupportLeader] = from(Techsupport.supportLeaders)(
      sl =>
        where(sl.id === id)
        select(sl.convertTo)
    ).singleOption
  }

}

trait SupervisionDaoComponentImpl extends SupervisionDaoComponent {

  class SupervisionDaoImpl extends SupervisionDao {
    def selectForPage(params: SupervisionQuery, sort: String = "id", dir: String = "asc") = {
      from(Techsupport.supervisions)(
        s =>
          where(params.id.? === s.id
            and params.stId.? === s.stId)
            select (s.convertTo)
            orderBy {
            if (sort == "id")
              if (dir == "asc")
                s.id asc
              else
                s.id desc
            else
              s.id desc
          }
      )
    }

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: Supervision): Supervision = Techsupport.supervisions.insert(models.squeryl.techsupport.Supervision.convertFrom(m)).convertTo

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: SupervisionQuery): Int = selectForPage(params).Count.toInt

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: Supervision): Unit = Techsupport.supervisions.update(models.squeryl.techsupport.Supervision.convertFrom(m))

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: Supervision): Unit = Techsupport.supervisions.deleteWhere(_.id === m.id)

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: Long): Unit = Techsupport.supervisions.deleteWhere(_.id === id)

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: SupervisionQuery, sort: String, dir: String): Page[Supervision] = {
      val page = Page[Supervision](pageno, pagesize, count(params))
      if (page.total == 0)
        page
      else
        page.copy(data = selectForPage(params, sort, dir).page(page.start, page.limit).toList)
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: SupervisionQuery): List[Supervision] = selectForPage(params).toList

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: Long): Option[Supervision] = from(Techsupport.supervisions)(
      s =>
        where(s.id === id)
        select(s.convertTo)
    ).singleOption
  }

}

trait AttachmentDaoComponentImpl extends AttachmentDaoComponent {

  class AttachmentDaoImpl extends AttachmentDao {
    def selectForPage(params: AttachmentQuery, sort: String = "id", dir: String = "asc") = {
      from(Techsupport.attachments)(
        a =>
          where(params.id.? === a.id
            and params.attachmentContentType.? === a.attachmentContentType
            and params.attachmentName.? === a.attachmentName
            and params.stId.? === a.stId)
            select (a.convertTo)
            orderBy {
            if (sort == "id")
              if (dir == "asc")
                a.id asc
              else
                a.id desc
            else if (sort == "attachmentContentType")
              if (dir == "asc")
                a.attachmentContentType asc
              else
                a.attachmentContentType desc
            else if (sort == "attachmentName")
              if (dir == "asc")
                a.attachmentName asc
              else
                a.attachmentName desc
            else if (sort == "stId")
              if (dir == "asc")
                a.stId asc
              else
                a.stId desc
            else
              a.id asc
          }
      )
    }

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: Attachment): Attachment = Techsupport.attachments.insert(models.squeryl.techsupport.Attachment.convertFrom(m)).convertTo

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: AttachmentQuery): Int = selectForPage(params).Count.toInt

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: Attachment): Unit = Techsupport.attachments.update(models.squeryl.techsupport.Attachment.convertFrom(m))

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: Attachment): Unit = Techsupport.attachments.deleteWhere(_.id === m.id)

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: Long): Unit = Techsupport.attachments.deleteWhere(_.id === id)

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: AttachmentQuery, sort: String, dir: String): Page[Attachment] = {
      val page = Page[Attachment](pageno, pagesize, count(params))
      if (page.total == 0)
        page
      else
        page.copy(data = selectForPage(params, sort, dir).page(page.start, page.limit).toList)
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: AttachmentQuery): List[Attachment] = selectForPage(params).toList

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: Long): Option[Attachment] = from(Techsupport.attachments)(
      a =>
        where(a.id === id)
        select(a.convertTo)
    ).singleOption
  }

}

trait TimeChangeDaoComponentImpl extends TimeChangeDaoComponent {

  class TimeChangeDaoImpl extends TimeChangeDao {
    def selectForPage(params: TimeChangeQuery, sort: String = "id", dir: String = "asc") = {
      from(Techsupport.timeChanges)(
        tc =>
          where(params.id.? === tc.id
            and params.trackingId.? === tc.trackingId
            and params.type_.? === tc.type_)
            select (tc.convertTo)
            orderBy {
            if (sort == "id")
              if (dir == "asc")
                tc.id asc
              else if (sort == "trackingId")
                if (dir == "asc")
                  tc.trackingId asc
                else
                  tc.trackingId desc
              else if (sort == "type_")
                if (dir == "asc")
                  tc.type_ asc
                else
                  tc.type_ desc
              else
                tc.id desc
            else
              tc.id asc
          }
      )
    }

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: TimeChange): TimeChange = Techsupport.timeChanges.insert(models.squeryl.techsupport.TimeChange.convertFrom(m)).convertTo

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: TimeChangeQuery): Int = selectForPage(params).Count.toInt

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: TimeChange): Unit = Techsupport.timeChanges.update(models.squeryl.techsupport.TimeChange.convertFrom(m))

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: TimeChange): Unit = Techsupport.timeChanges.delete(m.id)

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: Long): Unit = Techsupport.timeChanges.deleteWhere(_.id === id)

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: TimeChangeQuery, sort: String, dir: String): Page[TimeChange] = {
      val page = Page[TimeChange](pageno, pagesize, count(params))
      if (page.total == 0)
        page
      else
        page.copy(data = selectForPage(params, sort, dir).page(page.start, page.limit).toList)
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: TimeChangeQuery): List[TimeChange] = selectForPage(params).toList

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: Long): Option[TimeChange] = from(Techsupport.timeChanges)(
      tc =>
        where(tc.id === id)
        select(tc.convertTo)
    ).singleOption
  }

}

/**
 * 工作单
 */
trait WorksheetDaoComponentImpl extends WorksheetDaoComponent {
  class WorksheetDaoImpl extends WorksheetDao {
    def page(pageno: Int, pagesize: Int, worksheetQuery: WorksheetQuery, sort: String="stNo", dir: String="desc"): Page[Worksheet] = {
      val resultQuery = join(
        Techsupport.jbpmTasks,
        Techsupport.jbpmVariables,
        Techsupport.supportTickets,
        Techsupport.jbpmParticipations,
        Techsupport.supportLeaders.leftOuter,
        Techsupport.supportDepartments.leftOuter)(
          (t,v,s,p,sl,sd) =>
            where {
              (worksheetQuery.taskId.? === t.id)
                .and(worksheetQuery.activity.? === t.activity_name_)
                .and(v.key_ === "worksheetno")
                .and(worksheetQuery.st match {
                case Some(st) => (st.region.? === s.region)
                  .and(st.stStatus.? === s.stStatus)
                case None => 1 === 1
              })
            }
            select {
              val supportTicket = new SupportTicket(
                s.applicantId,
                s.stNo,
                None,
                s.stStatus,
                s.region,
                s.serialNumber,
                lastUpdateDate = s.lastUpdateDate,
                id = s.id
              )
              val task = t.copy(descr_ = None)
              (supportTicket,task)
            }
            orderBy {
              if(sort=="stNo")
                if(dir=="asc")
                  s.stNo asc
                else
                  s.stNo desc
              else if(sort=="region")
                if(dir=="asc")
                  s.region asc
                else
                  s.region desc
              else if(sort=="applicantId")
                if(dir=="asc")
                  s.applicantId asc
                else
                  s.applicantId desc
              else if(sort=="supportLeader")
                if(dir=="asc")
                  sl.map(_.slId) asc
                else
                  sl.map(_.slId) desc
              else if(sort=="supportDept")
                if(dir=="asc")
                  sd.map(_.deptId) asc
                else
                  sd.map(_.deptId) desc
              else if(sort=="stStatus")
                if(dir=="asc")
                  s.stStatus asc
                else
                  s.stStatus desc
              else if(sort=="activityName_")
                if(dir=="asc")
                  t.activity_name_ asc
                    else
                  t.activity_name_ desc
              else
                s.stNo desc
            }
              on(t.procinst_ === v.execution_,
              s.id === v.long_value_,
              p.task_ === t.id,
              s.id === sl.map(_.stId),
              s.id === sd.map(_.stId)
              )
        ).distinct
      val page=Page[Worksheet](pageno,pagesize,resultQuery.Count.toInt)
      if(page.total == 0)
        page
      else{
        val data=resultQuery.page(page.start,page.limit).toList.map(r => {
          val st = r._1
          val task = r._2
          val applicantName = from(SystemManage.users)(u =>
            where(u.id === st.applicantId)
              select(u.username)
          ).singleOption.getOrElse("")
          val regionName = from(SystemManage.dictItems)(di =>
            where(di.dictcode === DictItems.DictCodes.REGION
            and di.factValue === st.region)
              select(di.displayName)
          ).singleOption.getOrElse("")
          val stStatusName = from(SystemManage.dictItems)(di =>
            where(di.dictcode === DictItems.DictCodes.STATUS
            and di.factValue === st.stStatus)
              select(di.displayName)
          ).singleOption.getOrElse("")
          val supportLeaderNames=from(SystemManage.users,Techsupport.supportLeaders)(
            (u,sl) =>
              where(sl.slId === u.id and sl.stId === st.id)
                select(u.username)
          ).toList.mkString(",")
          Worksheet(st,task,task.id.toString,task.activity_name_.getOrElse(""),task.name_,regionName,applicantName,stStatusName)
        })
        page.copy(data=data)
      }
    }
  }
}

/**
 * 工作流任务
 */
trait JbpmTaskDaoComponentImpl extends JbpmTaskDaoComponent{
  class JbpmTaskDaoImpl extends JbpmTaskDao{
    def getById(taskId: Long): Option[JbpmTask] = Techsupport.jbpmTasks.where(_.id === taskId).singleOption
  }
}