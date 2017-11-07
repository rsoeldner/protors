package protors.components

import diode.react.ModelProxy
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import protors.Pages.Page
import protors.User

object NavBar {

  case class Props(router: RouterCtl[Page], user: ModelProxy[User])

  lazy val compontent = ScalaComponent
    .builder[Props]("NavBar")
    .render_P { props =>
      val user = props.user()
      val router = props.router

      <.nav(
        ^.cls := "navbar navbar-toggleable-md navbar-light bg-faded",
        <.a(^.cls := "navbar-brand", "OlaHola!"),
        <.div(^.cls := "collapse navbar-collapse",
              <.ul(^.cls := "navbar-nav",
                   <.li(^.cls := "nav-item active", <.a(^.cls := "nav-link",^.href:="#home", "Home")))),
        <.form(
          ^.cls := "form-inline",
          if (user.isLogged)
            <.a(^.cls := "btn btn-outline-secondary","logout")
          else
            <.a(^.cls := "btn btn-outline-success",^.href := "#login", "login")
        )
      )
    }
    .build

  def apply(router: RouterCtl[Page], user: ModelProxy[User]) = compontent(Props(router, user))

}
