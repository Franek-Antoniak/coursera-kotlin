package board

open class SquareBoardImpl(override val width: Int) : SquareBoard {
    private val map: MutableMap<Pair<Int, Int>, Cell> = run {
        val map = mutableMapOf<Pair<Int, Int>, Cell>()
        for (i in 1..width) {
            for (j in 1..width) {
                map[i to j] = Cell(i, j)
            }
        }
        map
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return if (i in 1..width && j in 1..width) {
            map[i to j]
        } else null
    }

    override fun getCell(i: Int, j: Int): Cell {
        return map[i to j] ?: throw IllegalArgumentException()
    }

    override fun getAllCells(): Collection<Cell> {
        return map.values
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        return jRange.takeWhile { j -> j in 1..width }.map { j -> getCell(i, j) }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return iRange.takeWhile { i -> i in 1..width }.map { i -> getCell(i, j) }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            Direction.UP -> getCellOrNull(i - 1, j)
            Direction.DOWN -> getCellOrNull(i + 1, j)
            Direction.RIGHT -> getCellOrNull(i, j + 1)
            Direction.LEFT -> getCellOrNull(i, j - 1)
        }
    }
}

class GameBoardImpl<T>(width: Int) : SquareBoardImpl(width), GameBoard<T> {
    private val mapValues: MutableMap<Cell, T?> = getAllCells().associateWith { null }.toMutableMap()

    override fun get(cell: Cell): T? {
        return mapValues[cell]
    }

    override fun set(cell: Cell, value: T?) {
        mapValues[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return mapValues.values.filter(predicate).map {
            value -> mapValues.filterValues { it == value}.keys
        }.flatten()
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return mapValues.filterValues(predicate).keys.firstOrNull()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return mapValues.values.any(predicate)
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return mapValues.values.all(predicate)
    }
}

