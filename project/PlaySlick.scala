import com.typesafe.sbt.jse.JsEngineImport.JsEngineKeys
import com.typesafe.sbt.web.Import._
import play.sbt.PlayImport.{cache, filters}
import sbt.Keys.{excludeFilter, includeFilter, libraryDependencies, resolvers}
import sbt._


object PlaySlick{
  val slickPlayDependenciesSettingsKey = SettingKey[Setting[Seq[ModuleID]]]("")

  lazy val testDependencies = Seq[ModuleID](
    "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
    "org.mockito" % "mockito-all" % "1.10.19" % Test,
    "info.cukes" %% "cucumber-scala" % "1.2.4" % Test,
    "info.cukes" % "cucumber-junit" % "1.2.4" % Test
  )

  val silhouetteVersion = "4.0.0"
  lazy val silhouetteSettings = {
    Seq[Def.Setting[_]](
      resolvers ++= Seq("Atlassian Releases" at "https://maven.atlassian.com/public/",
        Resolver.jcenterRepo),
      resolvers += Resolver.sonatypeRepo("releases"),
      libraryDependencies ++= Seq(
        "com.mohiva" %% "play-silhouette" % silhouetteVersion,
        "com.mohiva" %% "play-silhouette-password-bcrypt" % silhouetteVersion,
        "com.mohiva" %% "play-silhouette-crypto-jca" % silhouetteVersion,
        "com.mohiva" %% "play-silhouette-persistence" % silhouetteVersion,
        "com.mohiva" %% "play-silhouette-testkit" % silhouetteVersion % "test",
        "org.webjars" %% "webjars-play" % "2.4.0-1",
        "org.webjars" % "webjars-locator" % "0.29",
        "com.iheart" %% "ficus" % "1.2.3",
        cache,
        filters
      )
    )
  }

  lazy val javascriptBuildSettings = Seq[Def.Setting[_]](
  )

  def slickPlayDependenciesSettings:Def.Initialize[Seq[ModuleID]] = Def.setting{
    val slickPgVersion = "0.14.3"
    Seq(
      "com.typesafe.play" %% "play-slick" % "2.0.2",
      "com.typesafe.play" %% "play-slick-evolutions" % "2.0.2",

      // database
      "org.postgresql" % "postgresql" % "9.4-1206-jdbc42",
      // extensions for postgres
      "com.github.tminglei" %% "slick-pg_core" % slickPgVersion,
      "com.github.tminglei" %% "slick-pg" % slickPgVersion,
      "com.github.tminglei" %% "slick-pg_date2" % slickPgVersion,
      "com.github.tminglei" %% "slick-pg_play-json" % slickPgVersion,

      "com.typesafe.play.modules" %% "play-modules-redis" % "2.5.0"
    )
  }
}
