package floppychess.model.piece

import floppychess.model.*

abstract class Piece(
    val pieceType: PieceType,
    val color: Color
) {
    var field: Field? = null
        private set

    abstract fun getMoveCandidates(): List<Move>

    fun getSlidingMoveToFieldIndexes(fieldList: List<Field>): List<Field> {
        val tmp = mutableListOf<Field>()
        var i = 0
        while (i < fieldList.size && fieldList[i].isEmpty()) {
            tmp += fieldList[i]
            i++
        }
        if (i < fieldList.size && fieldList[i].hasPieceOfColor(color.otherColor())) {
            tmp += fieldList[i]
        }
        return tmp
    }

    fun hasColor(checkColor: Color) = checkColor == color

    override fun toString() =
        if (color== Color.WHITE) pieceType.pieceChar.uppercase() else pieceType.pieceChar.lowercase()

    fun moveTo(to: Field) {
        removeFromBoard()
        setOnBoard(to)
    }

    fun removeFromBoard() {
        field!!.clearField()
        field = null
    }

    fun setOnBoard(newField: Field) {
        newField.setPiece(this)
        field = newField
    }

    fun isOnBoard() = field != null
    fun isCaptured() = !isOnBoard()
}