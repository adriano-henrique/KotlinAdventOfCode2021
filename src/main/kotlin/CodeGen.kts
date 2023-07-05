import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

readInputAndGenerateCode()

fun formatDay(dayInput: String): String {
    val splitDayInput = StringBuilder(dayInput).insert(3, " ").toString()
    return splitDayInput.split(" ")
        .joinToString(" ") { it -> it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } }
}

fun genCode(aocDayInput: String, testInputLines: List<String>, inputLines: List<String>) {
    println("Generating important files... ")
    val mainDirPath = Paths.get("")
    val packageDirPath = mainDirPath.resolve(aocDayInput.replace(".", "/"))

    if (!Files.exists(packageDirPath)) {
        Files.createDirectories(packageDirPath)
        println("Package '$aocDayInput' created successfully.")
    } else {
        println("Package '$aocDayInput' already exists.")
    }
    val solutionFileName = "Solution${this.formatDay(aocDayInput).replace(" ", "")}"
    val inputFilePath = packageDirPath.resolve("input.txt")
    val testInputFilePath = packageDirPath.resolve("test_input.txt")
    val solutionFilePath = packageDirPath.resolve("$solutionFileName.kt")

    createEmptyFile(inputFilePath)
    createEmptyFile(testInputFilePath)
    createEmptyFile(solutionFilePath)

    println("Writing content in files...")
    writeToFile(testInputFilePath, testInputLines.joinToString("\n"))
    writeToFile(inputFilePath, inputLines.joinToString("\n"))
    writeToFile(solutionFilePath, genSolutionFileContent(aocDayInput))

    println("Files created successfully.")
}

fun createEmptyFile(filePath: Path) {
    if (!Files.exists(filePath)) {
        Files.createFile(filePath)
        println("File '${filePath.fileName}' created successfully.")
    } else {
        println("File '${filePath.fileName}' already exists.")
    }
}

fun writeToFile(filePath: Path, content: String) {
    val writer = Files.newBufferedWriter(filePath)
    writer.write(content)
    writer.close()
    println("Content written to file '${filePath.fileName}' successfully.")
}

fun readInputAndGenerateCode() {
    println("Add the package name for the Advent of Code solution you're working on (ex: dayone, daytwo,...)?")
    val aocDay = readLine() ?: throw IllegalArgumentException("Input cannot be null")

    println("Enter test input from the problem you're solving:")

    val testInputLines: List<String> = generateSequence { readLine() }
        .takeWhile { it.isNotBlank() }
        .toList()

    println("Enter the input for the problem you're trying to solve: ")

    val inputLines: List<String> = generateSequence { readLine() }
        .takeWhile { it.isNotBlank() }
        .toList()

    genCode(aocDay, testInputLines, inputLines)
}

fun genSolutionFileContent(aocDayInput: String): String {
    val folder = "src/main/kotlin/${aocDayInput}/input.txt"
    val day = this.formatDay(aocDayInput)
    val className = day.replace(" ", "")
    return """
package $aocDayInput

import util.ISolution

class Solution${className}: ISolution {
    override val filePath: String = "$folder"
    override val day: String = "$day"

    override fun solvePartOne(): Int {
        return 0
    }

    override fun solvePartTwo(): Int {
        return 0
    }
}
    """.trimIndent()
}