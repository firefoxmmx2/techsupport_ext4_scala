import models.Department
import org.specs2.mutable._
import scala.util.Try

/**
 * Created by hooxin on 14-2-12.
 */
class SystemManageSpec extends Specification{
   "系统管理模块" should {
     "添加一个机构" in {
       val department=Department(9999,"csjd","测试节点",0,"csjd.",1)

     }
   }
}
