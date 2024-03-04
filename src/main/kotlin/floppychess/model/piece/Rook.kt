package floppychess.model.piece

import floppychess.model.*

class Rook(
    board: Board,
    pos: Int,
    color: Color
): Piece(board, pos, PieceType.ROOK, color) {

    override fun getMoveCandidates(): List<Move> {
        return board[pos]
            .legalRookFieldsPerDirection
            .flatMap{ dir -> getSlidingMoveToFieldIndexes(dir) }
            .map{moveTo -> Move(this, pos, moveTo) }
    }
}