package floppychess.model.piece

import floppychess.model.Board
import floppychess.model.Color
import floppychess.model.Move
import floppychess.model.PieceType

class Queen(
    board: Board,
    pos: Int,
    color: Color
): Piece(board, pos, PieceType.QUEEN, color) {

    override fun getMoveCandidates(): List<Move> {
        return board[pos]
            .legalQueenFieldsPerDirection
            .flatMap { dir -> getSlidingMoveToFieldIndexes(dir) }
            .map{moveTo -> Move(this, pos, moveTo) }
    }
}