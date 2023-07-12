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
        val availableLetters = listOf("a","b","c","d","e","f","g")
        val letterNumberMap = mutableMapOf<Int, List<String>>()
        for (outputStrings in outputArray) {
            for (output in outputStrings) {
                val outputLetters = output.map { it.toString() }
                when(output.length) {
                    2 -> {
                        if (!letterNumberMap.containsKey(1)) {
                            letterNumberMap[1] = outputLetters
                        }
                    }
                    3 -> {
                        if (!letterNumberMap.containsKey(7)) {
                            letterNumberMap[7] = outputLetters
                        }
                    }
                    4 -> {
                        if (!letterNumberMap.containsKey(4)) {
                            letterNumberMap[4] = outputLetters
                        }
                    }
                    7 -> {
                        if (!letterNumberMap.containsKey(8)) {
                            letterNumberMap[8] = outputLetters
                        }
                    }
                }
            }
        }
        println(letterNumberMap)
        return 0
    }

    private fun appendToMap(letterNumberMap: MutableMap<Int, MutableList<String>>, newLetter: String, key: Int) {
        if (letterNumberMap.containsKey(key)) {
            letterNumberMap[key]?.add(newLetter)
        } else {
            letterNumberMap[key] = mutableListOf(newLetter)
        }
    }
}
