package store

import cats._
import cats.data._
import cats.effect._
import tsec.authentication.BackingStore
import scala.collection.mutable

object InMemoryFactory {

  trait WithFind[F[_], I, V] {
    def find(func: V => Boolean): OptionT[F, (I, V)]
  }

  def apply[F[_], I, V](getId: V => I)(implicit F: Sync[F]) = new BackingStore[F, I, V] with WithFind[F,I,V] {
    private val storageMap = mutable.HashMap.empty[I, V]

    def put(elem: V): F[V] = {
      val map = storageMap.put(getId(elem), elem)
      if (map.isEmpty)
        F.pure(elem)
      else
        F.raiseError(new IllegalArgumentException)
    }

    def get(id: I): OptionT[F, V] =
      OptionT.fromOption[F](storageMap.get(id))

    def find(func: V => Boolean): OptionT[F, (I, V)] = OptionT.fromOption[F](storageMap.find(p => func(p._2)))

    def update(v: V): F[V] = {
      storageMap.update(getId(v), v)
      F.pure(v)
    }

    def delete(id: I): F[Unit] =
      storageMap.remove(id) match {
        case Some(_) => F.unit
        case None => F.raiseError(new IllegalArgumentException)
      }
  }
}