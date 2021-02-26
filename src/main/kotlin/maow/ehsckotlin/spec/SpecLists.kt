package maow.ehsckotlin.spec

import maow.ehsckotlin.handler.ConvertHandler

sealed class SpecList<T : Spec>(private val list: List<T>) {
    infix fun convert(lambda: ConvertHandler.() -> Unit) {
        list.forEach { it.convert(lambda) }
    }

    infix fun translate(spec: Spec) {
        list.forEach { it.translate(spec) }
    }

    infix fun pass(option: PassOption) {
        list.forEach { it.pass(option) }
    }
}

class ElementSpecList(list: List<ElementSpec>) : SpecList<ElementSpec>(list)

class AttributeSpecList(list: List<AttributeSpec>) : SpecList<AttributeSpec>(list)