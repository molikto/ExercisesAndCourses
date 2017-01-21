

import sbt.Project.projectToRef

val ScalaVersion = "2.11.8"


name := "ReadAndLearnTogether"

version := "1.0"

lazy val clients = Seq(client)

lazy val server = (project in file("server")).settings(
  scalaVersion := ScalaVersion,
  libraryDependencies ++= Seq(
    "com.lihaoyi" % "upickle_2.11" % "0.4.1",
    "com.lihaoyi" % "autowire_2.11" % "0.2.5",
    "com.lihaoyi" % "scalatags_2.11" % "0.6.0",
    "com.typesafe.akka" % "akka-actor_2.11" % "2.4.9-RC2",
    "io.spray" %  "spray-can_2.11"     % "1.3.3",
    "io.spray" %  "spray-routing_2.11" % "1.3.3",
    "org.webjars" % "bootstrap" % "4.0.0-alpha.2"
  )
).dependsOn(sharedJVM)


lazy val sharedJS = shared.js
lazy val sharedJVM = shared.jvm

lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared")).
  settings(scalaVersion := ScalaVersion)




lazy val client = (project in file("client")).settings(
  scalaVersion := ScalaVersion,
  persistLauncher := true,
  persistLauncher in Test := false,
  scalaJSUseRhino in Global := false,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.1",
    "com.lihaoyi" %%% "scalatags" % "0.6.0",
    "com.lihaoyi" % "upickle_sjs0.6_2.11" % "0.4.1",
    "com.lihaoyi" %%% "autowire" % "0.2.5"
  )
).enablePlugins(ScalaJSPlugin).dependsOn(sharedJS)





def copyFile(f: String, t: String): Unit = {
  import java.nio.file.Files
  import java.nio.file.FileSystems
  import java.nio.file.StandardCopyOption._
  val from = FileSystems.getDefault.getPath(f)
  val to = FileSystems.getDefault.getPath(t)
  Files.copy(from, to, REPLACE_EXISTING)
}

def mkdirs(s: String): Unit = {
  new File(s).mkdirs()
}

lazy val taskDevCopyFiles = taskKey[Unit]("copy files")

taskDevCopyFiles := {
  mkdirs("server/resources")
  copyFile("client/target/scala-2.11/client-fastopt.js", "server/resources/client.js")
}


commands += Command.command("dev") {
  "client/fastOptJS" ::
  "taskDevCopyFiles" ::
  "server/re-start" ::
  _
}

lazy val taskDeployCopyFiles = taskKey[Unit]("deploy copy files")

taskDeployCopyFiles := {
  mkdirs("deploy/resources")
  copyFile("client/target/scala-2.11/client-opt.js", "deploy/resources/client.js")
  copyFile("server/target/scala-2.11/server-assembly-0.1-SNAPSHOT.jar", "deploy/server.jar")
}

commands += Command.command("deploy") {
  "client/fullOptJS" ::
  "server/assembly" ::
  "taskDeployCopyFiles" ::
  _
}


