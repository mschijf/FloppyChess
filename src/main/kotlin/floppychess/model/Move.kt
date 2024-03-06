package floppychess.model

import floppychess.model.piece.Piece


data class Move(
    val piece: Piece,
    val from: Field,
    val to: Field,
    val capturedPiece: Piece?,
    val capturedField: Field?) {

    override fun toString(): String {
        return piece.toString() + from.name + to.name
    }

    fun isCapture() = capturedPiece != null
}