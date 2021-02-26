package maow.ehsckotlin.spec

import maow.ehsckotlin.handler.ElementHandler

class ElementSpec(handler: ElementHandler, name: String, value: String = "NULL") : Spec(handler, name, value)

class AttributeSpec(handler: ElementHandler, name: String, value: String = "NULL") : Spec(handler, name, value)