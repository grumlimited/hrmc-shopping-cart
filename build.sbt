val commonSettings = Seq(
  scalaVersion := "2.13.4"
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "shopping_cart"
  )
  .settings(parallelExecution in Test := false)
  .settings(libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % "2.3.1",
    "co.fs2" %% "fs2-io" % "3.0-5795280",
    "org.scalatest" %% "scalatest" % "3.2.3" % Test)
  )
