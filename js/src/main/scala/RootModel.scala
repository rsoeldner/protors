

case class RootModel(isLoggedIn: Boolean)


object RootModel {
  def initial = RootModel(isLoggedIn = false)
}