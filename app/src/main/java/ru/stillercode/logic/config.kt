package ru.stillercode.logic

val BOARD_SIZE = XY(10, 20)
val PIECE_MAX_DIM = XY(5, 5)

val UP = XY(0, -1)
val DOWN = XY(0, 1)
val LEFT = XY(-1, 0)
val RIGHT = XY(1, 0)

val START_POINT = XY(BOARD_SIZE.x / 2, 0)

private val classicShapeSchemes = listOf(
    listOf(
        listOf(
            "_____",
            "__AA_",
            "__AA_",
            "_____",
            "_____",
        )
    )
)

private fun shapeSchemesToSets(shapeSchemes: List<List<List<String>>>): List<List<Set<XY>>> {
    val out = mutableListOf<MutableList<MutableSet<XY>>>()
    for (shapeSchemeList in shapeSchemes) {
        val shape = mutableListOf<MutableSet<XY>>()
        for (shapeScheme in shapeSchemeList) {
            val subshape = mutableSetOf<XY>()
            for (y in 0 until PIECE_MAX_DIM.y) {
                for (x in 0 until PIECE_MAX_DIM.x) {
                    if (shapeScheme[y][x] == 'A') {
                        subshape.add(XY(x, y))
                    }
                }
            }
            shape.add(subshape)
        }
        out.add(shape)
    }
    return out.map{list -> list.map{it.toSet()}}
}
val CLASSIC_SHAPES = shapeSchemesToSets(classicShapeSchemes)