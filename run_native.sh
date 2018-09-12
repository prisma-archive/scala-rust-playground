#!/usr/bin/env bash
rm hello
echo ">>>>>>>>>>>> Compiling the rust sources"
rustc --out-dir . src/main/rust/hello.rs
echo ">>>>>>>>>>>> Generating the Header files"
cbindgen -o hello.h -l C src/main/rust/hello.rs
echo ">>>>>>>>>>>> Compiling the scala sources"
sbt package
echo ">>>>>>>>>>>> Generating the native image"
native-image -H:Name=hello --no-server --verbose -cp target/scala-2.12/hello-scala_2.12-0.1.0-SNAPSHOT.jar:/Users/marcusboehm/.ivy2/cache/net.java.dev.jna/jna/jars/jna-4.5.2.jar:/Users/marcusboehm/.sbt/boot/scala-2.12.6/lib/scala-library.jar -H:Class=example.Hello -H:+JNI -H:IncludeResources=libhello.dylib -Djava.library.path=. -H:CLibraryPath=. -Djna.debug_load=true -Djna.library.path=/Users/marcusboehm/R/github.com/graphcool/scala-rust-playground/jnalib -H:JNIConfigurationFiles=/Users/marcusboehm/R/github.com/graphcool/scala-rust-playground/jniconfig
echo ">>>>>>>>>>>> Running the binary"
./hello