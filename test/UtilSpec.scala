import akka.actor._
import akka.event.Logging
import akka.util.Timeout
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

    " become " in {
      val system=ActorSystem("myActorSystem")
      val myActor=system.actorOf(Props(new MyActor2),"myActor")
      myActor ! "foo"
      myActor ! "bar"
//      Thread.sleep(1000)
      1 must be be_=== 1
    }

    "actor sum" in {
      import akka.util.Timeout
      import akka.pattern._
      import scala.concurrent.{ ExecutionContext, Promise }
      import scala.concurrent.duration._
      import scala.concurrent.{Await, Future}

      implicit val timeout = Timeout(2500 microseconds)
      val system=ActorSystem("myActorSystem")
      val myActor=system.actorOf(Props(new SumActor))
//      (1 to 100).map( i =>  myActor.ask(i)(Timeout(10)).mapTo[Int]  ).sum must be be_=== (1 to 100).map( i => (1 to i).sum).sum
//      (for( i <- (1 to 100)) yield (1 to i).sum).sum must be be_=== (1 to 100).map( i => (1 to i).sum).sum
      val startTime =System.currentTimeMillis()
      val normalResult=(1 to 1000).map( i => (1 to i).sum).sum
      val useTime=System.currentTimeMillis()-startTime
      println("="*13+" non parral time = "+useTime+"="*13)
      val startConTime=System.currentTimeMillis()
      val conResult=(for( i <- (1 to 1000)) yield {
        val f=Future((1 to i).sum)
        Await.result(f,timeout.duration)
      }).sum
      val conUseTime=System.currentTimeMillis()-startConTime
      println("="*13+" use Future time = "+conUseTime+"="*13)
      conResult must be be_=== normalResult

    }

    "actor send and receive future" in {
      import akka.util.Timeout
      import akka.pattern._
      import scala.concurrent.{ ExecutionContext, Promise }
      import scala.concurrent.duration._
      import scala.concurrent.{Await, Future}
      val system=ActorSystem("reply")
      val actor1 = system.actorOf(Props(new ReplyActor))
      val actor2 = system.actorOf(Props(new FutureActor))
      implicit val timeout = Timeout(5000 microseconds)
      val future1  = ask(actor1, "hi").mapTo[String]
      import system.dispatcher
      future1 pipeTo actor2
      1 must  be be_=== 1
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

  class SumActor extends Actor {
    def receive: Actor.Receive = {
      case num:Int =>
        sender ! ( 1 to num).sum
    }
  }
  class MyActor2 extends Actor {
    import context._
    val log=Logging(context.system,this)
    def happy:Actor.Receive ={
      case "foo" =>
        become(angry)
      case "bar" =>
        log.info("i am happy")
        sender ! "i am happy"
    }

    def angry:Actor.Receive={
      case "bar" =>
        become(happy)
      case "foo" =>
        log.info("i am angry")
        sender ! "i am angry"
    }
    def receive: Actor.Receive = {
      case "foo" =>
        become(angry)
      case "bar" =>
        become(happy)
    }
  }

  class ReplyActor extends Actor {
    def receive: Actor.Receive = {
      case "hi" => sender ! "="*13+"hello"+"="*13
    }
  }

  class FutureActor extends Actor {
    def receive: Actor.Receive = {
      case m => println(m)
    }
  }
}
