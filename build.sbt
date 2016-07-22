import com.ovoenergy.sbt.{BasicSettings, BuildInfo, Publish}

BasicSettings.default
Publish.defaultSettings
Distribution.dockerSettings
Revolver.settings

name := "umbrella-api"
releaseProcess := ReleaseProcess.default

BuildInfo.generate()
libraryDependencies ++= Seq(
  "com.ovoenergy" %% "commons-service" % "1.0.+",
  "com.ovoenergy" %% "commons-client" % "1.0.+",
  "com.ovoenergy" %% "commons-monitoring" % "1.0.+",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.1.1",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.2.0",
  "mysql" % "mysql-connector-java" % "5.1.35", //6.0.3",
  "com.ovoenergy" %% "commons-testkit" % "1.0.+" % Test)

enablePlugins(JavaServerAppPackaging, DockerPlugin)