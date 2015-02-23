package controllers.techsupport

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
/**
 * Created by hooxin on 15-2-23.
 */
object Worksheet extends Controller {
  /**
   * 申请支持单
   * @return
   */
  def applySupport = TODO

  /**
   * 公司审批
   * @return
   */
  def ceAppr = TODO

  /**
   * 部门审批
   * @return
   */
  def departmentAppr=TODO

  /**
   * 追踪批复
   * @return
   */
  def tracking=TODO

  /**
   * 提请反馈
   * @return
   */
  def applyFeedback = TODO

  /**
   * 反馈确认
   * @return
   */
  def feedbackConfirm=TODO

  /**
   * 归档
   * @return
   */
  def archive = TODO

  def getWorksheet(taskId:String) = TODO

  def list = TODO

  def deployWorkFlow = TODO

  def deleteWorkFlow = TODO
}
