package util

/**
 * Created by hooxin on 14-3-10.
 */
case class Page[Y](val pageno:Int=1,val pagesize:Int=15,val total:Int=0,val datas:List[Y]=List()) {
  def start = (pageno -1) * pagesize
  def limit = pagesize
}
