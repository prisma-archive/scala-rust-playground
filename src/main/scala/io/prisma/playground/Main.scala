package io.prisma.playground

import org.graalvm.nativeimage.c.`type`.{CCharPointer, CTypeConversion}
import org.graalvm.nativeimage.PinnedObject
import org.graalvm.word.Pointer
import rpc.rpc.{User, Header}
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

    val user = User.parseFrom(arr)
    
    RustInterfaceGraal.destroy(protoBuf)
    
    user
  }
  
  def putUser(user: User) = {
    val ary = user.toByteArray
    val len = ary.length
    val data = PinnedObject.create(ary)
    RustInterfaceGraal.pb_input(data.addressOfArrayElement(0), len.toLong)
  }

  def main(args: Array[String]): Unit = {
    try {
      val user1 = getUser()
      println("Scala got a type " ++ user1.header.typeName ++ " from Rust with name " ++ user1.name)
      putUser(User(Header("User"), "Naukio"))
    } catch {
      case e: Throwable =>
        e.printStackTrace()
    }
  }
}
