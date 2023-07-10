package daysix

import util.ILongSolution

class SolutionDaySix: ILongSolution {
    override val filePath: String = "src/main/kotlin/daysix/input.txt"
    override val day: String = "Day Six"

    override fun solvePartOne(): Long {
        return simulateDays(80, createInitialFishPerDay())
    }

    override fun solvePartTwo(): Long {
        return simulateDays(256, createInitialFishPerDay())
    }

    private fun modifyFishList(fishPerDay: MutableList<Long>) {
        val firstElement = fishPerDay[0]
        for (i in 1 until fishPerDay.size) {
            fishPerDay[i - 1] = fishPerDay[i]
        }
        fishPerDay[fishPerDay.size - 1] = firstElement
        fishPerDay[6] += firstElement
    }

    private fun simulateDays(days: Int, fishPerDay: MutableList<Long>): Long {
        var dayCount = 0
        while (dayCount < days) {
            modifyFishList(fishPerDay)
            dayCount += 1
        }
        return fishPerDay.sum()
    }

    private fun createInitialFishPerDay(): MutableList<Long> {
        val fileLines = this.readFile()
        val initialFishList = fileLines[0].split(",").map { it.toInt() }
        val initialFishPerDay = MutableList<Long>(9) {0}
        for (fish in initialFishList) {
            initialFishPerDay[fish] = initialFishPerDay[fish] + 1
        }
        return initialFishPerDay
    }
}
