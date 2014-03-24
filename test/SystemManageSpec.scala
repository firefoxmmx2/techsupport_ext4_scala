import models.Department
import play.api.test._
import play.api.test.Helpers._
import org.specs2.mutable._
import ComponentRegister._
/**
 * Created by hooxin on 14-2-12.
 */

class SystemManageSpec extends Specification{
  "系统管理模块" should {
    "添加一个机构" in {
      running(FakeApplication()) {
        departmentService.deleteById(99999)
        val d = Department("test", "测试", 0, "test.", parentDepartid = 0, departid = Option(99999))
        val insertingDeparment = departmentService.insert(d)
        assert(d.departid != None)
        assert(d.departid != insertingDeparment.departid)
        departmentService.deleteById(d.departid.get)
        val selectDepartment=departmentService.getById(d.departid.get)
        assert(selectDepartment == None)
      }

    }
  }

}
