package dao.systemmanage

import models._
import util.Page
import models.Role
import models.Department
import models.Menu
import models.User
import models.System

/**
 * Created by hooxin on 14-2-14.
 */

import org.squeryl._
import org.squeryl.dsl._
import models.CommonTypeMode._

trait DepartmentDaoComponentImpl extends DepartmentDaoComponent {

  class DepartmentDaoImpl extends DepartmentDao {
    def selectForListQuery[T](params: Map[String, Object], sort: String = "departid", dir: String = "asc",
                           isCount: Boolean = false):Query[T] = from(SystemManage.departments)(d =>
      where {
        (d.id === params.get("departid").asInstanceOf[Option[Long]].?)
          .and(d.departcode === params.get("departcode").asInstanceOf[d.departcode.type].?)
          .and(d.departname like params.get("departname").asInstanceOf[d.departname.type].?)
          .and(d.departfullcode like params.get("departfullcode").asInstanceOf[d.departfullcode.type].?)
          .and(d.departlevel === params.get("departlevel").asInstanceOf[Int].?)
      }
        select (if (isCount) countDistinct(d.id).asInstanceOf[T] else d.asInstanceOf[T])
        orderBy (if (sort == "departid") if (dir == "asc") d.id asc else d.id desc else d.id asc)
    )

    /**
     * 分页查询
     * @param pageno 页码
     * @param pagesize 每页显示数
     * @param params 分页查询条件
     * @param sort 排序字段
     * @param dir 升降序

     * @return 分页结果
     */
    def page(pageno: Int, pagesize: Int, params: Map[String, Object], sort: String = "departid", dir: String = "asc"): Page[Department] = {
      val page = new Page[Department](pageno, pagesize, count(params))
      if (page.total == 0)
        page
      else {
        val datas = selectForListQuery(params, sort, dir).page(page.start,page.limit).toList
        page.copy(datas = datas)
      }
    }

    /**
     * 非分页查询
     * @param params 查询条件

     * @return 结果列表
     */
    def list(params: Map[String, Object]): List[Department] = selectForListQuery(params)
      .toList

    /**
     * 分页总数查询
     * @param params 分页查询条件

     * @return 结果数
     */
    def count(params: Map[String, Object]): Int = selectForListQuery(params, isCount = true).single

    /**
     * 通过主键获取单个实体
     * @param id 主键

     * @return 实体
     */
    def getById(id: Long): Department = from(SystemManage.departments) {
      d =>
        where(d.id === id)
        select(d)
    }.single

    /**
     * 通过主键删除
     * @param id 主键

     * @return
     */
    def deleteById(id: Long): Unit = SystemManage.departments.deleteWhere( d => id === d.id)

    /**
     * 删除
     * @param m 实体

     * @return
     */
    def delete(m: Department): Unit = SystemManage.departments.delete(m.id)

    /**
     * 修改
     * @param m 实体

     * @return
     */
    def update(m: Department): Unit = SystemManage.departments.update(m)

    /**
     * 插入
     * @param m 实体

     * @return 插入后的实体
     */
    def insert(m: Department): Department = SystemManage.departments.insert(m)
  }

}