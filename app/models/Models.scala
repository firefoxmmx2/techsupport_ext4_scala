package models

import scala.slick.lifted.TableQuery

/**
 * Created by hooxin on 14-2-14.
 */
trait Models {
  val departments = TableQuery[Departments]
  val users = TableQuery[Users]
  val roles = TableQuery[Roles]
  val systems = TableQuery[Systems]
  val menus = TableQuery[Menus]
}
