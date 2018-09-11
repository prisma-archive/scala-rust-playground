package example

import com.sun.jna.{Library, Native}

trait RustInterfaceJna extends Library {
  def printGreeting()
  def hello(): String
}