import org.specs2.mutable.Specification
import util.Page

/**
 * Created by hooxin on 14-3-10.
 */
class UtilSpec  extends Specification{
  "Utils Page" should {
    "page test" in {
      val page1=new Page
      val page2=new Page(pageno = 2)
      println("="*13+"[page1.start]"+page1.start+"="*13)
      println("="*13+"[page1.limit]"+page1.limit+"="*13)
      println("="*13+"[page2.start]"+page2.start+"="*13)
      println("="*13+"[page2.limit]"+page2.limit+"="*13)
    }
  }
}
