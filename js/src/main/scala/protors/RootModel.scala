package protors


case class User(isLogged: Boolean)

case class RootModel(user: User)

object RootModel {
  def initial = RootModel(User(false))
}