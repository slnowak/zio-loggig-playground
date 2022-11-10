name := "zio-logging-test"

version := "0.1"

scalaVersion := "2.13.10"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "2.0.3",
  "dev.zio" %% "zio-logging" % "2.1.3",
  "dev.zio" %% "zio-logging-slf4j" % "2.1.3",
  "org.slf4j" % "slf4j-api" % "2.0.3",
  "org.slf4j" % "jul-to-slf4j" % "2.0.3",
  "ch.qos.logback" % "logback-classic" % "1.4.4"
)
