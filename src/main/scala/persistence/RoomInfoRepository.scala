package persistence

import zio._

object DataRepository {

  // 1. Service Definition
  trait DataRepository {
    def insert(data: String): ZIO[Any, Nothing, String]
  }

  // 2. Service Implementation
  // 3. Define Service Dependencies
  import zio.console.Console
  case class DataRepositoryLive(console: Console.Service) extends DataRepository {

    override def insert(data: String): ZIO[Any,Nothing,String] = {
      ZIO.succeed(data)
    }
  }

  // 4. Defining ZLayer
  object DataRepositoryLive {
    val layer: ZLayer[Has[Console.Service], Nothing, Has[DataRepository]] = {
      (DataRepositoryLive(_)).toLayer
    }
  }

  // 5. Accessor Methods
  object DataRepository {
    def insert(data: String): ZIO[Has[DataRepository], Nothing, String] = ZIO.serviceWith[DataRepository](_.insert(data))
  }
}