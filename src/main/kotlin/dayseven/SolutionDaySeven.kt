package dayseven

import util.ISolution
import kotlin.math.abs

class SolutionDaySeven: ISolution {
    override val filePath: String = "src/main/kotlin/dayseven/input.txt"
    override val day: String = "Day Seven"

    override fun solvePartOne(): Int {
        val fileLines = this.readFile()
        var amountValueMap = mutableMapOf<Int, Int>()
        val crabPositions = fileLines[0].split(",").map{ it.toInt() }
        this.mountMap(amountValueMap, crabPositions)
        val minCost = amountValueMap.keys.getCrabRange().minOf { crabPosition ->
            amountValueMap.map { (crab, crabCount) -> abs(crabPosition - crab)*crabCount }.sum()
         }
        return minCost
    }

    override fun solvePartTwo(): Int {
        val fileLines = this.readFile()
        var amountValueMap = mutableMapOf<Int, Int>()
        val crabPositions = fileLines[0].split(",").map{ it.toInt() }
        this.mountMap(amountValueMap, crabPositions)
        val minCost = amountValueMap.keys.getCrabRange().minOf { crabPosition ->
            amountValueMap.map {(crab, crabCount) -> (abs((crabPosition - crab))*(abs(crabPosition - crab) + 1)/2)*crabCount}.sum()
        }
        return minCost
    }

    private fun MutableSet<Int>.getCrabRange(): IntRange =
        this.minOf { it }..this.maxOf { it }


    private fun mountMap(amountValueMap: MutableMap<Int, Int>, crabPositions: List<Int>) {
        for (crabPosition in crabPositions) {
            if (amountValueMap.containsKey(crabPosition)) {
                val currValue = amountValueMap[crabPosition]
                if (currValue != null) {
                    amountValueMap[crabPosition] = currValue + 1
                } else {
                    println("Key not found in map")
                }
            } else {
                amountValueMap[crabPosition] = 1
            }
        }
    }
}