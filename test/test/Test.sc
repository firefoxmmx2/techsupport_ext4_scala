import scala.io.Source
/**
 * Created by hooxin on 14-5-5.
 */
object Test {
  val hexDigests = (0 to 9).map(_.toString).toArray ++ Array("a", "b", "c", "d", "e", "f")
  println(hexDigests.mkString)

  println((0 to 9).flatMap(x => List(x,x + 10)))




//  hexDigits[(bt & 0xf0) >> 4]
}
