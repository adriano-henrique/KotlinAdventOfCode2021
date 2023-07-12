package dayeight

import util.ISolution

class SolutionDayEight: ISolution {
    override val filePath: String = "src/main/kotlin/dayeight/input.txt"
    override val day: String = "Day Eight"

    override fun solvePartOne(): Int {
        val fileLines = this.readFile()
        var outputArray = mutableListOf<List<String>>()
        var signalPatternArray = mutableListOf<List<String>>()
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
        return 0
    }
}
