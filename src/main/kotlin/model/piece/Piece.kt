package org.example.model.piece

import org.example.model.Board
import org.example.model.Color
import org.example.model.Move

abstract class Piece(
    val board: Board,
    var pos: Int,
    val color: Color
) {
    abstract fun getMoveCandidates(): List<Move>

    open fun isEmpty() = false
    fun hasColor(checkColor: Color) = checkColor == color
}