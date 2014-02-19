import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "techsupport_ext4_scala"
  val appVersion = "1.0-SNAPSHOT"


  resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/maven-releases/"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    //    anorm,
    "com.typesafe.slick" %% "slick" % "2.0.0",
    "org.slf4j" % "slf4j-nop" % "1.6.4",
    "com.cloudphysics" % "jerkson_2.10" % "0.6.3",
    "com.typesafe.slick" %% "slick-extensions" % "2.0.0"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
    //    resolvers += "Local Ivy Repo" at "/home/hooxin/.ivy2/"
    //    resolvers += "Local Maven Repo" at "/home/hooxin/.m2/"

  )

}
