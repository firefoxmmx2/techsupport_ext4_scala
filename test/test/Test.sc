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

