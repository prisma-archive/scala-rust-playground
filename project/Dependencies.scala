import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest"     %% "scalatest" % "3.0.5"
  lazy val jna       = "net.java.dev.jna"  % "jna"        % "4.5.2"
  lazy val playJson  = "com.typesafe.play" %% "play-json" % "2.6.8"
  lazy val jooq = Vector(
    "org.jooq" % "jooq"        % "3.11.0",
    "org.jooq" %% "jooq-scala" % "3.11.0"
  )
}
