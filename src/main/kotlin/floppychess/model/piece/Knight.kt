package floppychess.model.piece

import floppychess.model.Color
import floppychess.model.Move
import floppychess.model.PieceType

class Knight(color: Color): Piece(PieceType.KNIGHT, color) {

    override fun getMoveCandidates(): List<Move> {
        return field!!.legalKnightFields
            .filter {moveTo -> moveTo.isEmpty() || moveTo.hasPieceOfColor(color.otherColor())}
            .map{moveTo -> Move(this, field!!, moveTo, moveTo.getPieceOrNull(), if (moveTo.isEmpty()) null else moveTo) }
    }
}