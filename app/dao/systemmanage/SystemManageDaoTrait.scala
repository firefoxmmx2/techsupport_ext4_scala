package dao.systemmanage

import dao.BaseDao
import models.Department

/**
 * Created by hooxin on 14-3-10.
 */

trait DepartmentDaoComponent{
  val departmentDao:DepartmentDao

  trait DepartmentDao extends BaseDao[Department,Long]{

  }
}

