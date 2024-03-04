package floppychess.model.piece

import floppychess.model.Board
import floppychess.model.Color
import floppychess.model.Move
import floppychess.model.PieceType

class Bishop(
    board: Board,
    pos: Int,
    color: Color
): Piece(board, pos, PieceType.BISHOP, color) {

    override fun getMoveCandidates(): List<Move> {
        return board[pos]
            .legalBishopFieldsPerDirection
            .flatMap { dir -> getSlidingMoveToFieldIndexes(dir) }
            .map{moveTo -> Move(this, pos, moveTo) }
    }
}