package models.techsupport

import org.jbpm.pvm.internal.task.TaskImpl


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
                    st:SupportTicket,
                    task:TaskImpl,
                    taskId:Long,
                    activityName:String="",
                    activity:String="",
                    regionName:String="",
                    applicantName:String="",
                    stStatusName:String="",
                    supportLeaderName:Option[String]=None,
                    supportDeptName:Option[String]=None
                      )

