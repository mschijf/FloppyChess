package org.example.model.piece

import org.example.model.Board
import org.example.model.Color
import org.example.model.Move
import org.example.model.PieceType

class Pawn(
    board: Board,
    pos: Int,
    color: Color
): Piece(board, pos, PieceType.PAWN, color) {

    override fun getMoveCandidates(): List<Move> {
        val result = mutableListOf<Move>()
        return result
    }

    override fun toString() = if (color == Color.WHITE) "P" else "p"
}