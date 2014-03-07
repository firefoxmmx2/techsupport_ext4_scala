package dao

import scala.slick.lifted.{CanBeQueryCondition, Query, TableQuery}
import models.DepartmentTable

/**
 * Created by hooxin on 14-3-7.
 */
trait BaseDao {
  case class MaybeFilter[X, Y](val query: Query[X, Y]) {
    def filter[T, R: CanBeQueryCondition](data: Option[T])(f: T => X => R) = {
      data.map(v => MaybeFilter(query.filter(f(v)))).getOrElse(this)
    }
  }


}


