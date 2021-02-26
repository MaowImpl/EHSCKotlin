package maow.ehsckotlin.converter

import maow.ehsckotlin.handler.DocumentHandler
import org.dom4j.Document
import org.dom4j.io.OutputFormat
import org.dom4j.io.XMLWriter
import java.io.FileWriter

abstract class Converter(private var document: Document) {
    fun write() {
        convert()
        write(document)
    }

    private fun write(document: Document) {
        var xw: XMLWriter? = null
        FileWriter("Output.xml").use {
            xw = XMLWriter(it, OutputFormat.createPrettyPrint())
            xw!!.write(document)
            xw!!.flush()
        }
        xw?.close()
    }

    protected abstract fun convert()

    protected fun doc(lambda: DocumentHandler.() -> Unit) {
        val handler = DocumentHandler(document)
        lambda.invoke(handler)
        document = handler.new
    }
}