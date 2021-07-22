import zio._
import persistence.DataRepository._
import service.MyService._

object Application extends zio.App {

  val layers = DataRepositoryLive.layer >>> MyServiceLive.layer
  val program = MyService.run("data")

  override def run(args: List[String]): URIO[ZEnv,ExitCode] = {
    (for {
      data <- program.provideLayer(layers)
      _ <- console.putStrLn(data)
    } yield ()).exitCode
  }
}