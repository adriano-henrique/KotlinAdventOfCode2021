import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

readInputAndGenerateCode()

fun formatDay(dayInput: String): String {
    val splitDayInput = StringBuilder(dayInput).insert(3, " ").toString()
    return splitDayInput.split(" ")
        .joinToString(" ") { it -> it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } }
}

fun genTestInputFile(packageDirPath: Path, testInputLines: List<String>) {
    val testInputFilePath = packageDirPath.resolve("test_input.txt")
    createEmptyFile(testInputFilePath)
    writeToFile(testInputFilePath, testInputLines.joinToString("\n"))
}

fun genInputFile(packageDirPath: Path, inputLines: List<String>) {
    val inputFilePath = packageDirPath.resolve("input.txt")
    createEmptyFile(inputFilePath)
    writeToFile(inputFilePath, inputLines.joinToString("\n"))
}

fun genSolutionFile(packageDirPath: Path, aocDayInput: String, solutionFileName: String) {
    val solutionFilePath = packageDirPath.resolve("$solutionFileName.kt")
    createEmptyFile(solutionFilePath)
    writeToFile(solutionFilePath, genSolutionFileContent(aocDayInput))
}

fun genModifyMainKotlin(mainDirPath: Path, solutionFileName: String, aocDayInput: String) {
    val variableName: String = solutionFileName.replaceFirstChar { it.lowercase() }
    val importClass = "import $aocDayInput.$solutionFileName"
    val solutionClassInstantiation = """
    val $variableName = $solutionFileName()
    println($variableName.solution())
        """
    val filePath = mainDirPath.resolve("Main.kt")
    val fileContent = Files.readString(filePath)
    val lastImportIndex = fileContent.lastIndexOf("import")
    val newlineIndex = fileContent.indexOf("\n", lastImportIndex)
    val importModifiedContent =
        StringBuilder(fileContent).insert(newlineIndex + 1, "$importClass\n").toString().trimIndent()
    val closingBracketsIndex = importModifiedContent.lastIndexOf("}")
    val fileModifiedContent =
        StringBuilder(importModifiedContent).insert(closingBracketsIndex, "$solutionClassInstantiation\n").toString()
    Files.writeString(filePath, fileModifiedContent, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)
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

    genTestInputFile(packageDirPath, testInputLines)
    genInputFile(packageDirPath, inputLines)
    genSolutionFile(packageDirPath, aocDayInput, solutionFileName)
    genModifyMainKotlin(mainDirPath, solutionFileName, aocDayInput)

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