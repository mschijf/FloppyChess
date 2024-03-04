package floppychess.model.piece

import floppychess.model.Board
import floppychess.model.Color
import floppychess.model.Move
import floppychess.model.PieceType

abstract class Piece(
    val board: Board,
    var pos: Int,
    val pieceType: PieceType,
    val color: Color
) {
    abstract fun getMoveCandidates(): List<Move>

    fun getSlidingMoveToFieldIndexes(posList: List<Int>): List<Int> {
        val tmp = mutableListOf<Int>()
        var i = 0
        while (i < posList.size && board[posList[i]].isEmpty()) {
            tmp += posList[i]
            i++
        }
        if (i < posList.size && board[posList[i]].hasPieceOfColor(color.otherColor())) {
            tmp += posList[i]
        }
        return tmp
    }

    fun hasColor(checkColor: Color) = checkColor == color

    override fun toString() =
        if (color== Color.WHITE) pieceType.pieceChar.uppercase() else pieceType.pieceChar.lowercase()
}