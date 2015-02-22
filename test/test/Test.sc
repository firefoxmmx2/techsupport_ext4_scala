import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.parsing.combinator.RegexParsers

/**
 * Created by hooxin on 14-5-5.
 */
object Test {
  val hexDigests = (0 to 9).map(_.toString).toArray ++ Array("a", "b", "c", "d", "e", "f")
  println(hexDigests.mkString)

  println((0 to 9).flatMap(x => List(x, x + 10)))

  class ExprParser extends RegexParsers {
    val number = "[0-9]+".r

    def expr: Parser[Any] = term ~ opt(("+" | "-") ~ expr)

    def term: Parser[Any] = factory ~ rep("*" ~ factory)

    def factory: Parser[Any] = number | "(" ~ expr ~ ")"
  }


  //  val parser=new ExprParser
  //  val result=parser.parseAll(parser.expr,"3-4*5")
  //  if(result.successful) println(result.get)
  //
  ////  hexDigits[(bt & 0xf0) >> 4]
  //
  //  def isDivisibleBy(k:Int):Int=>Boolean={
  //        println("evaluating isDivisibleBy")
  //    i=>i%k==0
  //  }
  //  val isEven:Int=>Boolean = i => i%2==0
  //  isDivisibleBy(2)(11)
  //  List(1,2,3,4,5,6,7,8,9,10).filter(isDivisibleBy(2))

}

class Student(val name: String, val sex: Int) {
  override def equals(obj: Any): Boolean = {
    if (obj.isInstanceOf[Student]) {
      val student = obj.asInstanceOf[Student]
      if (student.name == this.name
        && student.sex == this.sex)
        true
      else
        false
    }
    else {
      false
    }
  }


  override def hashCode(): Int = {
    val promise=10002020
    promise+name.hashCode+sex.hashCode()
  }

  override def toString: String = s"Student($name,$sex)"
}
val fox = new Student("fox00", 1)
val fox2 = new Student("fox", 1)
val lst=List(fox,fox2)
val set=Set(fox,fox2)
val map=Map(fox.name->fox,fox2.name->fox2)
fox == fox2
lst.size
fox.hashCode()
fox2.hashCode()
set.size
map.size

trait InstantaneousTime extends Equals{
  val repr:Int

  def canEqual(that: Any): Boolean = that.isInstanceOf[InstantaneousTime]

  override def equals(other: scala.Any): Boolean = other match {
    case that:InstantaneousTime =>
      if(this eq that)
        true
      else{
        (that.canEqual(this)) &&
        (that.## == this.##) &&
          (repr == that.repr)
      }
    case _ => false
  }

  override def hashCode(): Int = repr.##
}

trait Event extends InstantaneousTime {
  val name:String

  override def canEqual(that: Any): Boolean = that.isInstanceOf[Event]

  override def equals(other: Any): Boolean = other match {
    case that:Event =>
      if(this eq that)
        true
      else{
        (that.canEqual(this)) &&
        (repr == that.repr) &&
          (name == that.name)
      }
    case _ => false
  }

}

val x=new InstantaneousTime {
  val repr: Int = 2
}

val y=new Event {
  val name: String = "TestEvent"
  val repr: Int = 2
}
val z=new Event {
  val name: String = "TestEvent"
  val repr: Int = 2
}
x == y
y == z

object Kaaaa extends App {
  println("111111111111111111111111111111111")
}

Kaaaa.main(Array())
trait Property {
  val name:String

  override val toString = "Property("+name+")"
}

val xx=new Property {
  override val name: String = "HI"
}

val yy=new { val name="HI2"} with Property
trait SimulationMessage {
  def message :Unit
}
trait Simulation {
}
trait SimulationEntity {
  def handleMessage(msg:SimulationMessage,ctx:Simulation):Unit
}
//trait NetworkEntity extends MixableParent{
//  def getMacAddress(ip:String):String
//  def hasIpAddress(addr:String):Boolean
//
//  def handleMessage(msg:SimulationMessage,ctx:Simulation):Unit = msg match {
////    case PingRequest(ip,sender) if hasIpAddress(ip) =>
////      ctx respond (sender,PingResponse(getMacAddress(ip)))
//    case _ =>
////      super.handleMessage(msg,ctx)
//  }
//}

trait MixableParent extends SimulationEntity {
  def handleMessage(msg: SimulationMessage, ctx: Simulation): Unit = {}
}

class Router extends SimulationEntity {
  def handleMessage(msg: SimulationMessage, ctx: Simulation): Unit = msg match {
    case x => println("YAY! "+x)
    case _ =>
  }
}

trait Logger {
  def log(category:String,msg:String):Unit = {
    println(msg)
  }
}

//trait DataAccess {
//  val logger=new Logger {}
//  def query[A](in:String):A={
//    logger.log("QUERY",in)
//  }
//}

trait RemoteLogger extends Logger{
  val socket = ???

  override def log(category: String, msg: String): Unit = {
    println("""aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa""")
    super.log(category,msg)
  }
}

trait NullLogger extends Logger{
  override def log(category: String, msg: String): Unit = {
    println("1111111111111111111111111111111111")
  }
}

trait HasLogger{
  val logger=new Logger {}
}

trait HasRemoteLogger extends HasLogger{
  override val logger: Logger with Object = new RemoteLogger {}
}

trait HasNullLogger extends HasLogger{
  override val logger: Logger with Object = new NullLogger {}
}

class Matrix(private val repr:Array[Array[Double]]) {
  def row(idx:Int):Seq[Double]={
    repr(idx)
  }
  def col(idx:Int):Seq[Double]={
    repr.foldLeft(ArrayBuffer[Double]()) {
      (buffer,currentRow) =>
        buffer.append(currentRow(idx))
        buffer
    }.toArray
  }

  lazy val rowRank=repr.size
  lazy val colRank=if(rowRank>0) repr(0).size else 0

  override def toString: String = "Matrix"+repr.foldLeft("") {
    (msg,row) =>msg+row.mkString("\n|"," | ","|")
  }
}

val matrix1=new Matrix(Array(Array(1,2,3),Array(4,5,6)))
val list1=List(1,2,3,4)
list1 match {
  case head::tail => print(head+"++++"+tail)
}