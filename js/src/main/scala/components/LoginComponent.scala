package components


import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object LoginComponent {

  lazy val  component = ScalaComponent.builder[Unit]("LoginComponent")
    .renderStatic(<.div("LoginCompontent"))
    .build

  def apply() = component()

}
