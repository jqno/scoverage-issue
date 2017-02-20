import sbt.Keys._
import sbt.Tests
import sbt._

object BuildSettings{
  lazy val Lint = sbt.config("lint") extend Test // scalastyle:ignore
  lazy val FunTest = sbt.config("fun") extend Test // scalastyle:ignore
  lazy val CucumberTest = sbt.config("cucumber") extend Test // scalastyle:ignore

  lazy val lintSettings =
    Defaults.compileSettings ++
      Seq(
        sources in Lint := (sources in Lint).value ++ (sources in Compile).value ++ (sources in Test).value
      )

  lazy val projectBuildSettings = {
    def javaOptionsConf = {
      val confFile = sys.env.getOrElse("PLAY_CONF_FILE", "application.local.conf")
      Seq(s"-Dconfig.resource=$confFile","-Dlogger.resource=logback.local.xml")
    }
    Seq(
      updateOptions := updateOptions.value.withCachedResolution(true),
      incOptions := incOptions.value.withNameHashing(true),
      scalacOptions ++= Seq("-encoding", "UTF8", "-target:jvm-1.8",
        "-deprecation", "-feature", "-unchecked", "-Xlint",
        "-Ywarn-dead-code", "-Ywarn-unused", "-Ywarn-adapted-args",
        "-Xlint:-missing-interpolator"
      ),
      scalacOptions in Compile := (scalacOptions in Compile).value filterNot { _ contains "wartremover" },
      scalacOptions in Test := (scalacOptions in Test).value filterNot { _ contains "wartremover" },
      scalacOptions in Lint += "-Xfatal-warnings",
      scalaVersion := "2.11.8",
      javaOptions in Compile ++= javaOptionsConf,
      javaOptions in Test ++= javaOptionsConf,
      javaOptions in FunTest ++= Seq(s"-Dconfig.resource=application.test.conf"),
      testOptions in Test := Seq(Tests.Filter(s => s.endsWith("Spec") && !s.endsWith("IntSpec"))),
      testOptions in FunTest := Seq(Tests.Filter(s => s.endsWith("IntSpec"))),
      testOptions in CucumberTest := Seq(Tests.Filter(s => s.endsWith("RunCucumber")))
    )
  }
}
