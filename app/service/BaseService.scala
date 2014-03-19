package service

import util.Page

trait BaseService[T,P] {
  def insert(e: T): T

  def update(e: T)

  def deleteById(id: P)

  def page(pageno: Int, pagesize: Int, params: Map[String, Object], sort: String, dir: String): Page[T]

  def list(params: Map[String, Object]): List[T]

  def getById(id: P): T
}
