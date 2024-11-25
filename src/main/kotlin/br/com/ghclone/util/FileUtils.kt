package br.com.ghclone.util

import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.absolute
import kotlin.io.path.absolutePathString
import kotlin.io.path.name


fun String.toPath(): Path {
    return Path(this).normalize()

}

fun String.toPath(vararg subPaths: String): Path {
    return Path(this, *subPaths).normalize()
}

