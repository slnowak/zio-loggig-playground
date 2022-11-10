import org.slf4j.LoggerFactory
import zio.Scope
import zio.UIO
import zio.ZIO
import zio.ZIOAppArgs
import zio.ZIOAppDefault

import java.util.logging.Logger

object ExampleApp extends ZIOAppDefault {

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = app.provideLayer(CustomLogging.live)

  private val app: ZIO[Any, Nothing, Unit] = for {
    _ <- ZIOGreeter.greet("Slawek")
    _ <- Sl4jGreeter.greet("Slawek")
    _ <- JavaGreeter.greet("Slawek")
  } yield ()
}

private object ZIOGreeter {
  def greet(name: String): UIO[Unit] =
    ZIO.logInfo(s"Greeting from zio logs, $name!")
}


private object Sl4jGreeter {
  private val log = LoggerFactory.getLogger(getClass)

  def greet(name: String): UIO[Unit] =
    ZIO.succeed(log.info(s"Greeting from sl4j logs, $name!"))
}

private object JavaGreeter {
  private val log = Logger.getLogger("java-logger")

  def greet(name: String): UIO[Unit] =
    ZIO.succeed(log.info(s"Greeting from java logs, $name!"))
}