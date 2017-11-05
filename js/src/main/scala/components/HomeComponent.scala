package components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object HomeComponent {

  lazy val  component = ScalaComponent.builder[Unit]("A")
    .renderStatic(<.div("a"))
    .build

  def apply() = component()

}
