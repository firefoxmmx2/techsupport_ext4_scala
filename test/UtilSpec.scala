import akka.actor._
import akka.event.Logging
import org.specs2.mutable.Specification
import play.api.Logger
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
      page1 mustNotEqual page2
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

  "Akka actor " should {
    " actor create" in {

      val system=ActorSystem("myActorSystem")
      val myActor=system.actorOf(Props(new MyActor),"myActor")
      myActor ! "test"
      myActor ! "1111"
//      system.stop(myActor)
//      system.shutdown()
      1 must be be_=== 1
    }
  }

  class MyActor extends Actor {
    val log=Logging(context.system,this)
    def receive: Receive = {
      case "test" =>
        log.info("test")
        val subActor = context.system.actorOf(Props(new Actor {
          def receive: Actor.Receive = {
            case "test" => log.info("test !! test")
            case _ => log.info("test !! test !! shutdown")
              context.system.stop(self)
          }
        }))
        subActor ! "test"
        subActor ! "shutdown"
      case _ => log.info("system down")
    }
  }
}
