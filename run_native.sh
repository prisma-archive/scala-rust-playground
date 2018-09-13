#!/usr/bin/env bash
rm hello
echo ">>>>>>>>>>>> Compiling the rust sources"
rustc --out-dir . src/main/rust/hello.rs
echo ">>>>>>>>>>>> Generating the Header files"
cbindgen -o hello.h -l C src/main/rust/hello.rs
echo ">>>>>>>>>>>> Compiling the scala sources"
sbt package
echo ">>>>>>>>>>>> Generating the native image"
native-image --no-server --verbose -H:Name=hello -H:Class=example.Hello -H:CLibraryPath=. -cp lib_managed/scala-library.jar:lib_managed/jna-4.5.2.jar:lib_managed/play-json_2.12-2.6.8.jar:lib_managed/play-functional_2.12-2.6.8.jar:lib_managed/scala-reflect.jar:lib_managed/macro-compat_2.12-1.1.1.jar:lib_managed/joda-time-2.9.9.jar:lib_managed/jackson-core-2.8.9.jar:lib_managed/jackson-annotations-2.8.9.jar:lib_managed/jackson-databind-2.8.9.jar:lib_managed/jackson-datatype-jdk8-2.8.9.jar:lib_managed/jackson-datatype-jsr310-2.8.9.jar:target/scala-2.12/hello-scala_2.12-0.1.0-SNAPSHOT.jar
echo ">>>>>>>>>>>> Running the binary"
./hello