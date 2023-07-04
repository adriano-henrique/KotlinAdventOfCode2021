package dayfour

import util.ISolution

class SolutionDayFour: ISolution {
    override val filePath = "src/main/kotlin/dayfour/input.txt"
    override val day = "Day Four"

    override fun solvePartOne(): Int {
        val fileLines: List<String> = this.readFile()
        val selectedNumbers: List<Int> = fileLines[0].split(",").map { it.toInt() }
        val boards = this.mountBoard(fileLines)
        var sumNonSelected = 0
        var winnerNumber = 0
        for (number in selectedNumbers) {
            for (board in boards) {
                modifyBoard(number, board)
            }
            val winnerBoard = this.winner(boards)
            if (winnerBoard[0].isNotEmpty()) {
                winnerNumber = number
                sumNonSelected = this.getSumNonSelected(winnerBoard)
                break
            }
        }
        return winnerNumber*sumNonSelected
    }

    override fun solvePartTwo(): Int {
        return 0
    }

    private fun getSumNonSelected(board: BingoBoardType): Int {
        var sum = 0
        for (rowIndex in board.indices) {
            for (columnIndex in board[rowIndex].indices) {
                if (board[rowIndex][columnIndex] != -1) {
                    sum += board[rowIndex][columnIndex]
                }
            }
        }
        return sum
    }
    private fun winner(boards: MutableList<BingoBoardType>): BingoBoardType {
        for (board in boards) {
            if (this.columnWinner(board) || this.lineWinner(board)) {
                return board
            }
        }
        return mutableListOf(mutableListOf())
    }

    private fun lineWinner(board: BingoBoardType): Boolean {
        for (line in board) {
            if (line.all { it == -1 }) {
                return true
            }
        }
        return false
    }

    private fun columnWinner(board: BingoBoardType): Boolean {
        for (columnIndex in board[0].indices){
            if (board[columnIndex].all { it == -1 }) {
                return true
            }
        }
        return false
    }

    private fun modifyBoard(number: Int, board: BingoBoardType): Unit {
        for (i in board.indices) {
            for (j in board[i].indices) {
                if (board[i][j] == number) {
                    board[i][j] = -1
                }
            }
        }
    }

    private fun parseBoardLine(boardLine: String): MutableList<Int> {
        val delimiters = "\\s+".toRegex()
        return boardLine.split(delimiters).filter { it.isNotBlank() }.map { it.toInt() }.toMutableList()
    }

    private fun mountBoard(fileLines: List<String>): MutableList<BingoBoardType> {
        val boards: MutableList<BingoBoardType> = mutableListOf()
        var newBoard: BingoBoardType = mutableListOf()
        for (i in 1 until fileLines.size) {
            if (fileLines[i] == "") {
                if (newBoard.isNotEmpty()) {
                    boards.add(newBoard)
                }
                newBoard = mutableListOf()
            } else {
                val newLine = this.parseBoardLine(fileLines[i])
                newBoard.add(newLine)
            }
        }
        boards.add(newBoard)
        return boards
    }
}

typealias BingoBoardType = MutableList<MutableList<Int>>