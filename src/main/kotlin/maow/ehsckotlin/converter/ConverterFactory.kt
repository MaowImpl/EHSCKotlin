package maow.ehsckotlin.converter

import maow.ehsckotlin.log
import org.dom4j.Document

object ConverterFactory {
    fun convert(document: Document) {
        val name = document.rootElement?.name
        if (name != null) {
            log("Detected a(n) $name document")
            get(name, document)?.write()
        }
    }

    private fun get(name: String, document: Document) : Converter? {
        return when (name.toLowerCase()) {
            "extension" -> ExtensionConverter(document)
            else -> null
        }
    }
}