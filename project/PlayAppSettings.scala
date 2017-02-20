import BuildSettings._
import PlaySlick._
import com.typesafe.sbt.web.Import._
import play.sbt.PlayImport._
import play.sbt.routes.RoutesKeys._
import sbt.Defaults
import sbt.Keys._
import sbt._

// scalastyle:off underscore.import multiple.string.literals field.name

object PlayAppSettings{
  lazy val playAppSettings = Seq(
    libraryDependencies ++= slickPlayDependenciesSettings.value,

    dependencyOverrides ++= Set( // fixes eviction warnings
      "org.webjars" % "webjars-locator" % "0.29",
      "com.google.code.findbugs"%"jsr305" % "3.0.1",
      "com.typesafe.play"%%"play-slick" % "2.0.2",
      "org.webjars.npm" % "minimatch" % "2.0.10"
    ),
    resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases", // Specs2 depends on this
    libraryDependencies ++= Seq(
      evolutions,
      cache,
      ws,
      filters
    ),
    libraryDependencies ++= testDependencies,
    routesGenerator := InjectedRoutesGenerator
  ) ++ silhouetteSettings ++ javascriptBuildSettings ++ inConfig(FunTest)(Defaults.testTasks) ++ inConfig(CucumberTest)(Defaults.testTasks)

}
