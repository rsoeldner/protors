package protors.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.{Resolution, RouterCtl}
import japgolly.scalajs.react.vdom.html_<^._
import protors.RoutesCfg.Page

object Layout {

  def render(c: RouterCtl[Page], r: Resolution[Page]) = {
    <.div(^.backgroundColor := "blue",
          <.div(^.backgroundColor := "red", "blaa"),
          <.div(^.backgroundColor := "yellow", r.render()))
  }

}
