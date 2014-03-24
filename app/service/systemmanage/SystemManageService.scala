package service.systemmanage

import models.Department
import dao.systemmanage.DepartmentDaoComponent
import util.Page

/**
 * Created by hooxin on 14-3-10.
 */

import models.CommonTypeMode._

trait DepartmentServiceComponentImpl extends DepartmentServiceComponent {
  this: DepartmentDaoComponent =>

  class DepartmentServiceImpl extends DepartmentService {
    def getById(id: Long): Department = departmentDao.getById(id)

    def list(params: Map[String, Object]): List[Department] = departmentDao.list(params)

    def page(pageno: Int = 1, pagesize: Int = 20, params: Map[String, Object], sort: String, dir: String): Page[Department] =
      departmentDao.page(pageno, pagesize, params, sort, dir)

    def deleteById(id: Long): Unit = inTransaction {
      departmentDao.deleteById(id)
    }

    def update(e: Department): Unit = inTransaction {
      departmentDao.update(e)
    }

    def insert(e: Department): Department = inTransaction {
      departmentDao.insert(e)
    }
  }

}


