import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "UVG",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "CRM",

    libraryDependencies ++= Seq(
  							"org.scalikejdbc" %% "scalikejdbc"       % "2.5.1",
 							"com.h2database"  %  "h2"                % "1.4.193",
  							"ch.qos.logback"  %  "logback-classic"   % "1.2.1",
  							 "org.twitter4j" % "twitter4j-core" % "3.0.3",
  							"org.twitter4j" % "twitter4j-stream" % "3.0.3",
  							"org.mongodb.scala" %% "mongo-scala-driver" % "2.0.0",
  							"org.scalafx" %% "scalafx" % "8.0.102-R11",
  							scalaTest % Test
	)
  )
