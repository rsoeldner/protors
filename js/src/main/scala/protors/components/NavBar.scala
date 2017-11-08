package protors.components

import diode.react.ModelProxy
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import protors.Pages.Page
import protors.components.LoginComponent.Logout
import protors.{AppCircuit, User}
import diode.react.ReactPot._

object NavBar {

  case class Props(router: RouterCtl[Page], user: ModelProxy[User])

  lazy val compontent = ScalaComponent
    .builder[Props]("NavBar")
    .render_P { props =>
      val router = props.router

      <.nav(
        ^.cls := "navbar navbar-toggleable-md navbar-light bg-faded",
        <.a(^.cls := "navbar-brand", "OlaHola!"),
        <.div(^.cls := "collapse navbar-collapse",
              <.ul(^.cls := "navbar-nav",
                <.li(^.cls := "nav-item active", <.a(^.cls := "nav-link",^.href:="#home", "Home"))
              )),
        <.form(
          ^.cls := "form-inline",
          if (props.user().isLogged)
            <.a(^.cls := "btn btn-outline-secondary","logout", ^.onClick --> props.user.dispatchCB(Logout))
          else
            <.div
        )
      )
    }
    .build

  def apply(router: RouterCtl[Page], user: ModelProxy[User]) = compontent(Props(router, user))

}
