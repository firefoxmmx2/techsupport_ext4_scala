import play.api.test._
import play.api.test.Helpers._
import models.Department
import org.specs2.mutable._
import play.api.Play.current

/**
 * Created by hooxin on 14-2-12.
 */

class SystemManageSpec extends Specification {

  "系统管理模块" should {
    "添加一个机构" in {
      running(FakeApplication()) {

      }

    }
  }
}
