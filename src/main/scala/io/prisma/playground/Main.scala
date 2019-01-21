package io.prisma.playground

import org.graalvm.nativeimage.c.`type`.{CCharPointer, CTypeConversion}
import rpc.rpc.User
import org.graalvm.word.WordFactory
import java.nio.ByteBuffer

object Main {
  def toJavaString(str: CCharPointer)                            = CTypeConversion.toJavaString(str)
  def toCString(str: String): CTypeConversion.CCharPointerHolder = CTypeConversion.toCString(str)

  def main(args: Array[String]): Unit = {
    try {
      val protoBuf = RustInterfaceGraal.pb_output()
      val buf: ByteBuffer = CTypeConversion.asByteBuffer(protoBuf.getData, protoBuf.getLen.toInt)
      val arr: Array[Byte] = new Array(buf.remaining());
      buf.get(arr);

      val user = User.parseFrom(arr)
      println(user)
    } catch {
      case e: Throwable =>
        e.printStackTrace()
    }
  }
}
