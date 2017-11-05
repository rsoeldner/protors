import components.{HomeComponent, LoginComponent, NotFoundComponent}
import japgolly.scalajs.react.{Callback, CallbackTo}
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.Implicits._

object RouterConfig {

  sealed trait Page

  case object LoginPage extends Page

  case object HomePage extends Page

  case object NotFoundPage extends Page

  var isUserLoggedIn = false

  val routerConfig = RouterConfigDsl[Page].buildConfig { dsl =>
    import dsl._

    val cb = CallbackTo.pure(false)

    val privateRoutes =
      ( emptyRule
      | staticRoute(root, HomePage) ~> render(HomeComponent()))
        .addCondition(cb)(page => redirectToPage(LoginPage)(Redirect.Push))

    (privateRoutes
      | staticRoute("#woop", NotFoundPage) ~> render(NotFoundComponent.component())
      | staticRoute("#login", LoginPage) ~> render(LoginComponent())
    ).notFound(redirectToPage(NotFoundPage)(Redirect.Replace))
      .verify(HomePage, LoginPage, NotFoundPage)
      .renderWith(Layout.render)

  }

}
