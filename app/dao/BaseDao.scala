package dao

import scala.slick.lifted.{CanBeQueryCondition, Query}
import util.Page

/**
 * Created by hooxin on 14-3-7.
 */
import com.typesafe.slick.driver.oracle.OracleDriver.simple.Session
trait BaseDao [M,L] {
  case class MaybeFilter[X, Y](val query: Query[X, Y]) {
    def filter[T, R: CanBeQueryCondition](data: Option[T])(f: T => X => R) = {
      data.map(v => MaybeFilter(query.filter(f(v)))).getOrElse(this)
    }
  }

  /**
   * 插入
   * @param m 实体
   * @param session 会话
   * @return 插入后的实体
   */
  def insert(m:M)(implicit session:Session):M

  /**
   * 修改
   * @param m 实体
   * @param session 会话
   * @return
   */
  def update(m:M)(implicit session:Session):Unit

  /**
   * 删除
   * @param m 实体
   * @param session 会话
   * @return
   */
  def delete(m:M)(implicit session:Session):Unit

  /**
   * 通过主键删除
   * @param id 主键
   * @param session 会话
   * @return
   */
  def deleteById(id:L)(implicit session:Session):Unit

  /**
   * 通过主键获取单个实体
   * @param id 主键
   * @param session 会话
   * @return 实体
   */
  def getById(id:L)(implicit session:Session):M

  /**
   * 分页总数查询
   * @param params 分页查询条件
   * @param session 会话
   * @return 结果数
   */
  def count(params:Map[String,Object])(implicit session:Session):Int

  /**
   * 非分页查询
   * @param params 查询条件
   * @param session 会话
   * @return 结果列表
   */
  def list(params:Map[String,Object])(implicit session:Session):List[M]

  /**
   * 分页查询
   * @param pageno 页码
   * @param pagesize 每页显示数
   * @param params 分页查询条件
   * @param sort 排序字段
   * @param dir 升降序
   * @param session 会话
   * @return 分页结果
   */
  def page(pageno:Int,pagesize:Int,params:Map[String,Object],sort:String,dir:String)(implicit session:Session):Page[M]
}
