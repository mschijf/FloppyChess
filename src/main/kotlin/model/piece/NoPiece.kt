package org.example.model.piece

import org.example.model.Board
import org.example.model.Color
import org.example.model.Move
import org.example.model.PieceType

class NoPiece(board: Board): Piece(board, -1, PieceType.NONE, Color.NONE) {

    override fun getMoveCandidates(): List<Move> = emptyList()
    override fun isEmpty() = true
}