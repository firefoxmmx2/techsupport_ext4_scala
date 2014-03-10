package dao.systemmanage

import dao.BaseDao
import models.Department
import com.typesafe.slick.driver.oracle.OracleDriver.simple.Session
import util.Page

/**
 * Created by hooxin on 14-3-10.
 */
trait DepartmentDaoTrait extends BaseDao[Department,Long]{
  def count(params:Map[String,Object])(implicit session:Session):Int
  def list(params:Map[String,Object])(implicit session:Session):List[Department]
  def page(pageno:Int,pagesize:Int,params:Map[String,Object],sort:String,dir:String)(implicit session:Session):Page[Department]
}
