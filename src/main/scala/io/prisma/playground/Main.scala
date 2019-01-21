package io.prisma.playground

import org.graalvm.nativeimage.c.`type`.{CCharPointer, CTypeConversion}
import rpc.rpc.User
import org.graalvm.word.WordFactory
import java.nio.ByteBuffer

object Main {
  def toJavaString(str: CCharPointer)                            = CTypeConversion.toJavaString(str)
  def toCString(str: String): CTypeConversion.CCharPointerHolder = CTypeConversion.toCString(str)
  
  def getUser(): User = {
      val protoBuf = RustInterfaceGraal.pb_output()
      val buf: ByteBuffer = CTypeConversion.asByteBuffer(protoBuf.getData, protoBuf.getLen.toInt)
      val arr: Array[Byte] = new Array(buf.remaining());
      buf.get(arr);

      User.parseFrom(arr)
  }

  def main(args: Array[String]): Unit = {
    try {
      val user = getUser()
      println("We got a type " ++ user.header.typeName ++ " with name " ++ user.name)
    } catch {
      case e: Throwable =>
        e.printStackTrace()
    }
  }
}
