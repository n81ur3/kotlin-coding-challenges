package utils

import java.io.File

object ResourceLoader {

    fun getFile(fileName: String): File {
        return File(javaClass.classLoader.getResource(fileName).toURI())
    }
}