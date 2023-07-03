package daythree

import util.ISolution
import kotlin.math.pow

enum class BitRatingType {
    CO2_SCRUBBER {
        override fun getMostValuedBit(): Char {
            return '0'
        }
        override fun getLeastValuedBit(): Char {
            return '1'
        }
    },
    OXYGEN_GENERATOR {
        override fun getMostValuedBit(): Char {
            return '1'
        }

        override fun getLeastValuedBit(): Char {
            return '0'
        }
    };

    abstract fun getMostValuedBit(): Char
    abstract fun getLeastValuedBit(): Char
}

class SolutionDayThree: ISolution {
    override val filePath = "src/main/kotlin/daythree/input.txt"

    fun getNumberFromBinaryArray(binaryArray: List<Int>): Int {
        var ans = 0
        var exponent: Int = binaryArray.size - 1
        val BASE = 2.00
        for (number in binaryArray) {
            ans += ((BASE.pow(exponent)).toInt())*number
            exponent -= 1
        }
        return ans
    }

    override fun solvePartOne(): Int {
        val fileLines: List<String> = this.readFile()
        val gammaRateArray = this.getCountArray(fileLines).map { if (it > 0)  1 else 0 }
        val epsilonRateArray = this.getCountArray(fileLines).map { if(it > 0) 0 else 1 }
        val gammaRate = this.getNumberFromBinaryArray(gammaRateArray)
        val epsilonRate = this.getNumberFromBinaryArray(epsilonRateArray)
        return gammaRate*epsilonRate
    }

    fun getCountArray(readingsArray: List<String>): List<Int> {
        val counterArray = IntArray(readingsArray[0].length) {0}
        for (bitReading in readingsArray) {
            val n = bitReading.length
            for (i in 0 until n) {
                when (bitReading[i]) {
                    '0' -> counterArray[i] -= 1
                    '1' -> counterArray[i] += 1
                }
            }
        }
        return counterArray.toList()
    }

    fun getRating(startingIndex: Int, readingsArray: List<String>, bitLength: Int, type: BitRatingType): List<String> {
        if (startingIndex > bitLength || readingsArray.size == 1) {
            return readingsArray
        }
        var newValidList = listOf<String>()
        val counterArray = this.getCountArray(readingsArray)
        val largestValue = if(counterArray[startingIndex] >= 0) type.getMostValuedBit() else type.getLeastValuedBit()
        for (array in readingsArray) {
            if (array[startingIndex] == largestValue) {
                newValidList += array
            }
        }
        val newStartingIndex = startingIndex + 1
        return getRating(newStartingIndex, newValidList, bitLength, type)
    }

    override fun solvePartTwo(): Int {
        val fileLines: List<String> = this.readFile()
        val n: Int = fileLines[0].length
        val oxygenGeneratorRatingBitArray = this.getRating(0, fileLines, n, BitRatingType.OXYGEN_GENERATOR)[0].toCharArray().map { it.toString().toInt() }
        val co2ScrubberRatingBitArray = this.getRating(0, fileLines, n, BitRatingType.CO2_SCRUBBER)[0].toCharArray().map { it.toString().toInt() }
        val oxygenGeneratorRating = this.getNumberFromBinaryArray(oxygenGeneratorRatingBitArray)
        val co2ScrubberRating = this.getNumberFromBinaryArray(co2ScrubberRatingBitArray)
        return co2ScrubberRating*oxygenGeneratorRating
    }
}