// scalastyle:off underscore.import multiple.string.literals field.name

import com.typesafe.sbt.web.SbtWeb.autoImport._
import play.sbt.routes.RoutesKeys.routesGenerator
import sbt.Keys.{javaOptions, libraryDependencies, testOptions}
import sbt.{inConfig, _}
import PlaySlick._
import StaticAnalysis._
import BuildSettings._
import PlayAppSettings._


name := """my app"""
version := "0.2"
organization := "my organization"


lazy val nextstep = (project in file("."))
  .enablePlugins(PlayScala, SbtWeb)
  .configs(FunTest, CucumberTest, Lint)
  .settings(playAppSettings ++ projectBuildSettings)
  .settings(inConfig(Lint) { playAppSettings ++ projectBuildSettings ++ lintSettings ++ wartRemoverAndPlaywartsSettings})

initialize := {
  val required = "1.8"
  val current = sys.props("java.specification.version")
  assert(current == required, s"Unsupported JDK: java.specification.version $current != $required")
}

staticAnalysisSettings

