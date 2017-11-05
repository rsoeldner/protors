
lazy val jvmDeps = {
  val http4sVersion = "0.18.0-SNAPSHOT"
  val catsVersion = "1.0.0-RC1"
  val circeVersion = "0.9.0-M2"

  Seq(
    "org.http4s" %% "http4s-dsl" % http4sVersion,
    "org.http4s" %% "http4s-blaze-server" % http4sVersion,
    "org.typelevel" %% "cats-core" % catsVersion,
    "org.typelevel" %% "cats-effect" % "0.5",
    "co.fs2" %% "fs2-core" % "0.10.0-M8",
    "io.circe" %% "circe-core" % circeVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "io.circe" %% "circe-parser" % circeVersion
  )
}

lazy val jvmOps = Seq(
  "-Ypartial-unification",
  "-deprecation",
  "-encoding",
  "UTF-8", // yes, this is 2 args
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions"
)


lazy val root = project.in(file(".")).
  aggregate(frontend, backend).
  settings(
    publish := {},
    publishLocal := {}
  )

lazy val seed = crossProject.in(file(".")).
  settings(
    name := "foo",
    version := "0.1-SNAPSHOT"
  ).
  jvmSettings(
    resolvers += Resolver.sonatypeRepo("snapshots"),
    scalacOptions := jvmOps,
    libraryDependencies := jvmDeps
  ).
  jsSettings(
    scalaJSUseMainModuleInitializer := true
)

lazy val backend = seed.jvm

lazy val frontend = seed.js
