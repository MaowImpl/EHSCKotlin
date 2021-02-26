package maow.ehsckotlin

import maow.ehsckotlin.converter.ConverterFactory
import org.dom4j.io.SAXReader
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

fun log(message: String) = println("[EHSC] $message")

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        val path = Paths.get(args[0])
        if (Files.notExists(path))
            throw FileNotFoundException("Target file does not exist.")
        log("Target file: $path")
        convert(path)
    }
}

private fun convert(path: Path) {
    log("Attempting file conversion")

    val reader = SAXReader()
    val doc = reader.read(path.toFile())
    if (doc != null)
        ConverterFactory.convert(doc)
}