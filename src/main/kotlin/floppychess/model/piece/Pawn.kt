package floppychess.model.piece

import floppychess.model.Color
import floppychess.model.Move
import floppychess.model.PieceType

class Pawn(color: Color): Piece(PieceType.PAWN, color) {

    override fun getMoveCandidates(): List<Move> {
        val slidingFields = if (color == Color.WHITE) field!!.legalWhitePawnMoveFields else field!!.legalBlackPawnMoveFields
        val captureFields = if (color == Color.WHITE) field!!.legalWhitePawnCaptureFields else field!!.legalBlackPawnCaptureFields

        val slideMoves = slidingFields
            .takeWhile { field -> field.isEmpty() }
            .map{moveTo -> Move(this, field!!, moveTo, null, null) }
        val captureMoves = captureFields
            .filter{ field -> field.hasPieceOfColor(color.otherColor()) || field.isEpField()}
            .map{moveTo -> Move(this, field!!, moveTo, moveTo.getPieceOrNull(), if (moveTo.isEmpty()) null else moveTo) }

        return slideMoves + captureMoves
    }
}