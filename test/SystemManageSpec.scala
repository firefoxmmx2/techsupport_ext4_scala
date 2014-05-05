import models.Department
import play.api.test._
import play.api.test.Helpers._
import org.specs2.mutable._
import util.ComponentRegister._
/**
 * Created by hooxin on 14-2-12.
 */

class SystemManageSpec extends Specification{
  "系统管理模块" should {
    "添加一个机构" in {
      running(FakeApplication()) {
        val d = Department("test", "测试", 0, "test.", parentDepartid = 0, id = Option(99999))
        val insertingDeparment = departmentService.insert(d)
        assert(d.id != None)
        departmentService.deleteById(insertingDeparment.id.get)
        val selectDepartment=departmentService.getById(insertingDeparment.id.get)
        
        selectDepartment must beEqualTo( null) 
      }

    }
  }

}
