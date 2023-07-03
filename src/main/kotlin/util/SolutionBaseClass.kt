package util

import java.io.File

interface ISolution {
    val filePath: String

    fun readFile(): List<String> {
        return File(this.filePath).readLines()
    }

    fun solvePartOne(): Int
    fun solvePartTwo(): Int
}