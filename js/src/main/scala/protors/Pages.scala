package protors

object Pages {

  sealed trait Page

  case object HomePage extends Page

  case object LoginPage extends Page

}
