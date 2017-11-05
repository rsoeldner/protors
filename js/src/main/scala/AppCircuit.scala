import diode.ActionResult.ModelUpdate
import diode.react.ReactConnector
import diode.{Action, Circuit}

object AppCircuit extends Circuit[RootModel] with ReactConnector[RootModel] {

  case class Login(username: String) extends Action
  case object Reset extends Action


  protected def initialModel: RootModel = RootModel.initial

  protected def actionHandler: AppCircuit.HandlerFunction = (model, action) => action match {
    case Login(username) => if(username equals "Robert") Some(ModelUpdate(model.copy(isLoggedIn = true))) else None
    case Reset => Some(ModelUpdate(RootModel.initial))
  }
}
