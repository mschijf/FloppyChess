package model.piece

import model.Board
import model.Color
import model.Move
import model.PieceType

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