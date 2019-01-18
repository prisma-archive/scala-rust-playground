package io.prisma.playground

import org.graalvm.nativeimage.c.`type`.{CCharPointer, CTypeConversion}

object Main {
  def toJavaString(str: CCharPointer)                            = CTypeConversion.toJavaString(str)
  def toCString(str: String): CTypeConversion.CCharPointerHolder = CTypeConversion.toCString(str)

  def main(args: Array[String]): Unit = {
    val message = toCString("Hello, world!")
    try {
      RustInterfaceGraal.simple_test(message.get())
    } catch {
      case e: Throwable =>
        e.printStackTrace()
        message.close()
    }
  }
}
