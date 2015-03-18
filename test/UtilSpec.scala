import akka.actor.SupervisorStrategy._
import akka.pattern.{ask,pipe}
import akka.actor._
import akka.event.{LoggingReceive, Logging}
import akka.util._
import com.typesafe.config.ConfigFactory
import org.specs2.mutable.Specification
import util.{Utils, Page}

import scala.collection.IterableLike
import scala.collection.generic.CanBuildFrom
import scala.concurrent.duration._
import scala.concurrent.duration.Duration

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

    "actor error process" in {

      import Worker._
      val config=ConfigFactory.parseString(
        """
          | akka.loglevel = DEBUG
          |    akka.actor.debug {
          |      receive = on
          |      lifecycle = on
          |    }
        """.stripMargin)
      val system=ActorSystem("FaultToleranceSample",config)
      val worker=system.actorOf(Props[Worker],name="worker")
      val listener=system.actorOf(Props[Listener],name="listener")

      worker.tell(Start,sender=listener)
      1 must be be_=== 1
    }
  }

  "Collection Implicit" should {
    "sort" in {
      val seq=Seq(3,2,3,1,45,3,1,2,9,10,22)
      val sortedSeq=seq.sorted
      Sorter.quicksort.sort(seq) must be be_=== sortedSeq
    }
  }

  object Sorter extends GenericSortTrait {

  }
  trait Sortable[A] {
    def sort(a:A):A
  }

  trait GenericSortTrait {
    implicit def quicksort[T,Coll](
                                  implicit ev0: Coll <:< IterableLike[T,Coll],
                                  cbf: CanBuildFrom[Coll,T,Coll],
                                  n:Ordering[T]
                                    ) = new Sortable[Coll] {
      def sort(a: Coll): Coll =
        if(a.size < 2)
          a
      else{
          import n._
          val pivot=a.head
          val (lower:Coll,tmp:Coll)  = a.partition(_ < pivot)
          val (upper:Coll,same:Coll) = tmp.partition( _ > pivot)
          val b=cbf()
          b.sizeHint(a.size)
          b ++= sort(lower)
          b ++= same
          b ++= sort(upper)
          b.result()
        }
    }
  }
  object Worker {
    case object Start
    case object Do
    case class Progress(percent:Double)
  }
  class Worker extends Actor with ActorLogging {
    import Worker._
    import CounterService._
    implicit val askTimeout=Timeout(5000 milli)

    override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
      case _: CounterService.ServiceUnavailable => Stop
    }

    var progressListener:Option[ActorRef]=None
    val counterService = context.actorOf(Props[CounterService],name="counter")
    val totalCount = 51

    def receive: Actor.Receive = LoggingReceive {
      case Start if progressListener.isEmpty =>
        progressListener=Some(sender)
        context.system.scheduler.schedule(Duration.Zero,1000 milli,self,Do)
      case Do =>
        counterService ! Increment(1)
        counterService ! Increment(1)
        counterService ! Increment(1)

        counterService ? GetCurrentCount map {
          case CurrentCount(_,count) => Progress(100.0 * count / totalCount)
        } pipeTo progressListener.get
    }
  }

  object CounterService {
    case class Increment(n:Int)
    case object GetCurrentCount
    case class CurrentCount(key:String,count:Long)
    class ServiceUnavailable(msg:String) extends RuntimeException(msg)

    private case object Reconnect
  }

  class CounterService extends Actor {
    import CounterService._
    import Counter._
    import Storage._

    override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(
      maxNrOfRetries = 3,withinTimeRange = 5000 milli
    ) {
      case _ : StorageException => Restart
    }

    val key = self.path.name
    var storage:Option[ActorRef]=None
    var counter:Option[ActorRef]=None
    var backlog=IndexedSeq.empty[(ActorRef,Any)]
    val MaxBacklog=10000

    @throws[Exception](classOf[Exception])
    override def preStart(): Unit = initStorage()

    def receive: Actor.Receive = LoggingReceive {
      case Entry(k,v) if k==key && counter==None =>
        val c=context.actorOf(Props(new Counter(key,v)))
        counter=Some(c)
        c ! UseStorage(storage)
        for((replyTo,msg) <- backlog) c.tell(msg,sender=replyTo)
        backlog=IndexedSeq.empty
      case msg@Increment(n) => forwardOrPlaceBacklog(msg)
      case msg@GetCurrentCount=>forwardOrPlaceBacklog(msg)
      case Terminated(actorRef) if Some(actorRef)==storage=>
        storage=None
        counter.foreach(_!UseStorage(None))
        context.system.scheduler.scheduleOnce(10000 milli,self,Reconnect)
      case Reconnect=>
        initStorage()
    }

    def forwardOrPlaceBacklog(msg:Any): Unit = {
      counter match {
        case Some(c)=>c.forward(msg)
        case None=>
          if(backlog.size >= MaxBacklog)
            throw new CounterService.ServiceUnavailable("CounterService not available, lack of initial value")
          backlog=backlog :+ (sender,msg)
      }
    }

    def initStorage(): Unit ={
      storage=Some(context.watch(context.actorOf(Props[Storage],name="storage")))
      counter foreach( _ ! UseStorage(storage))
      storage.get ! Get(key)
    }
  }

  object DummyDB {
    import Storage.StorageException
    private var db=Map[String,Long]()

    @throws(classOf[StorageException])
    def save(key:String,value:Long):Unit=synchronized {
      if(11 <= value && value <= 14) throw new StorageException("Simulated store failure "+value)
      db += (key -> value)
    }

    @throws(classOf[StorageException])
    def load(key:String):Option[Long]=synchronized {
      db.get(key)
    }
  }
  object Storage {
    case class Store(entry:Entry)
    case class Get(key:String)
    case class Entry(key:String,value:Long)
    class StorageException(msg:String) extends RuntimeException(msg)
  }
  class Storage extends Actor {
    import Storage._
    val db=DummyDB

    def receive: Actor.Receive = LoggingReceive {
      case Store(Entry(key,count)) => db.save(key,count)
      case Get(key)=>sender ! Entry(key,db.load(key).getOrElse(0))
    }
  }
  object Counter {
    case class UseStorage(storage: Option[ActorRef])
  }
  class Counter(key:String,initialValue:Long) extends Actor {
    import Counter._
    import CounterService._
    import Storage._

    var count=initialValue
    var storage:Option[ActorRef]=None

    def receive: Actor.Receive = LoggingReceive {
      case UseStorage(s)=>
        storage=s
        storeCount()
      case Increment(n)=>
        count += n
        storeCount()
      case GetCurrentCount=>
        sender!CurrentCount(key,count)
    }

    def storeCount(): Unit ={
      storage.foreach {
        _ ! Store(Entry(key,count))
      }
    }
  }
  class Listener extends Actor with ActorLogging {
    import Worker._
    context.setReceiveTimeout(15000 milli)
    def receive: Actor.Receive = {
      case Progress(percent) =>
        log.info("Current progress: {} %",percent)
        if(percent >= 100) {
          log.info("That's all, shuttting down")
          context.system.shutdown()
        }
      case ReceiveTimeout =>
        log.error("Shutting down due to unavaliable service")
        context.system.shutdown()
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
