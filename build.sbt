import Dependencies.*

version := "2.0.2"

ThisBuild / scalaVersion := Versions.scala
ThisBuild / organization := "dev.jaqubm"
ThisBuild / useCoursier  := false

lazy val root = project
  .in(file("."))
  .enablePlugins(ScalafmtPlugin, BuildInfoPlugin)
  .settings(
    name := "AOC_2024",
    buildInfoKeys := Seq[BuildInfoKey](
      resolvers,
      name,
      version,
      scalaVersion,
      sbtVersion
    ),
    resolvers ++= Seq("utils" at "https://pkgs.dev.azure.com/dh-platforms-devops/app-deng-nas_us/_packaging/com.pg.bigdata/maven/v1"),
    credentials += Credentials(Path.userHome / "credentials.txt"),
    libraryDependencies ++= Seq(
      Libraries.aocScala
    )
  )

lazy val badConsoleFlags = Seq("-Xfatal-warnings", "-Ywarn-unused:imports")
