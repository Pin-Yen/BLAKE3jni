inThisBuild(
  List(
    organization := "io.lktk",
    homepage := Some(url("https://lktk.io")),
    licenses := List(
      "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
    ),
    developers := List(
      Developer(
        "sken",
        "sken",
        "sken77@pm.me",
        url("https://www.github.com/sken77")
      )
    )
  )
)

publishMavenStyle := true

// GitHub Package Registry settings
publishTo := {
  val githubOwner = "Pin-Yen"
  val githubToken = sys.env.get("GITHUB_TOKEN").orElse(sys.error("GitHub token is required"))
  val githubPackageRegistry = s"https://maven.pkg.github.com/$githubOwner/BLAKE3jni"
  if (isSnapshot.value) {
    Some("GitHub Packages" at s"$githubPackageRegistry/snapshots")
  } else {
    Some("GitHub Packages" at s"$githubPackageRegistry/releases")
  }
}

credentials += Credentials(
  "GitHub Package Registry",
  "maven.pkg.github.com",
  sys.env.getOrElse("GITHUB_USERNAME", ""),
  sys.env.getOrElse("GITHUB_TOKEN", "")
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")

name := "BLAKE3jni"

version := "0.2.2"

libraryDependencies ++= List(
  "com.novocode" % "junit-interface" % "0.11" % "test",
  "org.scijava" % "native-lib-loader" % "2.5.0",
  "org.slf4j" % "slf4j-nop" % "1.7.30"
)

Compile / unmanagedResourceDirectories += baseDirectory.value / "natives"

//ignore everything related to scala
Compile / unmanagedSourceDirectories := (Compile / javaSource).value :: Nil
Test / unmanagedSourceDirectories := (Test / javaSource).value :: Nil
autoScalaLibrary := false // exclude scala-library from dependencies
crossPaths := false // drop off Scala suffix from artifact names.
