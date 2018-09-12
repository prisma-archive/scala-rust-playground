package example

import com.sun.jna.{Library, Native}
import org.graalvm.nativeimage.c
import org.graalvm.nativeimage.c.`type`.CTypeConversion

object Hello extends Greeting {

  def main(args: Array[String]): Unit = {
    // debug flags for loading JNA itself
    System.setProperty("jna.debug_load.jna", "true")
    System.setProperty("jna.boot.library.path", "/Users/marcusboehm/R/github.com/graphcool/scala-rust-playground/jnalib/")
    // debug flags for loading libraries with JNA
    System.setProperty("jna.debug_load", "true")
    System.setProperty("jna.library.path", "/Users/marcusboehm/R/github.com/graphcool/scala-rust-playground/")
    testJna()

//    testGraalsCApi()
  }

  def testJna(): Unit = {
//    val libc = Native.loadLibrary("c", classOf[libc])
//    println(libc.puts("hello c"))

    val library = Native.loadLibrary("hello", classOf[RustInterfaceJna])
    library.printHello()
  }

  def testGraalsCApi(): Unit = {
    RustInterfaceGraal.printHello()

    val hello = CTypeConversion.toJavaString(RustInterfaceGraal.hello())
    println(s"hello returned: $hello")

    val formattedHello = CTypeConversion.toJavaString(RustInterfaceGraal.formatHello(CTypeConversion.toCString("Marcus").get()))
    println(s"formatHello returned: $formattedHello")

    testStructViaGraal()
  }

  def testStructViaGraal(): Unit = {
    println("about to create a struct from Scala")
    val byValue = RustInterfaceGraal.newCounterByValue()
    require(byValue.isNull) // don't know why that is


    val struct = RustInterfaceGraal.newCounterByReference()
    println("created the struct. Now calling a method on it")
    println(s"count is: ${struct.getCount}")
    RustInterfaceGraal.increment(struct)
    println(s"count is: ${struct.getCount}")
    RustInterfaceGraal.increment(struct)
    println(s"count is: ${struct.getCount}")
  }
}

trait libc extends Library {
  def open(path:String, flag:Int):Int
  def ioctl(fd:Int, request:Int, args:Array[_]):Int
  def close(fd:Int):Unit
  def puts(s: String): Int
}

trait Greeting {
  lazy val greeting: String = "hello alda!"
}
