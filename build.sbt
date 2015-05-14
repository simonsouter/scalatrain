organization := "com.typesafe.training"

name := "scala-train"

version := "3.0.0"

scalaVersion := Version.scala

// The Typesafe repository
resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "mvnrepository" at "http://mvnrepository.com/artifact/"

libraryDependencies ++= Dependencies.scalaTrain
libraryDependencies += "org.joda" % "joda-money" % "0.10.0"

scalacOptions ++= List(
  "-unchecked",
  "-deprecation",
  "-language:_",
  "-target:jvm-1.6",
  "-encoding", "UTF-8"
)

initialCommands in console := "import com.typesafe.training.scalatrain._"

initialCommands in (Test, console) := (initialCommands in console).value + ",TestData._"
