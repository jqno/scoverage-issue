import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import org.scalastyle.sbt.ScalastylePlugin.{scalastyle, scalastyleConfig}
import sbt.Keys._
import sbt._
import scoverage.ScoverageKeys.coverageEnabled
import wartremover._

import scalariform.formatter.preferences._

object StaticAnalysis {
  // scalastyle:off underscore.import multiple.string.literals

  val staticAnalysisSettings = {
    lazy val compileScalastyle = taskKey[Unit]("Runs Scalastyle on production code")
    lazy val testScalastyle = taskKey[Unit]("Runs Scalastyle on test code")

    Seq(
      // Static analysis
      coverageEnabled := false, // disable because of issue #5
      scalastyleConfig in Compile := (baseDirectory in ThisBuild).value / "project" / "scalastyle-config.xml",
      scalastyleConfig in Test := (baseDirectory in ThisBuild).value / "project" / "test-scalastyle-config.xml",
      // The line below is needed until this issue is fixed: https://github.com/scalastyle/scalastyle-sbt-plugin/issues/44
      scalastyleConfig in scalastyle := (baseDirectory in ThisBuild).value / "project" / "test-scalastyle-config.xml",

      compileScalastyle := scalastyle.in(Compile).toTask("").value,
      testScalastyle := scalastyle.in(Test).toTask("").value,

      ScalariformKeys.preferences := ScalariformKeys.preferences.value
        .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, true)
        .setPreference(PreserveSpaceBeforeArguments, true)
        .setPreference(SpacesAroundMultiImports, false)
    )
  }

  val wartRemoverAndPlaywartsSettings = {
    val playwartsVersion = "0.12"

    Seq(
      wartremoverErrors ++= Seq(
        Wart.Any, Wart.Any2StringAdd, Wart.EitherProjectionPartial, Wart.Enumeration, Wart.ExplicitImplicitTypes,
        Wart.JavaConversions, Wart.ListOps, Wart.MutableDataStructures, Wart.Option2Iterable,
        Wart.Product, Wart.Return, Wart.Serializable, Wart.TryPartial, Wart.OptionPartial
      ),
      wartremoverExcluded += crossTarget.value / "routes" / "main" / "router" / "Routes.scala",
      wartremoverExcluded += crossTarget.value / "routes" / "main" / "router" / "RoutesPrefix.scala",
      wartremoverExcluded += crossTarget.value / "routes" / "main" / "controllers" / "ReverseRoutes.scala",
      wartremoverExcluded += crossTarget.value / "routes" / "main" / "controllers" / "javascript" / "JavaScriptReverseRoutes.scala",

      // Wartremover voor het PlayFramework en Slick

      libraryDependencies += "org.danielnixon" %% "playwarts" % playwartsVersion,

      wartremoverClasspaths += s"file:${(baseDirectory in ThisBuild).value}/project/playwarts_2.11-$playwartsVersion.jar",

      // Play Framework
      wartremoverWarnings ++= Seq(
        Wart.custom("org.danielnixon.playwarts.AkkaObject"),
        Wart.custom("org.danielnixon.playwarts.CacheObject"),
        Wart.custom("org.danielnixon.playwarts.CookiesPartial"),
        Wart.custom("org.danielnixon.playwarts.CryptoObject"),
        Wart.custom("org.danielnixon.playwarts.FlashPartial"),
        Wart.custom("org.danielnixon.playwarts.FormPartial"),
        Wart.custom("org.danielnixon.playwarts.GlobalSettings"),
        Wart.custom("org.danielnixon.playwarts.HeadersPartial"),
        Wart.custom("org.danielnixon.playwarts.JsLookupResultPartial"),
        Wart.custom("org.danielnixon.playwarts.JsReadablePartial"),
        Wart.custom("org.danielnixon.playwarts.LangObject"),
        Wart.custom("org.danielnixon.playwarts.MessagesObject"),
        Wart.custom("org.danielnixon.playwarts.PlayObject"),
        Wart.custom("org.danielnixon.playwarts.SessionPartial"),
        Wart.custom("org.danielnixon.playwarts.WSObject")
      ),

      wartremoverWarnings ++= Seq(
        Wart.custom("org.danielnixon.playwarts.TestHelpersObject")
      ),

      // Slick
      wartremoverWarnings ++= Seq(
        Wart.custom("org.danielnixon.playwarts.BasicStreamingActionPartial")
      ),

      // Bonus Warts
      wartremoverWarnings ++= Seq(
        Wart.custom("org.danielnixon.playwarts.DateFormatPartial"),
        Wart.custom("org.danielnixon.playwarts.FutureObject"),
        Wart.custom("org.danielnixon.playwarts.GenMapLikePartial"),
        Wart.custom("org.danielnixon.playwarts.GenTraversableLikeOps"),
        Wart.custom("org.danielnixon.playwarts.GenTraversableOnceOps"),
        Wart.custom("org.danielnixon.playwarts.OptionPartial"),
        Wart.custom("org.danielnixon.playwarts.StringOpsPartial"),
        Wart.custom("org.danielnixon.playwarts.TraversableOnceOps"),
        Wart.custom("org.danielnixon.playwarts.TryPartial")
      )
    )
  }
}
