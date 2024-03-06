package floppychess.model.piece

import floppychess.model.Color
import floppychess.model.Move
import floppychess.model.PieceType

class Knight(color: Color): Piece(PieceType.KNIGHT, color) {

    override fun getMoveCandidates(): List<Move> {
        return getJumpFieldMoves(field!!.legalKnightFields)
    }
}