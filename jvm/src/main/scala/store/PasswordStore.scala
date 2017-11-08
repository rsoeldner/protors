package store

package store

import cats.data.OptionT
import cats.implicits._
import cats.effect.Effect
import model.UserPassword
import tsec.authentication.BackingStore

class PasswordStore[F[_]](implicit val F: Effect[F]) extends BackingStore[F, Int, UserPassword]{

  private[this] val storage = collection.mutable.HashMap.empty[Int, UserPassword]

  def put(elem: UserPassword) = F.pure(storage.+=((elem.id, elem))).map(_ => elem)

  def get(id: Int) = OptionT.fromOption[F](storage.get(id))

  def update(v: UserPassword) = F.pure(storage.update(v.id, v)).map(_ => v)

  def delete(id: Int) = storage.remove(id).fold(F.raiseError[Unit](new IllegalArgumentException))(_ => F.unit)
}
