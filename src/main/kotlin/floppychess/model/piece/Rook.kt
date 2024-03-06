package floppychess.model.piece

import floppychess.model.*

class Rook(color: Color): Piece(PieceType.ROOK, color) {

    override fun getMoveCandidates(): List<Move> {
        return getSlidingMoves(field!!.legalRookFieldsPerDirection)
    }
}