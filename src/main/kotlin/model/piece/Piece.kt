package org.example.model.piece

import org.example.model.Board
import org.example.model.Color
import org.example.model.Move
import org.example.model.PieceType

abstract class Piece(
    val board: Board,
    var pos: Int,
    val pieceType: PieceType,
    val color: Color
) {
    abstract fun getMoveCandidates(): List<Move>

    open fun isEmpty() = false
    fun hasColor(checkColor: Color) = checkColor == color

    override fun toString() =
        if (color==Color.WHITE) pieceType.pieceChar.uppercase() else pieceType.pieceChar.lowercase()
}