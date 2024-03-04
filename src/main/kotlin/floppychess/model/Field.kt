package floppychess.model

import floppychess.model.piece.Piece
import floppychess.tools.getBishopMoveFields
import floppychess.tools.getKingMoveFields
import floppychess.tools.getKnightMoveFields
import floppychess.tools.getRookMoveFields

class Field(val boardIndex: Int) {
    private var piece: Piece? = null
    fun isEmpty() = piece == null
    fun hasPieceOfColor(color: Color) = piece != null && piece!!.hasColor(color)

    fun setPiece(aPiece: Piece) {
        piece = aPiece
    }
    fun clearField() {
        piece = null
    }

    fun getPieceOrNull() = piece
    fun getPiece() = piece!!

    override fun toString(): String {
        return if (piece == null) "." else piece!!.toString()
    }

    val legalKingFields: List<Int> =
        getKingMoveFields(Board.toFieldString(boardIndex))
            .map { Board.toBoardIndex(it) }
    val legalKnightFields: List<Int> =
        getKnightMoveFields(Board.toFieldString(boardIndex))
            .map { Board.toBoardIndex(it) }
    val legalRookFieldsPerDirection: List<List<Int>> =
        getRookMoveFields(Board.toFieldString(boardIndex))
            .map { slidingList -> slidingList.map{Board.toBoardIndex(it) } }
    val legalBishopFieldsPerDirection: List<List<Int>> =
        getBishopMoveFields(Board.toFieldString(boardIndex))
            .map { slidingList -> slidingList.map{Board.toBoardIndex(it) } }
    val legalQueenFieldsPerDirection: List<List<Int>> =
        legalRookFieldsPerDirection + legalBishopFieldsPerDirection


}