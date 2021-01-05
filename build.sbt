val commonSettings = Seq(
  scalaVersion := "2.12.8"
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "shopping_cart"
  )
  .settings(libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    "org.scalacheck" %% "scalacheck" % "1.14.0" % Test)
  )

