build:
	sbt prisma-native-image:packageBin

run: build
	 ./target/prisma-native-image/scala-rust-playground -XX:+VerboseGC -XX:MaximumHeapSizePercent=1 -XX:+PrintGC -XX:+PrintGCSummary -XX:+PrintGCTimeStamps -XX:+PrintGCTimes
