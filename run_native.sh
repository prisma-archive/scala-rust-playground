#!/usr/bin/env bash
rm example.hello
rustc --out-dir . src/main/rust/hello.rs
sbt package
native-image --verbose -cp target/scala-2.12/hello-scala_2.12-0.1.0-SNAPSHOT.jar:/Users/marcusboehm/.ivy2/cache/net.java.dev.jna/jna/jars/jna-4.5.2.jar:/Users/marcusboehm/.sbt/boot/scala-2.12.6/lib/scala-library.jar -H:Class=example.Hello -H:+JNI -H:IncludeResources=darwin/libhello.dylib -Djava.library.path=. -H:CLibraryPath=.
./example.hello