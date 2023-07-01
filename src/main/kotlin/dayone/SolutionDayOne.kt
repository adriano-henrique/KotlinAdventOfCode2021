package dayone

import java.io.File

class SolutionDayOne {
    fun readFile(): List<String> {
        val fileName = "src/main/kotlin/dayone/input.txt"
        return File(fileName).readLines()
    }

    fun countIncrease(list: List<Int>): Int {
        val n = list.size - 1
        var count = 0
        var prevValue = list[0]
        for (i in 1..n) {
            val currValue = list[i]
            if (prevValue < currValue) {
                count += 1
            }
            prevValue = currValue
        }
        return count
    }

    fun solvePartOne(): Int {
        val fileLinesInt: List<Int> = this.readFile().map { it.toInt() }
        return this.countIncrease(fileLinesInt)
    }

    fun solvePartTwo(): Int {
        val fileLinesInt: List<Int> = this.readFile().map { it.toInt() }
        val n = fileLinesInt.size - 1
        val threeStepArray: MutableList<Int> = mutableListOf()
        var i = 0
        var j = 2
        var currSum = 0
        for (k in i..j) {
            currSum += fileLinesInt[k]
        }
        threeStepArray += currSum
        while (j < n) {
            j += 1
            currSum = currSum + fileLinesInt[j] - fileLinesInt[i]
            i += 1
            threeStepArray += currSum
        }

        return this.countIncrease(threeStepArray.toList())
    }
}