package util

/**
 * Created by hooxin on 14-3-10.
 */
class Page[Y](pageno:Int=1,pagesize:Int=15,total:Int=0,datas:List[Y]=List()) {
  def start = (pageno -1) * pagesize
  def limit = pageno * pagesize
}
