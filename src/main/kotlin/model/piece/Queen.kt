package model.piece

import model.Board
import model.Color
import model.Move
import model.PieceType

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