package floppychess.model.piece

import floppychess.model.Color
import floppychess.model.Move
import floppychess.model.PieceType

class King(color: Color): Piece(PieceType.KING, color) {

    override fun getMoveCandidates(): List<Move> {
        return field!!.legalKingFields
            .filter {moveTo -> moveTo.isEmpty() || moveTo.hasPieceOfColor(color.otherColor())}
            .map{moveTo -> Move(this, field!!, moveTo, moveTo.getPieceOrNull(), if (moveTo.isEmpty()) null else moveTo) }
    }
}