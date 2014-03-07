package dao

import scala.slick.lifted.{CanBeQueryCondition, Query, TableQuery}
import models.Departments

trait Queries {
  val departments = TableQuery[Departments]

  case class MaybeFilter[X, Y](val query: Query[X, Y]) {
    def filter[T, R: CanBeQueryCondition](data: Option[T])(f: T => X => R) = {
      data.map(v => MaybeFilter(query.filter(f(v)))).getOrElse(this)
    }
  }
}
