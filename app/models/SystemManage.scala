package models

import org.squeryl.Schema

/**
 * 机构
 */
case class Department(departid: Long, departcode: String, departname: String,
                      departlevel: Int, departfullcode: String,
                      parentDepartid: Long,
                      nodeOrder: Int = 0, isLeaf: String = "Y",
                      departsimplepin: String = None, departallpin: String = None,
                      departbrevitycode: String = departcode)
object Department {
  def find(map:Map) = {}
  def insert(department:Department) = {
    SystemManage.departments.insert(department)
  }
  def update(department:Department)={
    SystemManage.departments.update(department)
  }
//  def delete(departid:Long)= SystemManage.departments.delete(q => { q})
}
/**
 * 用户
 */
case class User(userid: Long, departid: Long, useraccount: String, username: String,
                password: String, idnum: String, mobilePhone: String = None, userorder: Int = 1,
                isVaild: String = "Y", userType: String = None, jzlbdm: String = None, jzlbmc: String = None,
                email: String = None)
/**
* 角色
 */
case class Role(roleid: Long, departid: Long, rolename: String, roleDescription: String = None,
                roleType: Int = None, jzlbdm: String = None, jzlbmc: String = None)

/**
 * 系统管理模块
 */
object SystemManage extends Schema {
  val departments = table[Department]("t_department")
  val users = table[User]("t_user")
  val roles = table[Role]("t_role")
}


