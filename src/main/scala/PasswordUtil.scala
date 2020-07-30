import java.nio.file.{Files, Path}
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.util.Properties

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object PasswordUtil {
  val keyData = Array[Byte](
    0x50.toByte, 0x72.toByte, 0x6f.toByte, 0x78.toByte, 0x79.toByte, 0x20.toByte, 0x43.toByte, 0x6f.toByte,
    0x6e.toByte, 0x66.toByte, 0x69.toByte, 0x67.toByte, 0x20.toByte, 0x53.toByte, 0x65.toByte, 0x63.toByte)
  val key = new SecretKeySpec(keyData, "AES")
  val cipherAlgo = "AES/CBC/PKCS5Padding"

  private def encrypt(msgBytes: Array[Byte]) = {
    val ciph =  Cipher.getInstance(cipherAlgo)
    ciph.init(Cipher.ENCRYPT_MODE, key)
    val body = ciph.doFinal(msgBytes)
    val iv = ciph.getIV

    val data = new Array[Byte](4 + iv.length + body.length)

    val length = body.length
    data(0) = ((length >> 24) & 0xFF).toByte
    data(1) = ((length >> 16) & 0xFF).toByte
    data(2) = ((length >> 8) & 0xFF).toByte
    data(3) = (length & 0xFF).toByte

    System.arraycopy(iv, 0, data, 4, iv.length)
    System.arraycopy(body, 0, data, 4 + iv.length, body.length)
    data
  }

  private def decrypt(data: Array[Byte]) = {
    import javax.crypto.Cipher
    import javax.crypto.spec.IvParameterSpec

    val bodyLength = ((data(0) & 0xFF) << 24) | ((data(1) & 0xFF) << 16) | ((data(2) & 0xFF) << 8) | data(3) & 0xFF

    val ivlength = data.length - 4 - bodyLength

    val ciph = Cipher.getInstance(cipherAlgo)
    ciph.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(data, 4, ivlength))
    ciph.doFinal(data, 4 + ivlength, bodyLength)
  }

  def load(file: Path) = {
    val props = new Properties
    val bytes = decrypt(Files.readAllBytes(file))
    props.load(new ByteArrayInputStream(bytes))
    props
  }

  def store(props: Properties, file: Path) = {
    val out = new ByteArrayOutputStream()
    props.store(out, "Proxy Credentials")
    Files.createDirectories(file.getParent)
    Files.write(file, encrypt(out.toByteArray))
  }
}
