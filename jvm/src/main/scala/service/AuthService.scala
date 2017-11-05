package service

import cats.effect._, org.http4s._, org.http4s.dsl.io._
import org.http4s.dsl.Http4sDsl

class AuthService[F[_]: Effect]()  extends Http4sDsl[F]{

  lazy val service = HttpService[F] {
    case POST -> Root / "auth" => ???
 }

}