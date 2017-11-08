lazy val jvmDeps = {
  val http4sVersion = "0.18.0-SNAPSHOT"
  val catsVersion   = "1.0.0-RC1"
  val circeVersion  = "0.9.0-M2"
  val tsecV         = "0.0.1-M4"

  Seq(
    "org.http4s"         %% "http4s-dsl"          % http4sVersion,
    "org.http4s"         %% "http4s-blaze-server" % http4sVersion,
    "org.http4s"         %% "http4s-circe"        % http4sVersion,
    "org.typelevel"      %% "cats-core"           % catsVersion,
    "org.typelevel"      %% "cats-effect"         % "0.5",
    "co.fs2"             %% "fs2-core"            % "0.10.0-M8",
    "io.circe"           %% "circe-core"          % circeVersion,
    "io.circe"           %% "circe-generic"       % circeVersion,
    "io.circe"           %% "circe-parser"        % circeVersion,
    "io.github.jmcardon" %% "tsec-http4s"         % tsecV
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

lazy val root = project
  .in(file("."))
  .aggregate(frontend, backend)
  .settings(
    publish := {},
    publishLocal := {}
  )

lazy val seed = crossProject
  .in(file("."))
  .settings(
    name := "foo",
    version := "0.1-SNAPSHOT"
  )
  .jvmSettings(
    resolvers += "jmcardon at bintray" at "https://dl.bintray.com/jmcardon/tsec",
    resolvers += Resolver.sonatypeRepo("snapshots"),
    scalacOptions := jvmOps,
    libraryDependencies := jvmDeps
  )
  .jsSettings(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "com.github.japgolly.scalajs-react" %%% "core"        % "1.1.1",
      "com.github.japgolly.scalajs-react" %%% "extra"       % "1.1.1",
      "io.suzaku"                         %%% "diode"       % "1.1.2",
      "io.suzaku"                         %%% "diode-react" % "1.1.2"
    ),
    jsDependencies ++= Seq(
      "org.webjars.bower" % "react" % "15.6.1"
        / "react-with-addons.js"
        minified "react-with-addons.min.js"
        commonJSName "React",
      "org.webjars.bower" % "react" % "15.6.1"
        / "react-dom.js"
        minified "react-dom.min.js"
        dependsOn "react-with-addons.js"
        commonJSName "ReactDOM",
      "org.webjars.bower" % "react" % "15.6.1"
        / "react-dom-server.js"
        minified "react-dom-server.min.js"
        dependsOn "react-dom.js"
        commonJSName "ReactDOMServer"
    )
  )

lazy val backend = seed.jvm

lazy val frontend = seed.js
