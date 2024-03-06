package floppychess.model.piece

import floppychess.model.Color
import floppychess.model.Move
import floppychess.model.PieceType

class Queen(color: Color): Piece(PieceType.QUEEN, color) {

    override fun getMoveCandidates(): List<Move> {
        return field!!
            .legalQueenFieldsPerDirection
            .flatMap { dir -> getSlidingMoveToFieldIndexes(dir) }
            .map{moveTo -> Move(this, field!!, moveTo, moveTo.getPieceOrNull(), if (moveTo.isEmpty()) null else moveTo) }
    }
}