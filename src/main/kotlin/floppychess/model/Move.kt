package floppychess.model

import floppychess.model.piece.Piece


data class Move(val piece: Piece, val from: Int, val to: Int, val capturedPiece: Piece?) {
    override fun toString(): String {
        return piece.toString() + Board.toFieldString(from) + Board.toFieldString(to)
    }

    fun isCapture() = capturedPiece != null
}