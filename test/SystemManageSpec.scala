import dao.systemmanage.DepartmentDaoImpl
import play.api.test._
import play.api.test.Helpers._
import models.Department
import org.specs2.mutable._
import dao.DB._
import play.api.Play.current
import dao.DB

/**
 * Created by hooxin on 14-2-12.
 */

class SystemManageSpec extends Specification  {

  "系统管理模块" should {
    "添加一个机构" in {
      running(FakeApplication()) {
        
        val department = Department(9999L, "csjd", "测试节点", 0, "csjd.", 1, departallpin = None,
          departsimplepin = None, departbrevitycode = None)
        DB.getDatabase().withSession {
          implicit session =>
            DepartmentDaoImpl.deleteById(9999L)
            val d1 = DepartmentDaoImpl.insert(department)
            val d2 = DepartmentDaoImpl.list(departid = Option(9999L))
            println("="*13+"["+d2(0).departid+"]"+"="*13)
            println("="*13+"["+d1.departid+"]"+"="*13)
            assert(d2(0) == department)
        }

      }

    }
  }
}
