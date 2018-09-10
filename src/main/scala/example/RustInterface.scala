package example

import com.sun.jna.{Library, Native}

trait RustInterface extends Library {

  def printGreeting()
  def hello(): String
}
