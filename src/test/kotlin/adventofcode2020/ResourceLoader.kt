package adventofcode2020

import java.io.File

object ResourceLoader {

    fun getFile(fileName: String): File {
        return File(javaClass.classLoader.getResource(fileName).toURI())
    }
}