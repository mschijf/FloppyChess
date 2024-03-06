package floppychess.model.piece

import floppychess.model.*

abstract class Piece(
    val pieceType: PieceType,
    val color: Color
) {
    var field: Field? = null
        private set

    abstract fun getMoveCandidates(): List<Move>

    fun getJumpFieldMoves(fieldList: List<Field>): List<Move> {
        return fieldList
            .filter {moveTo -> moveTo.isEmpty() || moveTo.hasPieceOfColor(color.otherColor())}
            .map{moveTo -> Move(this, field!!, moveTo, moveTo.getPieceOrNull(), if (moveTo.isEmpty()) null else moveTo) }
    }

    fun getSlidingMoves(listOfFieldList: List<List<Field>>): List<Move> {
        val sliders = listOfFieldList
            .flatMap { fieldList -> getSlidingMoveToFields(fieldList) }
            .map{moveTo -> Move(this, field!!, moveTo, null, null) }
        val captures = listOfFieldList
            .mapNotNull { fieldList -> getSlidingCaptureField(fieldList) }
            .map{moveTo -> Move(this, field!!, moveTo, moveTo.getPieceOrNull(), if (moveTo.isEmpty()) null else moveTo) }
        return sliders+captures
    }

    private fun getSlidingMoveToFields(fieldList: List<Field>): List<Field> {
        return fieldList.takeWhile { field -> field.isEmpty() }
    }

    private fun getSlidingCaptureField(fieldList: List<Field>): Field? {
        val firstPieceAfterSlide = fieldList.dropWhile { field -> field.isEmpty() }.firstOrNull()
        return if (firstPieceAfterSlide?.hasPieceOfColor(color.otherColor()) == true) firstPieceAfterSlide else null
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