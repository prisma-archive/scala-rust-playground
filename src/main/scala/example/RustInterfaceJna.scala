package example

import com.sun.jna.{Library, Native}

trait RustInterfaceJna extends Library {
  def printHello()
  def hello(): String
}