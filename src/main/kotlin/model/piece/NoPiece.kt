package model.piece

import model.Board
import model.Color
import model.Move
import model.PieceType

class NoPiece(board: Board): Piece(board, -1, PieceType.NONE, Color.NONE) {

    override fun getMoveCandidates(): List<Move> = emptyList()
    override fun isEmpty() = true
}