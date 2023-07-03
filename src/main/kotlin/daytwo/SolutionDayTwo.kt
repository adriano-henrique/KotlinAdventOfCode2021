package daytwo

import util.ISolution
import kotlin.math.abs

class SolutionDayTwo: ISolution {
    override val filePath: String = "src/main/kotlin/daytwo/input.txt"
    override val day: String = "Day Two"

    override fun solvePartOne(): Int {
        val fileLines = this.readFile()
        var positionX = 0
        var positionY = 0
        for (line in fileLines) {
            val splitLine = line.split(" ")
            val value = splitLine[1].toInt()
            val operationStr: String = splitLine[0]
            val operation: DayTwoSubmarineOperation? = DayTwoSubmarineOperation.fromString(operationStr)
            if (operation == null) {
                return 0
            }
            val resultOfOperation = operation.performOperation(positionX, positionY, value)
            positionX = resultOfOperation[0]
            positionY = resultOfOperation[1]
        }
        return abs(positionX*positionY)
    }

    override fun solvePartTwo(): Int {
        val fileLines = this.readFile()
        var horizontal = 0
        var depth = 0
        var aim = 0
        for (line in fileLines) {
            val splitLine = line.split(" ")
            val value = splitLine[1].toInt()
            val operationStr: String = splitLine[0]
            val operation: DayTwoSubmarineOperation? = DayTwoSubmarineOperation.fromString(operationStr)
            if (operation == null) {
                return 0
            }
            val resultOfOperation = operation.performOperationPartTwo(horizontal, depth, aim, value)
            horizontal = resultOfOperation[0]
            depth = resultOfOperation[1]
            aim = resultOfOperation[2]
        }
        return abs(horizontal*depth)
    }
}