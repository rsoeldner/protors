import cats.data.OptionT
import cats.effect.Effect

package object service {

  case object LoginError extends Throwable {
    override def fillInStackTrace(): Throwable = null
  }

  implicit final class LoginErrorOps[T, F[_]](val value: OptionT[F, T]) extends AnyVal {
    def raiseLoginError(implicit F: Effect[F]) = value.getOrElseF(F.raiseError(LoginError))
  }

}
