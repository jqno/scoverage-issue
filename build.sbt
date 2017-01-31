lazy val commonSettings = Seq(
  organization := "com.example",
  scalaVersion := "2.11.8",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1",
  testOptions in Test := Seq(Tests.Filter(s => !s.endsWith("FunSpec"))),
  testOptions in FunTest := Seq(Tests.Filter(s => s.endsWith("FunSpec")))
)

lazy val root = (project in file("."))
  .configs(FunTest)
  .dependsOn(sub)
  .aggregate(sub)
  .settings(commonSettings: _*)
  .settings(inConfig(FunTest)(Defaults.testTasks): _*)
  .settings(
    name := "scoverage issue repro"
  )

lazy val sub = (project in file("subproject"))
  .configs(FunTest)
  .settings(commonSettings: _*)
  .settings(inConfig(FunTest)(Defaults.testTasks): _*)
  .settings(
    name := "scoverage issue repro subproject"
  )

lazy val FunTest = config("fun") extend(Test)

