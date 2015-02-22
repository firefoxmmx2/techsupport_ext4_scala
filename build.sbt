name := """techsupport_ext4_scala"""

version := "2.0.0"

scalaVersion := "2.11.5"

crossScalaVersions := Seq("2.10.5", scalaVersion.value)

libraryDependencies ++= Seq(
  "org.virtuslab" %% "unicorn-play" % "0.6.2",
  "com.h2database" % "h2" % "1.4.181" % "test",
  "com.typesafe.slick" %% "slick" % "2.1.0",
  jdbc,
  cache,
  filters,
  "org.squeryl" %% "squeryl" % "0.9.6-RC3",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.cloudphysics" % "jerkson_2.10" % "0.6.3",
  "com.typesafe.slick" %% "slick-extensions" % "2.1.0",
  "com.typesafe.play" %% "play-slick" % "0.8.1",
  "com.github.tototoshi" %% "slick-joda-mapper" % "1.2.0",
  "joda-time" % "joda-time" % "2.4",
  "org.joda" % "joda-convert" % "1.6"
//  "org.squeryl" %% "squeryl" % "0.9.6-RC3",
  // Add your own project dependencies in the form:
  // "group" % "artifact" % "version"
)

//unmanagedClasspath in run +=  file("/home/hooxin/Pro/jrebel_6.0.3-agent-crack")

//seq(jrebelSettings: _*)

//jrebel.enabled := true


lazy val `techsupport_ext4_scala` = (project in file(".")).enablePlugins(PlayScala, SbtWeb)
