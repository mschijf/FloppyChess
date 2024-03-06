package floppychess.model

import Tools.Fen.FenBoard
import floppychess.model.piece.*
import kotlin.collections.ArrayDeque

class Board {

    private var colorToMove = Color.WHITE
    private var epFieldIndex = -1
    private var kingCastling = mutableSetOf<Color>()
    private var queenCastling = mutableSetOf<Color>()
    private var halfMoveCount = 0
    private var fullMoveCount = 0
    private val movesPlayed = ArrayDeque<Move>()
    private val board =
        ('1'..'8').map{ row ->
            ('a'..'h').map { col ->
                Field("$col$row")
            }
        }.flatten().associateBy { field -> field.name }

    init {
        board.values.forEach { field -> field.initReachableFields(board) }
        setStartPosition()
    }

    companion object {
        private fun toBoardIndex(row: Int, col: Int) = row * 8 + col
        fun toBoardIndex(field: String) = toBoardIndex(field[1]-'1', field[0]-'a')
        fun toFieldString(fieldIndex: Int) = ('a' + (fieldIndex % 8)).toString() + ('1' + (fieldIndex / 8)).toString()
    }


    private fun putPieceOnBoard(pieceType: PieceType, color: Color, fieldName: String) {
        val piece = when (pieceType) {
            PieceType.KING -> King(color)
            PieceType.QUEEN -> Queen(color)
            PieceType.ROOK -> Rook(color)
            PieceType.BISHOP -> Bishop(color)
            PieceType.KNIGHT -> Knight(color)
            PieceType.PAWN -> Pawn(color)
        }
        piece.setOnBoard(board[fieldName]!!)
    }

    private fun putPieceOnBoard(pieceChar: Char, fieldName: String) {
        val pieceType = PieceType.ofChar(pieceChar)
        val color = if (pieceChar.isLowerCase()) Color.BLACK else Color.WHITE
        putPieceOnBoard(pieceType, color, fieldName)
    }

    private fun clearBoard() {
        board.values.forEach { field -> field.clearField() }
    }

    fun setStartPosition() {
//        initByFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0")
        initByFen("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e3 0 1")
    }

    fun initByFen(fenString: String) {
        val fen = FenBoard(fenString)
        fillBoardByFen(fen)
        setCastling(fen)
        colorToMove = if (fen.whiteToMove) Color.WHITE else Color.BLACK
        epFieldIndex = if (fen.epField != null) toBoardIndex(fen.epField) else -1
        halfMoveCount = fen.halfMoveCount
        fullMoveCount = fen.fullMoveCount
    }

    private fun setCastling(fen: FenBoard) {
        kingCastling.clear()
        queenCastling.clear()
        if (fen.whiteKingCastlingAllowed) kingCastling += Color.WHITE
        if (fen.blackKingCastlingAllowed) kingCastling += Color.BLACK
        if (fen.whiteQueenCastlingAllowed) queenCastling += Color.WHITE
        if (fen.blackQueenCastlingAllowed) queenCastling += Color.BLACK
    }

    private fun fillBoardByFen(fen: FenBoard) {
        clearBoard()
        fen.occupiedFields.forEach { (fieldString, pieceChar) ->
            putPieceOnBoard(pieceChar, fieldString)
        }
    }

    //-----------------------------------------------------------------------------------------------------------------

    private fun getLegalMoves(): List<Move> {
        return board
            .values
            .filter { field -> field.hasPieceOfColor(colorToMove) }
//            .filter { it.getPiece().pieceType == PieceType.KNIGHT }
            .flatMap { it.getPiece().getMoveCandidates() }
    }

    fun doMove(move: Move) {
        if (move.isCapture())
            move.capturedPiece!!.removeFromBoard()
        move.piece.moveTo(move.to)
        movesPlayed.addLast(move)
    }

    fun takeBackLastMove() {
        val move = movesPlayed.removeLast()
        move.piece.moveTo(move.from)
        if (move.isCapture())
            move.capturedPiece!!.setOnBoard(move.capturedField!!)
    }

    //-----------------------------------------------------------------------------------------------------------------

    fun toAsciiBoard(): String {
        val asciiBoard = ('8' downTo '1').joinToString ("\n"){ row ->
            ('a'..'h').joinToString (""){ col ->
                val piece = board["$col$row"]!!.getPieceOrNull()
                piece?.toString() ?: "."
            }
        }
        return asciiBoard + "\n" + toFenString() + "\n" + getLegalMoves()
    }

    fun toFenString(): String {
        val fenPosition = ('8' downTo '1').joinToString ("/"){ row ->
            val sb = StringBuilder()
            var emptyCount = 0
            for (col in 'a'..'h') {
                if (board["$col$row"]!!.isEmpty()) {
                    emptyCount++
                } else {
                    if (emptyCount > 0)
                        sb.append(emptyCount)
                    emptyCount = 0
                    sb.append(board["$col$row"]!!.getPieceOrNull().toString() ?: ".")
                }
            }
            if (emptyCount > 0)
                sb.append(emptyCount)
            sb.toString()
        }
        val sb = StringBuilder(fenPosition)
        sb.append(" ")
        sb.append(if (colorToMove == Color.WHITE) "w" else "b")
        sb.append(" ")
        sb.append(if (epFieldIndex >= 0) toFieldString(epFieldIndex) else "-")
        sb.append(" ")
        if (Color.WHITE in kingCastling) sb.append("K")
        if (Color.WHITE in queenCastling) sb.append("Q")
        if (Color.BLACK in kingCastling) sb.append("k")
        if (Color.BLACK in queenCastling) sb.append("q")
        if (kingCastling.isEmpty() && queenCastling.isEmpty()) sb.append("-")
        sb.append(" ")
        sb.append(halfMoveCount)
        sb.append(" ")
        sb.append(fullMoveCount)
        return sb.toString()
    }
}