package floppychess.model.piece

import floppychess.model.Board
import floppychess.model.Color
import floppychess.model.Move
import floppychess.model.PieceType

class Pawn(color: Color): Piece(PieceType.PAWN, color) {

    override fun getMoveCandidates(): List<Move> {
        val result = mutableListOf<Move>()
        return result
    }
}