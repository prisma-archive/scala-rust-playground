# scala-rust-playground

This repo explores the interoperability with SubstrateVMs native image code bindings (the C-API provided by Graal) in Scala and Rust.

## Requirements
- Graal RC11
- Latest Rust (>= 1.32.0)

## Usage
- `make build` for build only
- `make run` for building and running.
- Or manually: `sbt` -> `prisma-native-image:packageBin` to build.