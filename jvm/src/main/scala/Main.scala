import cats.effect.IO
import fs2.Stream
import org.http4s.server.blaze._
import org.http4s.util.StreamApp
import org.http4s.util.ExitCode
import service.{AuthService, UserService}
import org.http4s.implicits._
import cats.implicits._
import org.http4s.HttpService

object Main extends StreamApp[IO] {
  override def stream(args: List[String], requestShutdown: IO[Unit]): Stream[IO, ExitCode] = {

    val authService = new AuthService[IO]
    val userService = new UserService[IO]()

    def service: HttpService[IO] = authService.service <+> userService.service

    BlazeBuilder[IO]
      .bindHttp(8080, "localhost")
      .mountService(service, "/")
      .serve

  }
}
