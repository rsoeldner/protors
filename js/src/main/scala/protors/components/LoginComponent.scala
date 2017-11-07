package protors.components

import diode.{Action, ActionHandler, ModelRW}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.html.Input
import protors.{AppCircuit, User}
import org.scalajs.dom.document

object LoginComponent {

  case class Login(username: String, password: String) extends Action

  class LoginHandler[M](modelRW: ModelRW[M, User]) extends ActionHandler(modelRW) {
    override def handle = {
      case Login(username, password) => {
        if(username == "robert@robert.com")
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
    .builder[Unit]("LoginComponent")
    .render_P(prop =>
      <.form(
        <.div( ^.cls :="form-group",
          <.label( ^.`for` :="exampleInputEmail1", "Email address"),
          <.input( ^.`type` :="email", ^.cls :="form-control", ^.id :="exampleInputEmail1", ^.placeholder :="Enter email"),
            <.small(^.id :="emailHelp", ^.cls :="form-text text-muted", "We'll never share your email with anyone else."),
        ),
          <.div(^.cls := "form-group",
            <.label(^.`for` :="exampleInputPassword1", "Password"),
            <.input(^.`type` :="password", ^.cls := "form-control", ^.id :="exampleInputPassword1", ^.placeholder :="Password")
          ),
            <.button( ^.`type` :="submit", ^.cls :="btn btn-primary", "Submit", ^.onClick --> submit)
      ))
    .build

  def apply() = component()

}
