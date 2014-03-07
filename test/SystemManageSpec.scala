import dao.{Queries, Departments}
import models.Department
import org.specs2.mutable._
import dao.Db._

import play.api.Play.current
/**
 * Created by hooxin on 14-2-12.
 */

class SystemManageSpec extends Specification with Queries{

  "系统管理模块" should {
    "添加一个机构" in {
      val department = Department(9999L, "csjd", "测试节点", 0, "csjd.", 1, departallpin = None,
        departsimplepin = None, departbrevitycode = None)
      Departments.insert(department)
      val d2 = Departments.list(departid=Option(9999L))
      assert(d2 == department)

    }
  }
}
