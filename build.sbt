ThisBuild / organization := "com.adamdabrowski"
ThisBuild / scalaVersion := "3.5.0-RC6"
ThisBuild / version      := "0.0.0-SNAPSHOT"

ThisBuild / semanticdbEnabled := true

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
        )
    )
