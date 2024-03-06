package floppychess.model

import floppychess.model.piece.Piece
import floppychess.tools.getBishopMoveFields
import floppychess.tools.getKingMoveFields
import floppychess.tools.getKnightMoveFields
import floppychess.tools.getRookMoveFields

class Field(val name: String) {
    private var piece: Piece? = null
    fun isEmpty() = piece == null
    fun hasPieceOfColor(color: Color) = piece != null && piece!!.hasColor(color)

    fun setPiece(aPiece: Piece) {
        check(piece==null ) {"Trying to put a piece on a field that is not empty: ${this.toString()}"}
        piece = aPiece
    }
    fun clearField() {
        piece = null
    }

    fun getPieceOrNull() = piece
    fun getPiece() = piece!!

    override fun toString() = "$name: $piece"

    var legalKingFields: List<Field> = emptyList()
        private set
    var legalKnightFields: List<Field> = emptyList()
        private set
    var legalRookFieldsPerDirection: List<List<Field>> = emptyList()
        private set
    var legalBishopFieldsPerDirection: List<List<Field>> = emptyList()
        private set
    var legalQueenFieldsPerDirection: List<List<Field>> = emptyList()
        private set

    fun initReachableFields(fieldMap: Map<String, Field>) {
        legalKingFields = getKingMoveFields(name).map { fieldMap[it]!! }
        legalKnightFields = getKnightMoveFields(name).map { fieldMap[it]!! }
        legalRookFieldsPerDirection = getRookMoveFields(name).map { slidingList -> slidingList.map{ fieldMap[it]!! } }
        legalBishopFieldsPerDirection = getBishopMoveFields(name).map { slidingList -> slidingList.map{ fieldMap[it]!! } }
        legalQueenFieldsPerDirection = legalRookFieldsPerDirection + legalBishopFieldsPerDirection
    }
}