import dayfour.SolutionDayFour
import dayone.SolutionDayOne
import daythree.SolutionDayThree
import daytwo.SolutionDayTwo

fun main() {
    val solutionDayOne = SolutionDayOne()
    val solutionDayTwo = SolutionDayTwo()
    val solutionDayThree = SolutionDayThree()
    val solutionDayFour = SolutionDayFour()
    println(solutionDayOne.solution())
    println(solutionDayTwo.solution())
    println(solutionDayThree.solution())
    println(solutionDayFour.solution())
}