package util

/**
 * Created by hooxin on 14-5-9.
 */
object Utils {
  def nonBlank(s:String):Boolean = (s ne null) && s == ""
  def md5(data:Array[Byte]):Array[Byte] = {
    _md5.digest(data)
  }
  def md5(s:String):String = {
    bytes2Hex(md5(s.getBytes()))
  }

  def sha1(data:Array[Byte]):Array[Byte] = {
    _sha1.digest(data)
  }

  def sha1(s:String):String = {
    new String(sha1(s.getBytes()))
  }

  def bytes2Hex(data:Array[Byte]):String = {
    data.toList.flatMap( b => List(hexDigests((b & 0xf0) >> 4),hexDigests(b & 0xf))).mkString
  }
  val _md5 = DigestHelper("MD5")
  val _sha1 = DigestHelper("SHA1")
  private val hexDigests:Array[String] = (0 to 9).map(_.toString).toArray ++ Array("a", "b", "c", "d", "e", "f")
}
