package protors

import diode.react.ModelProxy
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.html_<^.{<, ^}
import org.scalajs.dom.document
import protors.Pages._
import protors.components.{HomeComponent, LoginComponent, NavBar}

object Main {
  def main(args: Array[String]): Unit = {

    val app = document.getElementById("app")



    def layout(c: RouterCtl[Page], r: Resolution[Page]) = {

      val proxy = AppCircuit.connect(_.user)
      <.div(^.cls :="container",
        proxy((p: ModelProxy[User]) => NavBar(c, p)),
        <.div(^.cls := "container", r.render())
      )
    }

    val routerConfig = RouterConfigDsl[Page].buildConfig { dsl =>
      import dsl._

      (emptyRule
        | staticRoute(root, HomePage) ~> render(HomeComponent())
        | staticRoute("#login", LoginPage) ~> render(LoginComponent()))
        .notFound(redirectToPage(HomePage)(Redirect.Replace))
        .renderWith(layout)
    }

    val router = Router(baseUrl = BaseUrl.until_#, routerConfig)
    router().renderIntoDOM(app)

  }
}
