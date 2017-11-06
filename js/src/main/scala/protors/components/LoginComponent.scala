package protors.components

import diode.react.ModelProxy
import diode.{Action, ActionHandler, ActionResult, ModelRW}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.Input
import protors.{AppCircuit, User}
import org.scalajs.dom.{document, Blob}

object LoginComponent {

  case class Login(username: String, password: String) extends Action

  class LoginHandler[M](modelRW: ModelRW[M, User]) extends ActionHandler(modelRW) {
    override def handle = {
      case Login(username, password) => {
        if(username == "robert")
          updated(User(true))
        else
          noChange
      }
    }
  }

  def submit: Callback = Callback {
    val username = document.getElementById("username").asInstanceOf[Input].value.trim
    val password = document.getElementById("password").asInstanceOf[Input].value.trim
    AppCircuit.dispatch(Login(username, password))
  }

  lazy val component = ScalaComponent
    .builder[ModelProxy[User]]("LoginComponent")
    .render_P(prop =>
      if(!prop.value.isLogged) {
      <.div(
        <.input(^.id := "username", ^.`type` := "text", ^.placeholder := "username"),
        <.input(^.id := "password", ^.`type` := "password", ^.placeholder := "password"),
        <.button("submit", ^.onClick --> submit)
    )}
      else <.div())
    .build

  def apply(user: ModelProxy[User]) = component(user)

}
