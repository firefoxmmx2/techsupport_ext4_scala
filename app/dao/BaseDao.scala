package dao

import scala.slick.lifted.{CanBeQueryCondition, Query, TableQuery}

/**
 * Created by hooxin on 14-3-7.
 */
trait BaseDao [M] {
  case class MaybeFilter[X, Y](val query: Query[X, Y]) {
    def filter[T, R: CanBeQueryCondition](data: Option[T])(f: T => X => R) = {
      data.map(v => MaybeFilter(query.filter(f(v)))).getOrElse(this)
    }
  }

  def list(params:Map[String,Object]):M

  def page(pageno:Int,pagesize:Int,params:Map[String,Object])

  def insert(m:M):M

  def update(m:M):Unit

  def delete(m:M):Unit

  def count(m:M):Int

  def deleteById[L](id:L):Unit

  def getById[L](id:L):M
}


