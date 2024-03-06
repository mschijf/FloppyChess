package floppychess.model.piece

import floppychess.model.Color
import floppychess.model.Move
import floppychess.model.PieceType

class Bishop(color: Color): Piece(PieceType.BISHOP, color) {

    override fun getMoveCandidates(): List<Move> {
        return getSlidingMoves(field!!.legalBishopFieldsPerDirection)
    }
}