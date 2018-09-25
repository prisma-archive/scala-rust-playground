package example

import java.sql.Driver
import java.util.Properties

case class CustomJdbcDriver() extends Driver {
  override def getParentLogger = ???

  override def getMajorVersion                                = 1
  override def getMinorVersion                                = 0
  override def jdbcCompliant()                                = false
  override def acceptsURL(url: String)                        = true
  override def getPropertyInfo(url: String, info: Properties) = Array.empty

  override def connect(url: String, info: Properties) = new CustomJdbcConnection(url)

}
