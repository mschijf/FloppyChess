package org.example.model.piece

import org.example.model.Board
import org.example.model.Color
import org.example.model.Move

class Bishop(
    board: Board,
    pos: Int,
    color: Color
): Piece(board, pos, color) {

    override fun getMoveCandidates(): List<Move> {
        val result = mutableListOf<Move>()
        for (direction in listOf(-9, -7, 7, 9)) {
            var newPos = pos+direction
            while (board[newPos].isEmpty()) {
                result + Move(pos, newPos)
            }
            if (board[newPos].hasColor(color.otherColor())) {
                result + Move(pos, newPos)
            }
        }
        return result
    }

    override fun toString() = if (color == Color.WHITE) "B" else "b"
}