package example

import com.sun.jna.{Library, Native}

object Hello extends Greeting {

  def main(args: Array[String]): Unit = {
    println(greeting)

    val libc = Native.loadLibrary("c", classOf[libc])
    println(libc.puts("hello c"))

    val library = Native.loadLibrary("hello", classOf[RustInterface])
    library.printGreeting()

    println(library.hello())
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
