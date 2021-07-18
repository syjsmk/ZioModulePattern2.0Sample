import zio._
import DataRepository._


object Application extends zio.App {

  val program = DataRepository.insert("data")

  override def run(args: List[String]): URIO[ZEnv,ExitCode] = {
    (for {
      data <- program.provideLayer(DataRepositoryLive.layer)
      _ <- console.putStrLn(data)
    } yield ()).exitCode
  }
}