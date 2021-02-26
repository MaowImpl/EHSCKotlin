package maow.ehsckotlin.handler

import org.dom4j.*

class DocumentHandler(private val document: Document? = null) {
    val new: Document = DocumentHelper.createDocument()

    fun root(name: String? = null, lambda: ElementHandler.() -> Unit) {
        val root = document?.rootElement
        if (root != null) {
            val handler = ElementHandler(root, name ?: root.name)
            lambda.invoke(handler)
            new.rootElement = handler.new
        }
    }
}