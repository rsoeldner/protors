package protors

import japgolly.scalajs.react.extra.router.{BaseUrl, Router}
import org.scalajs.dom.document


object Main {
  def main(args: Array[String]): Unit = {

    val app = document.getElementById("app")

    val router = Router(baseUrl = BaseUrl.until_#, RoutesCfg.routerConfig)
    router().renderIntoDOM(app)

  }
}
