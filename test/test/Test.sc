import scala.io.Source

/**
 * Created by hooxin on 14-5-5.
 */
object Test {
  class RichedInt(i: Int) {
    def ! = (1 to i) reduce (_ * _)
  }
  implicit def int2RichedInt(i: Int) = new RichedInt(i)
  println("helloWorld")

  println(5!)



  var cont:(Unit=>Unit) = null
  var filename = "/home/hooxin/剪贴板.txt"
  var contents = ""

//  reset {
//    while(contents == "") {
//      try {
//        contents = Source.fromFile(filename,"utf8").mkString
//      }
//      catch {
//        case _ =>
//      }
//      shift {
//        k:(Unit=>Unit) =>
//          cont = k
//      }
//    }
//  }
}
