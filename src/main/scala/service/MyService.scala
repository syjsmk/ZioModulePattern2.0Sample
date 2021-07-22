package service

import zio._
import persistence.DataRepository._

object MyService {

  // 1. Service Definition
  trait MyService {
    def run(data: String): ZIO[Any, Nothing, String]
  }

  // 2. ServiceImplementation
  // 3. Define Service Dependencies
  case class MyServiceLive(dataRepository: DataRepository) extends MyService {

    override def run(data: String): ZIO[Any, Nothing, String] = {
      dataRepository.insert(data)
    }

  }

  // 4. Defining ZLayer
  object MyServiceLive {
    val layer: ZLayer[Has[DataRepository], Nothing, Has[MyService]] = {
      (MyServiceLive(_)).toLayer
    }
  }

  // 5. Accessor Methods
  object MyService {
    def run(data: String): ZIO[Has[MyService], Nothing, String] = ZIO.serviceWith[MyService](_.run(data))
  }
  
}
