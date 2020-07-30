name := "intellij-proxy-password-util"

version := "0.1"

scalaVersion := "2.13.3"

scalacOptions += "-deprecation"

libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % Test

testOptions in Test += Tests.Argument(TestFrameworks.JUnit, "-a", "-v", "-s")

test in assembly := {}