package protors


import japgolly.scalajs.react.CallbackTo
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.Implicits._
import protors.components.{HomeComponent, Layout, LoginComponent, NotFoundComponent}

object RoutesCfg {

  sealed trait Page

  case object LoginPage extends Page

  case object HomePage extends Page

  case object NotFoundPage extends Page

  var isUserLoggedIn = false

  val routerConfig = RouterConfigDsl[Page].buildConfig { dsl =>
    import dsl._

    val cb = CallbackTo.pure(false)

    val proxy = AppCircuit.connect(_.user)

    val privateRoutes =
      ( emptyRule
      | staticRoute(root, HomePage) ~> render(HomeComponent()))
        .addCondition(cb)(page => redirectToPage(LoginPage)(Redirect.Push))

    (privateRoutes
      | staticRoute("#woop", NotFoundPage) ~> render(NotFoundComponent.component())
      | staticRoute("#login", LoginPage) ~> renderR(r => proxy( p => LoginComponent(p)))
    ).notFound(redirectToPage(NotFoundPage)(Redirect.Replace))
      .verify(HomePage, LoginPage, NotFoundPage)
      .renderWith(Layout.render)

  }

}
