package floppychess.model.piece

import floppychess.model.Color
import floppychess.model.Move
import floppychess.model.PieceType

class Bishop(color: Color): Piece(PieceType.BISHOP, color) {

    override fun getMoveCandidates(): List<Move> {
        return field!!
            .legalBishopFieldsPerDirection
            .flatMap { dir -> getSlidingMoveToFieldIndexes(dir) }
            .map{moveTo -> Move(this, field!!, moveTo, moveTo.getPieceOrNull(), if (moveTo.isEmpty()) null else moveTo) }
    }
}