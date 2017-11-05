package protors

import diode.react.ReactConnector
import diode.{Action, Circuit}
import protors.components.LoginComponent

object AppCircuit extends Circuit[RootModel] with ReactConnector[RootModel] {

  case object Reset extends Action


  protected def initialModel: RootModel = RootModel.initial

  override def actionHandler = composeHandlers(new LoginComponent.LoginHandler(zoomTo(_.user)))
}
