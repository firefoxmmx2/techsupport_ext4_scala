package service

import util.Page
import models.TQueryable

trait BaseService[T, P, Q <: TQueryable] {
  def insert(e: T): T

  def update(e: T)

  def deleteById(id: P)

  def page(pageno: Int, pagesize: Int, params: Q, sort: String, dir: String): Page[T]

  def list(params: Q): List[T]

  def getById(id: P): Option[T]
}
