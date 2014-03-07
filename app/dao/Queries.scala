package dao

import scala.slick.lifted.TableQuery
import models.Departments

trait Queries {
  val departments = TableQuery[Departments]
}
