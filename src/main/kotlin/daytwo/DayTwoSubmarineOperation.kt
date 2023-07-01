package daytwo

enum class DayTwoSubmarineOperation {
    FORWARD {
        override fun performOperation(positionX: Int, positionY: Int, value: Int): Array<Int> {
            return arrayOf(positionX + value, positionY)
        }

        override fun performOperationPartTwo(horizontal: Int, depth: Int, aim: Int, value: Int): Array<Int> {
            return arrayOf(horizontal + value, depth + (aim*value), aim)
        }
    },
    UP {
        override fun performOperation(positionX: Int, positionY: Int, value: Int): Array<Int> {
            return arrayOf(positionX, positionY + value)
        }

        override fun performOperationPartTwo(horizontal: Int, depth: Int, aim: Int, value: Int): Array<Int> {
            return arrayOf(horizontal, depth, aim - value)
        }
    },
    DOWN {
        override fun performOperation(positionX: Int, positionY: Int, value: Int): Array<Int> {
            return arrayOf(positionX, positionY - value)
        }

        override fun performOperationPartTwo(horizontal: Int, depth: Int, aim: Int, value: Int): Array<Int> {
            return arrayOf(horizontal, depth, aim + value)
        }
    };
    abstract fun performOperation(positionX: Int, positionY: Int, value: Int): Array<Int>
    abstract fun performOperationPartTwo(horizontal: Int, depth: Int, aim: Int, value: Int): Array<Int>

    companion object {
        fun fromString(value: String): DayTwoSubmarineOperation? {
            return when(value.lowercase()) {
                "forward" -> FORWARD
                "up" -> UP
                "down" -> DOWN
                else -> null
            }
        }
    }
}