package service

import cats.effect.Effect
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl

class UserService[F[_]]()(implicit F: Effect[F]) extends Http4sDsl[F] {

  lazy val service = HttpService[F] {
    case request @ POST -> Root / "signup" => ???
  }

}
