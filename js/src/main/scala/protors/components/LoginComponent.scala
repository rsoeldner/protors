package protors.components

import diode.react.ModelProxy
import diode.{Action, ActionHandler, ActionResult, ModelRW}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import protors.{AppCircuit, User}


object LoginComponent {

  case class Login(username: String, password: String) extends Action

  class LoginHandler[M](modelRW: ModelRW[M, User]) extends ActionHandler(modelRW) {
    override def handle = {
      case res @ Login(username, password) => {
        println(s"Received Login: $res")
        updated(User(true))
      }
    }
  }


  def submit: Callback = Callback {
    AppCircuit.dispatch(Login("a", "b"))
  }

  lazy val component = ScalaComponent
    .builder[ModelProxy[User]]("LoginComponent")
    .render_P( prop =>
      <.div(
      s"LoginCompontent ${prop()}",
      <.input(^.id := "username", ^.`type` := "text", ^.placeholder := "username"),
      <.input(^.id := "password", ^.`type` := "password", ^.placeholder := "password"),
      <.button("submit", ^.onClick --> submit)
    ))
    .build

  def apply(user: ModelProxy[User]) = component(user)

}
