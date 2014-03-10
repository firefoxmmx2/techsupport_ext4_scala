package dao

import scala.slick.lifted.{CanBeQueryCondition, Query, TableQuery}
import scala.util.Try
import util.Page

/**
 * Created by hooxin on 14-3-7.
 */
import com.typesafe.slick.driver.oracle.OracleDriver.simple.Session
trait BaseDao [M,PRIMARY_KEY_T] {
  case class MaybeFilter[X, Y](val query: Query[X, Y]) {
    def filter[T, R: CanBeQueryCondition](data: Option[T])(f: T => X => R) = {
      data.map(v => MaybeFilter(query.filter(f(v)))).getOrElse(this)
    }
  }

  def insert(m:M)(implicit session:Session):Try[M]

  def update(m:M)(implicit session:Session):Unit

  def delete(m:M)(implicit session:Session):Unit

  def deleteById(id:PRIMARY_KEY_T)(implicit session:Session):Unit

  def getById(id:PRIMARY_KEY_T)(implicit session:Session):M
}
