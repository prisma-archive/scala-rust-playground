package io.prisma.playground

import org.graalvm.nativeimage.c.`type`.{CCharPointer, CTypeConversion}
import org.graalvm.nativeimage.PinnedObject
import org.graalvm.word.Pointer
import rpc.rpc.{User, Header}
import org.graalvm.word.WordFactory
import java.nio.ByteBuffer

object UserHelper {
  def get(): User = {
    val protoBuf = RustInterfaceGraal.pb_output()
    val buf: ByteBuffer = CTypeConversion.asByteBuffer(protoBuf.getData, protoBuf.getLen.toInt)
    val arr: Array[Byte] = new Array(buf.remaining());
    buf.get(arr);

    val user = User.parseFrom(arr)
    
    RustInterfaceGraal.destroy(protoBuf)
    
    user
  }
  
  def put(user: User) = {
    val ary = user.toByteArray
    val len = ary.length
    val data = PinnedObject.create(ary)
    RustInterfaceGraal.pb_input(data.addressOfArrayElement(0), len.toLong)
    data.close()
  }
}

object Main {
  def toJavaString(str: CCharPointer)                            = CTypeConversion.toJavaString(str)
  def toCString(str: String): CTypeConversion.CCharPointerHolder = CTypeConversion.toCString(str)

  def main(args: Array[String]): Unit = {
    try {
      while (true) {
        val user = UserHelper.get()
        println("Scala got a type " ++ user.header.typeName ++ " from Rust with name " ++ user.name)
        UserHelper.put(User(Header("User"), "Naukio"))
        Thread.sleep(1000)
      }
    } catch {
      case e: Throwable =>
        e.printStackTrace()
    }
  }
}
