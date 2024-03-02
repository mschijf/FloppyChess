package model.piece

import model.Board
import model.Color
import model.Move
import model.PieceType

class Bishop(
    board: Board,
    pos: Int,
    color: Color
): Piece(board, pos, PieceType.BISHOP, color) {

    override fun getMoveCandidates(): List<Move> {
        val result = mutableListOf<Move>()
        for (direction in Board.diagonalDirections) {
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