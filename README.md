# scala-rust-playground

This repo explores the interop with native code inside a native image generated with Graal. It explores the C API provided by Graal. Additionally it also tries to use [JNA](https://github.com/java-native-access/jna) as many existing JVM applications rely on this library for native interop. It would be great it Graal would work with JNA out of the box.

## To test the JNA interop do the following:
1. Open `Hello.scala` and adapt the code to call the `testJna` method.
2. Run the `run_native_jna.sh` script.

This will fail with: `library not found for -lhello`

## To test the interop with Graals new native API:
1. Open `Hello.scala` and adapt the code to call the `testGraalsCApi` method.
2. Run the `run_native.sh` script.

This will work.
