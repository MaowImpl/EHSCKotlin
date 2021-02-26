package maow.ehsckotlin.handler

import maow.ehsckotlin.spec.*
import org.dom4j.DocumentHelper
import org.dom4j.Element
import org.dom4j.Node

class ElementHandler(val element: Element, name: String) {
    val new: Element = DocumentHelper.createElement(name)

    fun element(name: String = "", text: String = ""): ElementSpec {
        return ElementSpec(this, name, text)
    }

    fun elements(vararg names: String): ElementSpecList {
        val list: MutableList<ElementSpec> = mutableListOf()
        names.forEach { name ->
            element.element(name)?.let {
                list += ElementSpec(this, it.name, it.text)
            }}
        return ElementSpecList(list)
    }

    fun attribute(name: String = "", value: String = ""): AttributeSpec {
        return AttributeSpec(this, name, value)
    }

    fun attributes(vararg names: String): AttributeSpecList {
        val list: MutableList<AttributeSpec> = mutableListOf()
        names.forEach { name ->
            element.attribute(name)?.let {
                list += AttributeSpec(this, it.name, it.value)
            }}
        return AttributeSpecList(list)
    }

    fun convert(spec: Spec, lambda: ConvertHandler.() -> Unit) {
        nodes(spec).forEach {
            lambda.invoke(ConvertHandler(it, this))
        }
    }

    fun translate(original: Spec, new: Spec) {
        nodes(original).forEach {
            when (new) {
                is ElementSpec -> {
                    val element = this.new.addElement(new.name)
                    if (it.text.isNotEmpty()) {
                        element.text = it.text
                    }
                }
                is AttributeSpec -> this.new.addAttribute(new.name, it.text)
                else -> throw UnsupportedOperationException()
            }
        }
    }

    fun pass(spec: Spec, option: PassOption) {
        when (option) {
            PassOption.ALL -> {
                val list = nodes(spec)
                list.forEach { it.parent = null }
                list
            }
            PassOption.NAME_ONLY -> listOf(DocumentHelper.createElement(spec.name))
        }.forEach { new.add(it) }
    }

    fun add(spec: Spec) {
        val element = new.addElement(spec.name)
        if (spec.value.isNotEmpty()) {
            element.text = spec.value
        }
    }

    private fun nodes(spec: Spec): List<Node> =
        when (spec) {
            is ElementSpec -> element.elements(spec.name)
            is AttributeSpec -> listOf(element.attribute(spec.name))
            else -> throw UnsupportedOperationException()
        }
}