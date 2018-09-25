#!/bin/bash

set -e

rm -f hello hello.h
echo ">>>>>>>>>>>> Compiling the rust sources"
cd hello-rs && cargo build && cd -
echo ">>>>>>>>>>>> Generating the Header files"
cbindgen -o hello.h -l C hello-rs/src/lib.rs
echo ">>>>>>>>>>>> Compiling the scala sources"
sbt package -java-home $GRAAL_HOME
echo ">>>>>>>>>>>> Generating the native image"
native-image --no-server --report-unsupported-elements-at-runtime --verbose -H:Name=hello -H:Class=example.Hello -H:CLibraryPath=hello-rs/target/debug -cp lib_managed/scala-library.jar:lib_managed/jna-4.5.2.jar:lib_managed/play-json_2.12-2.6.8.jar:lib_managed/play-functional_2.12-2.6.8.jar:lib_managed/scala-reflect.jar:lib_managed/macro-compat_2.12-1.1.1.jar:lib_managed/joda-time-2.9.9.jar:lib_managed/jackson-core-2.8.9.jar:lib_managed/jackson-annotations-2.8.9.jar:lib_managed/jackson-databind-2.8.9.jar:lib_managed/jackson-datatype-jdk8-2.8.9.jar:lib_managed/jackson-datatype-jsr310-2.8.9.jar:lib_managed/postgresql-42.2.2.jar:lib_managed/jooq-3.11.0.jar:lib_managed/jaxb-api-2.3.0.jar:lib_managed/jooq-scala_2.12-3.11.0.jar:target/scala-2.12/hello-scala_2.12-0.1.0-SNAPSHOT.jar
echo ">>>>>>>>>>>> Running the binary"
./hello