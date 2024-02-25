package org.example.model.piece

import org.example.model.Board
import org.example.model.Color
import org.example.model.Move

class King(
    board: Board,
    pos: Int,
    color: Color
): Piece(board, pos, color) {

    override fun getMoveCandidates(): List<Move> {
        val directions = listOf(-9, -8, -7, -1, 1, 7, 8, 9)
        return directions
            .filter {dir -> board[pos+dir].isEmpty() || board[pos+dir].hasColor(color.otherColor())}
            .map{dir -> Move(pos, pos+dir) }
    }

    override fun toString() = if (color == Color.WHITE) "K" else "k"
}