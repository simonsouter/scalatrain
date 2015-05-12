import sbt._

object Version {
  val scala        = "2.11.6"
  val scalaParsers = "1.0.3"
  val scalaTest    = "2.2.4"
  val playJson     = "2.3.8"
}

object Library {
  val scalaParsers = "org.scala-lang.modules" %% "scala-parser-combinators" % Version.scalaParsers
  val scalaTest    = "org.scalatest"          %% "scalatest"                % Version.scalaTest
  val playJson     = "com.typesafe.play"      %% "play-json"                % Version.playJson
}

object Dependencies {

  import Library._

  val scalaTrain = List(
    scalaParsers,
    scalaTest % "test",
    playJson
  )
}
