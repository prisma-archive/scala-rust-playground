import Dependencies._
import sbt._

def absolute(relativePathToProjectRoot: String) = {
  s"${System.getProperty("user.dir")}/${relativePathToProjectRoot.stripPrefix("/")}"
}

val buildNativeLib = TaskKey[Unit]("buildNativeLib", "builds the native rust lib")
buildNativeLib := {
  import sys.process._

  println("Building Rust lib.")

  val logger = ProcessLogger(println, println)
  val nativePath = new java.io.File("rust/")

  if ((Process(s"make build", nativePath) ! logger) != 0) {
    sys.error("Rust library build failed.")
  }
}

lazy val root = (project in file("."))
  .enablePlugins(PrismaGraalPlugin)
  .settings(
    inThisBuild(List(
      organization := "io.prisma",
      scalaVersion := "2.12.6",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "scala-rust-playground",
    libraryDependencies ++= allDeps,
    nativeImageOptions ++= Seq(
      "--enable-all-security-services",
      s"-H:CLibraryPath=${absolute("rust/target/debug")}",
      //"--rerun-class-initialization-at-runtime=",
      //"-H:IncludeResources=",
      s"-H:ReflectionConfigurationFiles=${absolute("reflection_config.json")}",
      "--verbose",
      "--no-server",
      //"-H:+AllowVMInspection"
    ),
    unmanagedJars in Compile ++= Seq(file(sys.env("GRAAL_HOME") + "/jre/lib/svm/builder/svm.jar"), file(sys.env("GRAAL_HOME") + "/jre/lib/boot/graal-sdk.jar")),
    compile in Compile := {
      buildNativeLib.value
      (compile in Compile).value
    }
  )

val nativeClasspath = taskKey[String]("The classpath.")

nativeClasspath := {
  val baseDir = baseDirectory.value
  val managedDir = managedDirectory.value
  val packagedFile = Keys.`package`.in(Compile).value.relativeTo(baseDir) // make sure that package is built
  val deps = dependencyClasspath.in(Compile).value.toVector.map { dep ⇒
    dep.data.relativeTo(baseDir).orElse {
      // For some reason sbt sometimes decides to use the scala-library from `~/.sbt/boot` (which is outside of the project dir)
      // As a workaround we copy the file in lib_managed and use the copy instead (shouldn't cause name collisions)
      val inManaged = managedDir / dep.data.name
      IO.copy(Seq(dep.data → inManaged))
      inManaged.relativeTo(baseDir)
    }
  }

  val classpath = (deps :+ packagedFile).flatten
  def relativePath(path: File): String = path.toString.replaceAll("\\\\", "/")
  val classpathStr = classpath.map(relativePath).mkString(":")

  classpathStr
}