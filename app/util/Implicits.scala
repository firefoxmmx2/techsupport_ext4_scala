package util

/**
 * 公共导入
 */
trait Implicits {
  implicit def map2RichMap[K,V](map:Map[K,V]) = new RichMap(map)
}
