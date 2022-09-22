ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.9"

lazy val root = (project in file("."))
  .settings(
    name := "branch",
    mainClass in assembly := Some("Main"),
  )

lazy val AkkaVersion = "2.6.9"
lazy val AkkaHttpVersion = "10.2.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe" % "config" % "1.4.0",
  "joda-time" % "joda-time" % "2.10.6",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.0" % Test
)

assembly / assemblyMergeStrategy := {
  case PathList("META-INF") => MergeStrategy.discard
  case PathList("reference.conf") => MergeStrategy.concat
  case PathList("version.conf") => MergeStrategy.concat
  case PathList("module-info.class") => MergeStrategy.discard
  case x if x.endsWith("module-info.class") => MergeStrategy.discard
  case PathList("MANIFEST.MF") => MergeStrategy.discard
  case x if x.endsWith("MANIFEST.MF") => MergeStrategy.discard
  case _ => MergeStrategy.first
}