import sbt.Defaults._

// scalastyle:off multiple.string.literals

// set sbt to use eclipse aether instead of ivy
addMavenResolverPlugin

resolvers += "com-mvn" at "https://repo.lightbend.com/commercial-releases/"
resolvers += Resolver.url("com-ivy",url("https://repo.lightbend.com/commercial-releases/"))(Resolver.ivyStylePatterns)
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.4")


/*
 * Static analysis
 */
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.3.5")
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.8.0")
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")
addSbtPlugin("org.brianmckenna" % "sbt-wartremover" % "0.14")

