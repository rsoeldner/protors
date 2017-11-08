package service

import cats.data.Kleisli
import cats.effect._
import cats.implicits._
import model.AuthForm
import org.http4s._
import org.http4s.dsl.Http4sDsl
import store.{InMemoryFactory, UserStore}
import tsec.authentication.{
  BearerTokenAuthenticator,
  TSecBearerToken,
  TSecMiddleware,
  TSecTokenSettings
}
import tsec.common.SecureRandomId
import org.http4s.circe._
import store.store.PasswordStore
import tsec.passwordhashers.imports.SCrypt
import tsec.common._
import tsec.passwordhashers._
import tsec.passwordhashers.imports._

import concurrent.duration._

class AuthService[F[_]]()(implicit val F: Effect[F]) extends Http4sDsl[F] {

  val bearerTokenStore =
    InMemoryFactory[F, SecureRandomId, TSecBearerToken[Int]](s => SecureRandomId.coerce(s.id))

  val userStore = new UserStore[F]()

  val passwordStore = new PasswordStore[F]()

  //These are the settings for our bearer token
  val settings: TSecTokenSettings = TSecTokenSettings(
    expirationTime = 10.minutes, //Absolute expiration time
    maxIdle = None
  )

  val bearerTokenAuth = BearerTokenAuthenticator(
    bearerTokenStore,
    userStore,
    settings
  )

  val middleware = TSecMiddleware(Kleisli(bearerTokenAuth.extractAndValidate))

  // borrowed from jose ;-)
  private def checkOrRaise(rawFromLogin: String, hashed: SCrypt): F[Unit] =
    if (rawFromLogin.base64Bytes.toAsciiString.checkWithHash(hashed))
      F.unit
    else
      F.raiseError[Unit](LoginError)

  lazy val service = HttpService[F] {

    case request @ POST -> Root / "auth" =>
      for {
        req   <- request.as[AuthForm]
        user  <- userStore.find(_.username == req.username).raiseLoginError
        hash  <- passwordStore.get(user.id).raiseLoginError
        _     <- checkOrRaise(req.password, hash.hash)
        token <- bearerTokenAuth.create(user.id).getOrElseF(F.raiseError(LoginError))
        resp  = Response[F](Status.Ok)
      } yield bearerTokenAuth.embed(resp, token)

  }

}
