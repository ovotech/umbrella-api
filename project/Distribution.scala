import com.amazonaws.regions.Regions
import com.ovoenergy.sbt.aws.AWSPlugin._
import com. typesafe.sbt.SbtNativePackager.Docker
import sbt.Keys._

object Distribution {
  def dockerSettings = Seq(
    packageName in Docker := name.value,
    dockerRepository := Some("ovotech"),
    dockerUpdateLatest := true,
    dockerExposedPorts := Seq(8080, 8081),
    awsBucket := "ovo-docker-apps",
    awsRegion := Regions.EU_WEST_1,
    awsVersion := sys.env.getOrElse("GO_PIPELINE_COUNTER", ""))
}