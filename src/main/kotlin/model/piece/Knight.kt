package org.example.model.piece

import org.example.model.Board
import org.example.model.Color
import org.example.model.Move
import org.example.model.PieceType

class Knight(
    board: Board,
    pos: Int,
    color: Color
): Piece(board, pos, PieceType.KNIGHT, color) {

    override fun getMoveCandidates(): List<Move> {
        return Board.knightDirections
            .filter {dir -> Board.onBoard(pos+dir)}
            .filter {dir -> board[pos+dir].isEmpty() || board[pos+dir].hasColor(color.otherColor())}
            .map{dir -> Move(this, pos, pos+dir) }
    }
}