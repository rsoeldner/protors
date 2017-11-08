package store

import cats.data.OptionT
import cats.implicits._
import cats.effect.Effect
import model.User
import tsec.authentication.BackingStore

class UserStore[F[_]](implicit val F: Effect[F]) extends BackingStore[F, Int, User]{

  private[this] val storage = collection.mutable.HashMap.empty[Int, User]

  def put(elem: User) = F.pure(storage.+=((elem.id, elem))).map(_ => elem)

  def get(id: Int) = OptionT.fromOption[F](storage.get(id))

  def update(v: User) = F.pure(storage.update(v.id, v)).map(_ => v)

  def delete(id: Int) = storage.remove(id).fold(F.raiseError[Unit](new IllegalArgumentException))(_ => F.unit)

  def find(p: User => Boolean) = OptionT.fromOption[F](storage.find(tup => p(tup._2)).map(tup => tup._2))
}
