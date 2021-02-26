package maow.ehsckotlin.handler

import org.dom4j.Attribute
import org.dom4j.Element
import org.dom4j.Node

class ConvertHandler(private val node: Node, private val parent: ElementHandler) {
    fun add(vararg nodes: ConvertNode) {
        nodes.forEach {
            when (it.type) {
                "element" -> addElement(it)
                "attribute" -> addAttribute(it)
                else -> throw UnsupportedOperationException()
            }
        }
    }

    private fun addElement(node: ConvertNode) {
        val element = parent.new.addElement(node.name)
        if (node.value.isNotEmpty()) element.text = node.value
    }

    private fun addAttribute(node: ConvertNode) =
        parent.new.addAttribute(node.name, node.value)

    fun element(name: String, value: String = ""): ConvertNode =
        ConvertNode(node, "element", name, value)

    fun attribute(name: String): ConvertNode =
        ConvertNode(node, "attribute", name, node.text)

    fun name(): String = node.name

    fun value(): String = node.text

    operator fun contains(node: ConvertNode): Boolean {
        return when (node.type) {
            "element" -> parent.element.element(node.name) != null
            "attribute" -> parent.element.attribute(node.name) != null
            else -> throw UnsupportedOperationException()
        }
    }

    class ConvertNode(private val node: Node, val type: String, val name: String, val value: String) {
        infix fun convert(node: ConvertNode): ConvertNode {
            val value = (this.node as Element).attributeValue(name)
            return ConvertNode(this.node, node.type, node.name, value)
        }
    }
}