package dayeight

import util.ISolution

class SolutionDayEight: ISolution {
    override val filePath: String = "src/main/kotlin/dayeight/input.txt"
    override val day: String = "Day Eight"

    override fun solvePartOne(): Int {
        val fileLines = this.readFile()
        val outputArray = mutableListOf<List<String>>()
        val signalPatternArray = mutableListOf<List<String>>()
        for(line in fileLines) {
            val splitLine = line.split("|").filter { it.isNotEmpty() }
            signalPatternArray.add(splitLine[0].split(" ").filter { it.isNotEmpty() })
            outputArray.add(splitLine[1].split(" ").filter { it.isNotEmpty() })
        }
        val uniqueSegments = listOf(2,3,4,7)
        var count = 0
        for (outputNumbers in outputArray) {
            for (output in outputNumbers) {
                if (uniqueSegments.contains(output.length)) {
                    count += 1
                }
            }
        }
        return count
    }

    override fun solvePartTwo(): Int {
        val fileLines = this.readFile()
        val outputArray = mutableListOf<List<String>>()
        val signalPatternArray = mutableListOf<List<String>>()
        for(line in fileLines) {
            val splitLine = line.split("|").filter { it.isNotEmpty() }
            signalPatternArray.add(splitLine[0].split(" ").filter { it.isNotEmpty() })
            outputArray.add(splitLine[1].split(" ").filter { it.isNotEmpty() })
        }
        var count = 0
        for (i in 0 until outputArray.size) {
            val letterNumberMap = determineNumbersMap(signalPatternArray[i], outputArray[i])
            println(letterNumberMap)
            count += determineOutputArrayValue(outputArray[i], letterNumberMap)
        }
        return count
    }

    private fun determineOutputArrayValue(outputArray: List<String>, letterNumberMap: MutableMap<Int, List<String>>): Int {
        val numberList = mutableListOf(0,0,0,0)
        for ((index, output) in outputArray.withIndex()) {
            val letters = output.map { it.toString() }.sorted()
            numberList[index]  = letterNumberMap.filterValues { it == letters }.keys.toIntArray()[0]
        }
        return numberList.joinToString("").toInt()
    }

    private fun determineNumbersMap(signalPatternArray: List<String>, outputArray: List<String>): MutableMap<Int, List<String>> {
        val combinedList = signalPatternArray + outputArray
        val letterNumberMap = mutableMapOf<Int, List<String>>()
        mountInitialLettersMap(combinedList, letterNumberMap)
        mountSecondaryLettersMap(combinedList, letterNumberMap)
        return letterNumberMap
    }

    // Create the initial, easy to identify numbers - 1 4 7 8
    private fun mountInitialLettersMap(combinedList: List<String>, letterNumberMap: MutableMap<Int, List<String>>) {
        for (element in combinedList) {
            val letters = element.map { it.toString() }.sorted()
            when(element.length) {
                2 -> {
                    letterNumberMap[1] = letters
                }
                3 -> {
                    letterNumberMap[7] = letters
                }
                4 -> {
                    letterNumberMap[4] = letters
                }
                7 -> {
                    letterNumberMap[8] = letters
                }
            }
        }
    }

    // From the initial numbers (1 4 7 8) it is easy to identify 0 and 9
    // 9 -> contains all letters from 4
    // 0 -> contains all letters from 7 (not from 4)
    private fun mountSecondaryLettersMap(combinedList: List<String>, letterNumberMap: MutableMap<Int, List<String>>) {
        for (element in combinedList) {
            val letters = element.map { it.toString() }.sorted()
            if (isThree(letters, letterNumberMap)) {
                letterNumberMap[3] = letters
            }

            if (isNine(letters, letterNumberMap)) {
                letterNumberMap[9] = letters
            }

            if (isZero(letters, letterNumberMap)) {
                letterNumberMap[0] = letters
            }

            if (isSix(letters, letterNumberMap)) {
                letterNumberMap[6] = letters
            }

            if (isFive(letters, letterNumberMap)) {
                letterNumberMap[5] = letters
            }

            if (isTwo(letters, letterNumberMap)) {
                letterNumberMap[2] = letters
            }
        }
    }

    private fun isThree(letters: List<String>, letterNumberMap: MutableMap<Int, List<String>>): Boolean {
        if (letters.size != 5) {
            return false
        }

        if (letters overlaps letterNumberMap[7] == true) {
            return true
        }
        return false
    }

    private fun isNine(letters: List<String>, letterNumberMap: MutableMap<Int, List<String>>): Boolean {
        if (letters.size ==6 && letters overlaps letterNumberMap[3] == true) {
            return true
        }
        return false
    }

    private fun isZero(letters: List<String>, letterNumberMap: MutableMap<Int, List<String>>): Boolean {
        if (letters.size == 6 && !isNine(letters, letterNumberMap) && letters overlaps letterNumberMap[7] == true) {
            return true
        }
        return false
    }

    private fun isSix(letters: List<String>, letterNumberMap: MutableMap<Int, List<String>>): Boolean {
        if (letters.size == 6 && !isNine(letters, letterNumberMap) && !isZero(letters, letterNumberMap)) {
            return true
        }
        return false
    }

    private fun isFive(letters: List<String>, letterNumberMap: MutableMap<Int, List<String>>): Boolean {
        if (letters.size == 5 && letterNumberMap[6]?.overlaps(letters) == true) {
            return true
        }
        return false
    }

    private fun isTwo(letters: List<String>, letterNumberMap: MutableMap<Int, List<String>>): Boolean {
        if (letters.size == 5 && !isFive(letters, letterNumberMap) && !isThree(letters, letterNumberMap)) {
            return true
        }
        return false
    }

    private infix fun List<String>.overlaps(that: List<String>?): Boolean? =
        that?.let { this.containsAll(it) }
}
