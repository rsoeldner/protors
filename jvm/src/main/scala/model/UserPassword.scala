package model

import tsec.passwordhashers.imports.SCrypt

case class UserPassword(id: Int, hash: SCrypt)
