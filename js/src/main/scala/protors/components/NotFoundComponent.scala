package protors.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object NotFoundComponent {
  lazy val component = ScalaComponent.builder[Unit]("NotFound")
    .renderStatic(<.div("Whoop Whoop, page not found!"))
    .build

}
