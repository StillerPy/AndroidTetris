package ru.stillercode.logic

val BOARD_SIZE = XY(10, 20)
val PIECE_MAX_DIM = XY(4, 4)

val UP = XY(0, -1)
val DOWN = XY(0, 1)
val LEFT = XY(-1, 0)
val RIGHT = XY(1, 0)

val START_POINT = XY(BOARD_SIZE.x / 2, 0)
val ROTATING_POINT = XY(1, 1)

private val classicShapeSchemes = listOf(
    listOf(
        "AA",
        "AA",
    ),
    listOf(
        "A",
        "A",
        "A",
        "A",
    ),
    listOf(
        "AA",
        "A_",
        "A_",
    ),
    listOf(
        "AA",
        "_A",
        "_A",
    ),
    listOf(
        "A_",
        "AA",
        "_A",
    ),
    listOf(
        "_A",
        "AA",
        "_A",
    ),
    listOf(
        "_A",
        "AA",
        "A_",
    ),
    )

private fun schemeToShape(scheme: List<String>): Set<XY> {
    val out = mutableSetOf<XY>()
    for (y in 0 until scheme.size) {
        for (x in 0 until scheme[y].length) {
            if (scheme[y][x] == 'A') {
                out.add(XY(x, y))
            }
        }
    }
    return out.toSet()
}

private fun shapeSchemesToSets(shapeSchemes: List<List<String>>): List<Set<XY>> {
    return classicShapeSchemes.map{ schemeToShape(it) }
}

val CLASSIC_SHAPES = shapeSchemesToSets(classicShapeSchemes)