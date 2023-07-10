package util

import java.io.File

interface ILongSolution {
    val filePath: String
    val day: String

    fun readFile(): List<String> {
        return File(this.filePath).readLines()
    }

    fun readablePartOne(): String {
        return if(this.solvePartOne() == Long.MIN_VALUE) "<NOT_IMPLEMENTED>"  else this.solvePartOne().toString()
    }

    fun readablePartTwo(): String {
        return if(this.solvePartTwo() == Long.MIN_VALUE) "<NOT_IMPLEMENTED>" else this.solvePartTwo().toString()
    }

    fun solution(): String {
        return "${day}\n==============\nSolution Part One: ${this.readablePartOne()} \nSolution Part Two: ${this.readablePartTwo()}\n=============="
    }

    fun solvePartOne(): Long
    fun solvePartTwo(): Long
}