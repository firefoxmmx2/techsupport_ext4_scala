import dao.systemmanage.{UserDaoComponentImpl, DepartmentDaoComponentImpl}
import service.systemmanage.DepartmentServiceComponentImpl

/**
 * Created by hooxin on 14-3-16.
 */
object ComponentRegister extends DepartmentDaoComponentImpl
  with DepartmentServiceComponentImpl with UserDaoComponentImpl{
  val departmentService: ComponentRegister.DepartmentService = new DepartmentServiceImpl
  val departmentDao: ComponentRegister.DepartmentDao = new DepartmentDaoImpl
  val userDao: ComponentRegister.UserDao = new UserDaoImpl
}
