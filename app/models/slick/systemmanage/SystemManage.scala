package models.slick.systemmanage

import scala.slick.lifted._

/**
 * Created by hooxin on 15-1-26.
 */
object SystemManage {
  val departments = TableQuery[DepartmentTable]
  val users = TableQuery[UserTable]
  val dicts = TableQuery[DictTable]
  val dictItems = TableQuery[DictItemTable]
}
