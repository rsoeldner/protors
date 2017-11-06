package protors.components

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import protors.Pages.Page

object NavBar {

  lazy val compontent = ScalaComponent
    .builder[RouterCtl[Page]]("NavBar")
      .render_P(router =>
      <.nav(^.cls :="navbar navbar-toggleable-md navbar-light bg-faded",
       <.a( ^.cls := "navbar-brand", "OlaHola!"),

        <.div( ^.cls :="collapse navbar-collapse",
          <.ul( ^.cls :="navbar-nav",
            <.li( ^.cls :="nav-item active",
              <.a( ^.cls :="nav-link", "Home")))),

        <.form(^.cls :="form-inline",
          <.button(^.cls :="btn btn-outline-success", ^.`type` :="button", "login")
        )
      )
    )
    .build


  def apply(router: RouterCtl[Page]) = compontent(router)

}
