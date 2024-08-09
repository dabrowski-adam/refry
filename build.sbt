ThisBuild / organization := "com.adamdabrowski"
ThisBuild / scalaVersion := "3.5.0-RC6"
ThisBuild / version      := "0.0.0-SNAPSHOT"

ThisBuild / usePipelining     := true
ThisBuild / semanticdbEnabled := true

ThisBuild / Compile / run / fork := true

ThisBuild / Test / parallelExecution  := true
ThisBuild / Test / fork               := true
ThisBuild / Test / testForkedParallel := true

lazy val refry = (project in file("."))
    .settings(
        scalacOptions ++= List(
            "-indent",
            "-new-syntax",
            "-deprecation",
            "-feature",
            "-Xmax-inlines:64",
            "-Xkind-projector:underscores",
            "-Yexplicit-nulls",
            "-Wsafe-init",
            "-Wunused:imports",
            "-Wvalue-discard",
            "-Wnonunit-statement",
        ),
        libraryDependencies ++= List(
            "org.scala-lang" %% "scala3-tasty-inspector" % scalaVersion.value,
            "ch.epfl.scala"  %% "tasty-query"            % "1.2.0",
            "org.typelevel"  %% "cats-core"              % "2.12.0",
            "org.typelevel"  %% "cats-effect"            % "3.5.3",
            "co.fs2"         %% "fs2-core"               % "3.10.2",
            "co.fs2"         %% "fs2-io"                 % "3.10.2",
        ) ++ List(
            "com.disneystreaming" %% "weaver-cats"     % "0.8.4",
            "qa.hedgehog"         %% "hedgehog-core"   % "0.10.1",
            "qa.hedgehog"         %% "hedgehog-runner" % "0.10.1",
            "qa.hedgehog"         %% "hedgehog-sbt"    % "0.10.1",
        ).map(_ % Test),
    )
