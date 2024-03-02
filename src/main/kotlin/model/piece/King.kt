package model.piece

import model.Board
import model.Color
import model.Move
import model.PieceType

class King(
    board: Board,
    pos: Int,
    color: Color
): Piece(board, pos, PieceType.KING, color) {

    override fun getMoveCandidates(): List<Move> {
        return Board.allDirections
            .filter {dir -> Board.onBoard(pos+dir)}
            .filter {dir -> board[pos+dir].isEmpty() || board[pos+dir].hasColor(color.otherColor())}
            .map{dir -> Move(this, pos, pos+dir) }
    }
}