name := "typed_actor_example"

organization := "com.navneetgupta"

scalaVersion := "2.12.6"

version      := "0.1.0"

libraryDependencies ++= {
	val akkaVersion = "2.5.13"
	val akkaTypedVersion = "2.5.16"
	Seq(
    	"com.typesafe.akka" %% "akka-actor" % akkaVersion,
    	"com.typesafe.akka" %% "akka-actor-typed" % akkaTypedVersion
	)
}

