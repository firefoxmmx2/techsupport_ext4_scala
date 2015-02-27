package models.techsupport

import models.squeryl.techsupport.JbpmTask


/**
 * 流程工作单
 * @param st
 * @param task
 * @param taskId
 * @param activityName
 * @param regionName
 * @param applicantName
 * @param supportLeaderName
 * @param supportDeptName
 */
case class Worksheet(
                      st: SupportTicket,
                      task: JbpmTask,
                      taskId: String,
                      activityName: String = "",
                      activity: String = "",
                      regionName: String = "",
                      applicantName: String = "",
                      stStatusName: String = "",
                      supportLeaderName: Option[String] = None,
                      supportDeptName: Option[String] = None
                      )

case class WorksheetQuery(
                           taskId: Option[Long] = None,
                           activity: Option[String] = None,
                           process: Option[String] = None,
                           st: Option[SupportTicketQuery] = None
                           )


