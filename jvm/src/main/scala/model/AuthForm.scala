package model

import cats.effect.Effect
import org.http4s.circe._
import io.circe.generic.auto._


case class AuthForm(username: String, password: String)

object AuthForm {
  implicit def decoder[F[_]: Effect] = jsonOf[F, AuthForm]
}
