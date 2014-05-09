package util

import java.security.MessageDigest
import java.nio.ByteBuffer
import java.io.InputStream

/**
 * Created by hooxin on 14-5-9.
 */
object DigestHelper {
  //  java.security.Security.addProvider(new )
  private val BC = "BC"
  private val STREAM_BUFFER_LENGTH = 1024

  def apply(sname: String) = new RichMessageDigest(MessageDigest.getInstance(sname))

  def digest(md: MessageDigest, in: InputStream) = {
    val buffer = (0 until 1024) map (_.toByte) toArray
    var rn = in.read(buffer, 0, STREAM_BUFFER_LENGTH)
    while (rn > -1) {
      md.update(buffer, 0, rn)
      rn = in.read(buffer, 0, STREAM_BUFFER_LENGTH)
    }
    md.digest()
  }

  class RichMessageDigest(md: MessageDigest) {

    def provider = md.getProvider

    def algorithm = md.getAlgorithm

    def digestLength = md.getDigestLength

    def length = digestLength

    def reset = {
      md.reset()
      this
    }

    def digest = md.digest

    def digest(data: Array[Byte]) = md.digest(data)

    def digest(data: Array[Byte], offset: Int, length: Int) = md.digest(data, offset, length)

    def update(data: Array[Byte], offset: Int, length: Int) = {
      md.update(data, offset, length)
      this
    }

    def update(data: Array[Byte]) = {
      md.update(data)
      this
    }

    def update(data: ByteBuffer) = {
      md.update(data)
      this
    }

    def update(data: Byte) = {
      md.update(data)
      this
    }

  }

}
