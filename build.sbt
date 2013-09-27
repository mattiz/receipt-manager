organization := "com.example"

name := "justplayin"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
  "net.databinder" %% "unfiltered-directives" % "0.7.0",
  "net.databinder" %% "unfiltered-filter" % "0.7.0",
  "net.databinder" %% "unfiltered-jetty" % "0.7.0",
  "net.databinder" %% "unfiltered-spec" % "0.7.0" % "test"
)


resolvers ++= Seq(
  "java m2" at "http://download.java.net/maven/2"
)

libraryDependencies ++= Seq(
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016"
)


resolvers += "repo.codahale.com" at "http://repo.codahale.com"

libraryDependencies += "com.codahale" % "jerkson_2.9.1" % "0.5.0"
