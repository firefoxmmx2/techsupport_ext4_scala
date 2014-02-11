import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "techsupport_ext4_scala"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "org.squeryl" % "squeryl_2.10" % "0.9.5-6",
    "com.cloudphysics" % "jerkson_2.10" % "0.6.3"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
