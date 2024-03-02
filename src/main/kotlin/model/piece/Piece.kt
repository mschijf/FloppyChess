package model.piece

import model.Board
import model.Color
import model.Move
import model.PieceType

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