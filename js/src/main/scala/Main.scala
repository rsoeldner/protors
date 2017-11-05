import japgolly.scalajs.react.extra.router.{BaseUrl, Router}
import org.scalajs.dom
import org.scalajs.dom.document


object Main {
  def main(args: Array[String]): Unit = {

    val app = document.getElementById("app")

    val baseUrl = dom.window.location.hostname match {
        case "localhost" | "127.0.0.1" | "0.0.0.0" =>
          BaseUrl.fromWindowUrl(_.takeWhile(_ != '#'))
        case _ =>
          BaseUrl.fromWindowOrigin
      }

    val router = Router(baseUrl = baseUrl, RouterConfig.routerConfig)
    router().renderIntoDOM(app)

  }
}
