package org.example.model.piece

import org.example.model.Board
import org.example.model.Color
import org.example.model.Move

class Knight(
    board: Board,
    pos: Int,
    color: Color
): Piece(board, pos, color) {

    override fun getMoveCandidates(): List<Move> {
        val directions = listOf(-17, -15, -10, -6, 6, 10, 15, 17)
        return directions
            .filter {dir -> board[pos+dir].isEmpty() || board[pos+dir].hasColor(color.otherColor())}
            .map{dir -> Move(pos, pos+dir) }
    }

    override fun toString() = if (color == Color.WHITE) "N" else "n"
}