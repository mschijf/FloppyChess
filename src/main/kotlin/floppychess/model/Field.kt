package floppychess.model

import floppychess.model.piece.Piece
import floppychess.tools.*

class Field(val name: String) {
    private var piece: Piece? = null
    private var epFlag = false

    fun isEmpty() = piece == null
    fun hasPieceOfColor(color: Color) = piece != null && piece!!.hasColor(color)

    fun setPiece(aPiece: Piece) {
        check(piece==null ) {"Trying to put a piece on a field that is not empty: $this"}
        piece = aPiece
    }
    fun clearField() {
        piece = null
        epFlag = false
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
    var legalWhitePawnMoveFields: List<Field> = emptyList()
        private set
    var legalWhitePawnCaptureFields: List<Field> = emptyList()
        private set
    var legalBlackPawnMoveFields: List<Field> = emptyList()
        private set
    var legalBlackPawnCaptureFields: List<Field> = emptyList()
        private set

    fun initReachableFields(fieldMap: Map<String, Field>) {
        legalKingFields = getKingMoveFields(name).map { fieldMap[it]!! }
        legalKnightFields = getKnightMoveFields(name).map { fieldMap[it]!! }
        legalRookFieldsPerDirection = getRookMoveFields(name).map { slidingList -> slidingList.map{ fieldMap[it]!! } }
        legalBishopFieldsPerDirection = getBishopMoveFields(name).map { slidingList -> slidingList.map{ fieldMap[it]!! } }
        legalQueenFieldsPerDirection = legalRookFieldsPerDirection + legalBishopFieldsPerDirection
        legalWhitePawnMoveFields = getPawnMoveFields(name, colorIsWhite = true).map { fieldMap[it]!! }
        legalWhitePawnCaptureFields = getPawnCaptureFields(name, colorIsWhite = true).map { fieldMap[it]!! }
        legalBlackPawnMoveFields = getPawnMoveFields(name, colorIsWhite = true).map { fieldMap[it]!! }
        legalBlackPawnCaptureFields = getPawnCaptureFields(name, colorIsWhite = true).map { fieldMap[it]!! }
    }

    fun isEpField(): Boolean {
        return epFlag
    }

    fun setEp() {
        epFlag = true
    }
    fun clearEp() {
        epFlag = false
    }
}