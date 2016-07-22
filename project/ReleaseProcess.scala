import com.ovoenergy.sbt.aws.AWSKeys
import com.typesafe.sbt.SbtNativePackager.Docker
import sbt.Keys._
import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._
import sbtrelease.ReleasePlugin.autoImport._

object ReleaseProcess extends AWSKeys {
  val default = Seq[ReleaseStep] (
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    publishArtifacts,
    releaseStepTask(publish in Docker),
    releaseStepTask(awsPublish),
    setNextVersion,
    commitNextVersion,
    pushChanges
  )
}