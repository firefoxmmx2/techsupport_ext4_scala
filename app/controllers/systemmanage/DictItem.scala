package controllers.systemmanage

import play.api.Logger
import play.api.mvc.{Action, Controller}

/**
 * 字典项
 */
object DictItem extends Controller{
  val log=Logger.logger

  /**
   * 列表查询
   * @return
   */
  def list = Action {
    implicit  request=>
      log.debug("="*13+" 开始字典项列表查询... "+"="*13)
      val pageno=request.getQueryString("page").getOrElse("1").toInt
      val limit=request.getQueryString("limit").getOrElse("20").toInt
      log.debug("="*13+s" pageno = $pageno,limit = $limit "+"="*13)
      // todo 字典项列表查询内容

      log.debug("="*13+" 结束字典项列表查询..."+"="*13)
      Ok()
  }
}
