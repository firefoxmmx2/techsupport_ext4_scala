package dao

import util.Page
import models.TQueryable

/**
 * Created by hooxin on 14-3-7.
 */
trait BaseDao[M, L,Q<:TQueryable] {
  //  case class MaybeFilter[X, Y](val query: Query[X, Y]) {
  //    def filter[T, R: CanBeQueryCondition](data: Option[T])(f: T => X => R) = {
  //      data.map(v => MaybeFilter(query.filter(f(v)))).getOrElse(this)
  //    }
  //  }

  /**
   * 插入
   * @param m 实体

   * @return 插入后的实体
   */
  def insert(m: M): M

  /**
   * 修改
   * @param m 实体

   * @return
   */
  def update(m: M): Unit

  /**
   * 删除
   * @param m 实体

   * @return
   */
  def delete(m: M): Unit

  /**
   * 通过主键删除
   * @param id 主键

   * @return
   */
  def deleteById(id: L): Unit

  /**
   * 通过主键获取单个实体
   * @param id 主键

   * @return 实体
   */
  def getById(id: L): Option[M]

  /**
   * 分页总数查询
   * @param params 分页查询条件

   * @return 结果数
   */
  def count(params : Q ): Int

  /**
   * 非分页查询
   * @param params 查询条件

   * @return 结果列表
   */
  def list(params: Q): List[M]

  /**
   * 分页查询
   * @param pageno 页码
   * @param pagesize 每页显示数
   * @param params 分页查询条件
   * @param sort 排序字段
   * @param dir 升降序

   * @return 分页结果
   */
  def page(pageno: Int, pagesize: Int, params: Q, sort: String, dir: String): Page[M]
}
