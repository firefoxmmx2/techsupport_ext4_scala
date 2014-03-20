package service.systemmanage

import models.Department
import dao.systemmanage.DepartmentDaoComponent
import util.Page
import dao.DB

/**
 * Created by hooxin on 14-3-10.
 */

trait DepartmentServiceComponentImpl extends DepartmentServiceComponent {
  this: DepartmentDaoComponent =>

  class DepartmentServiceImpl extends DepartmentService {
    def list(params: Map[String, Object]): List[Department] = {
      DB.getDatabase().withSession {
        implicit session =>
          departmentDao.list(params)
      }

    }

    def insert(d: Department): Department = DB.getDatabase().withTransaction {
      implicit session =>
        departmentDao.insert(d)
    }

    def update(d: Department): Unit = DB.getDatabase().withTransaction {
      implicit session =>
        departmentDao.update(d)
    }

    def page(pageno: Int, pagesize: Int, params: Map[String, Object], sort: String, dir: String): Page[Department] =
      DB.getDatabase().withSession {
        implicit session =>
          departmentDao.page(pageno, pagesize, params, sort, dir)
      }

    def deleteById(id: Long): Unit = DB.getDatabase().withTransaction {
      implicit session =>
        departmentDao.deleteById(id)
    }

    def getById(id: Long): Department = DB.getDatabase().withSession {
      implicit session =>
        departmentDao.getById(id)
    }
  }

}


