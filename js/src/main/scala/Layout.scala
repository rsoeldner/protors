import RouterConfig.Page
import japgolly.scalajs.react.extra.router.{Resolution, RouterCtl}
import japgolly.scalajs.react._
import vdom.html_<^._

object Layout {

  def render(c: RouterCtl[Page], r: Resolution[Page]) = {
    <.div(^.backgroundColor := "blue",
          <.div(^.backgroundColor := "red", "blaa"),
          <.div(^.backgroundColor := "yellow", r.render()))
  }

}
