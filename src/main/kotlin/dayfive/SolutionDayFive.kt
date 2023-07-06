package dayfive

import util.ISolution

class SolutionDayFive: ISolution {
    override val filePath: String = "src/main/kotlin/dayfive/input.txt"
    override val day: String = "Day Five"

    override fun solvePartOne(): Int {
        val fileLines = this.readFile()
        val operations: CoordinatesOperationType = mutableListOf()
        this.createCoordinateOperationsList(fileLines, operations)
        val sizeOfDiagram = operations.flatten().flatten().maxOrNull() ?: return -1
        val diagram: DiagramType = List(sizeOfDiagram + 1) { MutableList(sizeOfDiagram + 1) { 0 } }
        var countTwoOverlap = 0
        for (operation in operations) {
            val fromCoordinate: List<Int> = operation[0]
            val toCoordinate: List<Int> = operation[1]
            val isUnidirectional = fromCoordinate.zip(toCoordinate).any { (x,y) -> x == y }
            if (isUnidirectional) {
                countTwoOverlap += this.modifyDiagramUnidirectional(diagram, fromCoordinate, toCoordinate)
            }
        }

        return countTwoOverlap
    }

    override fun solvePartTwo(): Int {
        val fileLines = this.readFile()
        val operations: CoordinatesOperationType = mutableListOf()
        this.createCoordinateOperationsList(fileLines, operations)
        val sizeOfDiagram = operations.flatten().flatten().maxOrNull() ?: return -1
        val diagram: DiagramType = List(sizeOfDiagram + 1) { MutableList(sizeOfDiagram + 1) { 0 } }
        var countTwoOverlap = 0
        for (operation in operations) {
            val fromCoordinate: List<Int> = operation[0]
            val toCoordinate: List<Int> = operation[1]
            countTwoOverlap += this.modifyDiagramDiagonal(diagram, fromCoordinate, toCoordinate)
        }
        return countTwoOverlap
    }

    private fun modifyDiagramDiagonal(diagram: DiagramType, fromCoordinate: List<Int>, toCoordinate: List<Int>): Int {
        val iFromIndex = fromCoordinate[0]
        val jFromIndex = fromCoordinate[1]
        val iToIndex = toCoordinate[0]
        val jToIndex = toCoordinate[1]
        var valueToAdd = 0
        var dx = if (iFromIndex < iToIndex) 1 else -1
        var dy = if (jFromIndex < jToIndex) 1 else -1

        if (iFromIndex == iToIndex) {
            dx = 0
        }
        if (jFromIndex == jToIndex) {
            dy = 0
        }

        var i = iFromIndex
        var j = jFromIndex
        diagram[i][j] += 1
        while (i != iToIndex || j != jToIndex) {
            if (diagram[i][j] == 2) {
                valueToAdd += 1
            }
            i += dx
            j += dy
            diagram[i][j] += 1
        }

        return valueToAdd
    }

    private fun modifyDiagramUnidirectional(diagram: DiagramType, fromCoordinate: List<Int>, toCoordinate: List<Int>): Int {
        if (fromCoordinate[0] == toCoordinate[0]) {
            // Modify the line
            val jStart = minOf(fromCoordinate[1], toCoordinate[1])
            val jEnd = maxOf(fromCoordinate[1], toCoordinate[1])
            return this.modifyDiagramLine(diagram, jStart, jEnd, fromCoordinate[0])
        }
        // Modify the column
        val iStart = minOf(fromCoordinate[0], toCoordinate[0])
        val iEnd = maxOf(fromCoordinate[0], toCoordinate[0])
        return this.modifyDiagramColumn(diagram, iStart, iEnd, fromCoordinate[1])
    }

    private fun modifyDiagramColumn(diagram: DiagramType, iStartIndex: Int, iEndIndex: Int, j: Int): Int {
        var valueToAdd = 0
        for (i in iStartIndex..iEndIndex) {
            diagram[i][j] += 1
            if (diagram[i][j] == 2) {
                valueToAdd += 1
            }
        }
        return valueToAdd
    }

    private fun modifyDiagramLine(diagram: DiagramType, jStartIndex: Int, jEndIndex: Int, i: Int): Int {
        var valueToAdd = 0
        for (j in jStartIndex..jEndIndex) {
            diagram[i][j] += 1
            if (diagram[i][j] == 2) {
                valueToAdd += 1
            }
        }
        return valueToAdd
    }

    private fun createCoordinateOperationsList(fileLines: List<String>, operations: CoordinatesOperationType): Unit {
        for (line in fileLines) {
            val operation = line.split("->").map { it.replace(" ", "").split(",") }
            val coordinateOperationInt = operation.map { it -> it.map { it.toInt() } }
            operations.add(coordinateOperationInt)
        }
    }
}

typealias CoordinatesOperationType = MutableList<List<List<Int>>>
typealias DiagramType = List<MutableList<Int>>