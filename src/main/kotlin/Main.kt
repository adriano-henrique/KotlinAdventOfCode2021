import dayone.SolutionDayOne
import daythree.SolutionDayThree
import daytwo.SolutionDayTwo

fun main() {
    val solutionDayOne = SolutionDayOne()
    val solutionDayTwo = SolutionDayTwo()
    val solutionDayThree = SolutionDayThree()
    println(solutionDayOne.solvePartOne())
    println(solutionDayOne.solvePartTwo())
    println(solutionDayTwo.solvePartOne())
    println(solutionDayTwo.solvePartTwo())
    println(solutionDayThree.solvePartOne())
    println(solutionDayThree.solvePartTwo())
}