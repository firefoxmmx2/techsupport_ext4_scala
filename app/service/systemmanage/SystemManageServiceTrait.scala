package service.systemmanage

import models.Department
import util.Page

/**
 * Created by hooxin on 14-3-10.
 */
trait DepartmentServiceTrait {
  def insert (d:Department):Department
  def update (d:Department)
  def deleteById(id:Long)
  def page(pageno:Int,pagesize:Int,params:Map[String,Object],sort:String,dir:String):Page[Department]
  def list(params:Map[String,Object]):List[Department]
  def getById(id:Long):Department
}


