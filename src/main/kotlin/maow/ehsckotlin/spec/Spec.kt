package maow.ehsckotlin.spec

import maow.ehsckotlin.handler.ConvertHandler
import maow.ehsckotlin.handler.ElementHandler

open class Spec(private val handler: ElementHandler, val name: String, val value: String = "") {
    infix fun convert(lambda: ConvertHandler.() -> Unit) {
        handler.convert(this, lambda)
    }

    infix fun translate(spec: Spec) {
        handler.translate(this, spec)
    }

    infix fun pass(option: PassOption) {
        handler.pass(this, option)
    }
}

enum class PassOption {
    ALL,
    NAME_ONLY
}