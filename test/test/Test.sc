import scala.io.Source
import scala.util.parsing.combinator.RegexParsers

/**
 * Created by hooxin on 14-5-5.
 */
object Test {
  val hexDigests = (0 to 9).map(_.toString).toArray ++ Array("a", "b", "c", "d", "e", "f")
  println(hexDigests.mkString)

  println((0 to 9).flatMap(x => List(x,x + 10)))

  class ExprParser extends RegexParsers {
    val number="[0-9]+".r

    def expr:Parser[Any]=term ~ opt(("+"|"-") ~ expr)
    def term:Parser[Any]=factory ~ rep("*" ~ factory)
    def factory:Parser[Any]=number | "(" ~ expr ~ ")"
  }


  val parser=new ExprParser
  val result=parser.parseAll(parser.expr,"3-4*5")
  if(result.successful) println(result.get)

//  hexDigits[(bt & 0xf0) >> 4]

  def isDivisibleBy(k:Int):Int=>Boolean={
        println("evaluating isDivisibleBy")
    i=>i%k==0
  }
  val isEven:Int=>Boolean = i => i%2==0
  isDivisibleBy(2)(11)
  List(1,2,3,4,5,6,7,8,9,10).filter(isDivisibleBy(2))

}
