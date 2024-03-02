package org.example.model.piece

import org.example.model.Board
import org.example.model.Color
import org.example.model.Move
import org.example.model.PieceType

class Queen(
    board: Board,
    pos: Int,
    color: Color
): Piece(board, pos, PieceType.QUEEN, color) {

    override fun getMoveCandidates(): List<Move> {
        val result = mutableListOf<Move>()
        for (direction in Board.allDirections) {
            var newPos = pos+direction
            while (board[newPos].isEmpty()) {
                result + Move(this, pos, newPos)
            }
            if (board[newPos].hasColor(color.otherColor())) {
                result + Move(this, pos, newPos)
            }
        }
        return result
    }
}