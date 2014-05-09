import org.specs2.mutable.Specification
import util.{Utils, Page}

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
      page1 must be equals(page2) 
    }
  }
  "Utils Md5 " should {
    "md5 test" in {
      val s = "1";
      val md5str=Utils.md5(s)
      println("="*13+"md5str = "+md5str+"="*13)
      println(md5str == "c4ca4238a0b923820dcc509a6f75849b")
      md5str must be equalTo "c4ca4238a0b923820dcc509a6f75849b"
    }
  }
}
