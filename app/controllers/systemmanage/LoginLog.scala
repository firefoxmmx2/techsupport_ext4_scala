package controllers.systemmanage

import play.api.Logger
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

/**
 * 登录日志
 */
object LoginLog extends Controller {
  val log = Logger.logger

  /**
   * 添加登录日志
   * @param id
   * @return
   */
  def add(id: Long) = TODO

  /**
   * 修改登录日志
   * @param id
   * @return
   */
  def update(id:Long)=TODO

  /**
   * 删除日至
   * @param id
   * @return
   */
  def delete(id:Long)=TODO

  /**
   * 获取日志详情
   * @param id
   * @return
   */
  def get(id:Long)=TODO

  /**
   * 获取日志列表
   * @return
   */
  def list=TODO

}
