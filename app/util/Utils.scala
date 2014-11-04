package util

import play.api.Logger

import scala.util.{Failure, Success, Try}
import org.slf4j

/**
 * Created by hooxin on 14-5-9.
 */
object Utils {
  def nonBlank(s: String): Boolean = (s ne null) && s == ""

  def md5(data: Array[Byte]): Array[Byte] = {
    _md5.digest(data)
  }

  def md5(s: String): String = {
    bytes2Hex(md5(s.getBytes()))
  }

  def sha1(data: Array[Byte]): Array[Byte] = {
    _sha1.digest(data)
  }

  def sha1(s: String): String = {
    new String(sha1(s.getBytes()))
  }

  def bytes2Hex(data: Array[Byte]): String = {
    data.toList.flatMap(b => List(hexDigests((b & 0xf0) >> 4), hexDigests(b & 0xf))).mkString
  }

  val _md5 = DigestHelper("MD5")
  val _sha1 = DigestHelper("SHA1")
  private val hexDigests: Array[String] = (0 to 9).map(_.toString).toArray ++ Array("a", "b", "c", "d", "e", "f")
}

/**
 * 映射扩展
 * @param map
 * @tparam K
 * @tparam V
 */
class RichMap[K, +V](map: Map[K, V]) {
  def getAs[T](key: K): Option[T] = {
    map.get(key) match {
      case Some(value: T) => Some(value)
      case _ => None
    }
  }

  def getOrElseAs[T](key: K, defaultValue: T): T = {
    map.get(key) match {
      case Some(value: T) => value
      case _ => defaultValue
    }
  }
}

/**
 * 控制器工具
 */
trait ControllerUtils {
  def responseData(result: Int = 0,
                   log: slf4j.Logger = Logger.logger,
                   message: String = "操作成功",
                   extraParams: Option[Map[String, Any]] = None,
                   e: Option[Exception] = None): Map[String, Any] = Try {
    e match {
      case Some(e) =>
        log.error(message)
        log.error(e.getMessage)
        log.debug(e.getMessage, e.fillInStackTrace())
        if (result > 0) {
          log.error("当为错误时候的result的值必须小于0")
        }
        val response = Map("success" -> false, "result" -> result, "message" -> message)
        extraParams match {
          case Some(map) =>
            map ++ response
          case None =>
            response
        }

      case None =>
        val response = Map("result" -> result, "message" -> message, "success" -> (result == 0))
        extraParams match {
          case Some(map) =>
            map ++ response
          case None =>
            response
        }
    }
  } match {
    case Failure(e: Exception) =>
      responseData(result = -1, message = e.getMessage, e = Some(e))
    case Success(response: Map[String, Any]) =>
      response
  }
}