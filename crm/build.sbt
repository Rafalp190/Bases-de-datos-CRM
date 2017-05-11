import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "UVG",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT",
      resolvers += Resolver.sonatypeRepo("releases")
    )),
    name := "CRM",

    libraryDependencies ++= Seq(
  							"org.scalikejdbc" %% "scalikejdbc"       % "2.5.1",
 							"com.h2database"  %  "h2"                % "1.4.193",
  							"ch.qos.logback"  %  "logback-classic"   % "1.2.1",
  							"org.mongodb.scala" %% "mongo-scala-driver" % "2.0.0",
  							"org.scalafx" %% "scalafx" % "8.0.102-R11",
  							scalaTest % Test,
  							"com.danielasfregola" %% "twitter4s" % "5.1"
	),
    
unmanagedJars in Compile += {
  val ps = new sys.SystemProperties
  val jh = ps("java.home")
  Attributed.blank(file(jh) / "lib/ext/jfxrt.jar")
  }
)