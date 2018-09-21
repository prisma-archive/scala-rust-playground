package example

import java.sql.Driver
import java.util.Properties

case class CustomJdbcDriver() extends Driver {
  override def getParentLogger = ???

  override def getMinorVersion = ???

  override def jdbcCompliant() = ???

  override def acceptsURL(url: String) = ???

  override def getMajorVersion = ???

  override def connect(url: String, info: Properties) = new CustomJdbcConnection()

  override def getPropertyInfo(url: String, info: Properties) = ???
}
