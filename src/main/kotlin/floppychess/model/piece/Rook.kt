package floppychess.model.piece

import floppychess.model.*

class Rook(color: Color): Piece(PieceType.ROOK, color) {

    override fun getMoveCandidates(): List<Move> {
        return field!!
            .legalRookFieldsPerDirection
            .flatMap{ dir -> getSlidingMoveToFieldIndexes(dir) }
            .map{moveTo -> Move(this, field!!, moveTo, moveTo.getPieceOrNull(), if (moveTo.isEmpty()) null else moveTo) }
    }
}