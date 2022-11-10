import org.slf4j.bridge.SLF4JBridgeHandler
import zio.Runtime
import zio.Trace
import zio.UIO
import zio.ULayer
import zio.ZIO
import zio.ZLayer
import zio.logging.LogFormat
import zio.logging.backend.SLF4J
import zio.logging.backend.SLF4J.logFormatDefault


object CustomLogging {
  val live: ULayer[Unit] = ZIOCustomLogging.live ++ JavaCustomLogging.live
}

private object ZIOCustomLogging {
  val live: ULayer[Unit] = Runtime.removeDefaultLoggers >>> zioLogger

  private lazy val zioLogger = SLF4J.slf4j(
    format = logFormatDefault + moreAccurateLocation,
    loggerName = (_: Trace) => "zio-logger"
  )

  private lazy val moreAccurateLocation = LogFormat.make { case (builder, trace, _, _, _, _, _, _, _) =>
    val (location, line) = trace match {
      case Trace(location, _, line) => (location, line)
      case _ => ("???", "???")
    }

    builder.appendKeyValue("location", location)
    builder.appendKeyValue("line", line.toString)
    builder
  }
}

private object JavaCustomLogging {
  val live: ULayer[Unit] = ZLayer.fromZIO(configureJavaLoggingSl4jBindings)

  private lazy val configureJavaLoggingSl4jBindings: UIO[Unit] =
    ZIO.succeed {
      SLF4JBridgeHandler.removeHandlersForRootLogger()
      SLF4JBridgeHandler.install()
    }
}
