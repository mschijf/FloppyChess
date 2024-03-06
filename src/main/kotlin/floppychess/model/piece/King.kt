package floppychess.model.piece

import floppychess.model.Color
import floppychess.model.Move
import floppychess.model.PieceType

class King(color: Color): Piece(PieceType.KING, color) {

    override fun getMoveCandidates(): List<Move> {
        return getJumpFieldMoves(field!!.legalKingFields)
    }
}