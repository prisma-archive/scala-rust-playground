#!/usr/bin/env bash
rm hello
echo ">>>>>>>>>>>> Compiling the rust sources"
rustc --out-dir . src/main/rust/hello.rs
echo ">>>>>>>>>>>> Generating the Header files"
cbindgen -o hello.h -l C src/main/rust/hello.rs
echo ">>>>>>>>>>>> Compiling the scala sources"
sbt package
echo ">>>>>>>>>>>> Generating the native image"
native-image --no-server --verbose -H:Name=hello -H:Class=example.Hello -H:+JNI -H:IncludeResources=libhello.dylib -Djna.debug_load=true -Djna.library.path=`pwd`/jnalib -H:JNIConfigurationFiles=`pwd`/jniconfig -cp target/scala-2.12/hello-scala_2.12-0.1.0-SNAPSHOT.jar:$HOME/.ivy2/cache/net.java.dev.jna/jna/jars/jna-4.5.2.jar:$HOME/.sbt/boot/scala-2.12.6/lib/scala-library.jar
echo ">>>>>>>>>>>> Running the binary"
./hello