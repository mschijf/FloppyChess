package org.example.model

import org.example.Tools.Fen.FenBoard
import org.example.model.piece.*

class Board {

    private val emptyField = NoPiece(this)
    private val board = Array<Piece>(64) { emptyField }
    private var colorToMove = Color.WHITE
    private var epFieldIndex = -1
    private var kingCastling = mutableSetOf<Color>()
    private var queenCastling = mutableSetOf<Color>()
    private var halfMoveCount = 0
    private var fullMoveCount = 0

    operator fun get(index: Int): Piece {
        return board[index]
    }

    init {
        setStartPos()
    }

    private fun toBoardIndex(row: Int, col: Int) = row*8 + col
    private fun toBoardIndex(col: Char, row: Char) = toBoardIndex(row - '0' - 1, col - 'a')
    private fun toBoardIndex(field:String) = toBoardIndex(field[0], field[1])
    private fun toFieldString(fieldIndex:Int) = ('a' + fieldIndex % 8).toString() + ('1' + fieldIndex / 8).toString()


    private fun addPiece(pieceType: PieceType, color: Color, pos: Int) {
        board[pos] = when (pieceType) {
            PieceType.KING -> King(this, pos, color)
            PieceType.QUEEN -> Queen(this, pos, color)
            PieceType.ROOK -> Rook(this, pos, color)
            PieceType.BISHOP -> Bishop(this, pos, color)
            PieceType.KNIGHT -> Knight(this, pos, color)
            PieceType.PAWN -> Pawn(this, pos, color)
            PieceType.NONE -> throw Exception("Try To add a NoPiece")
        }
    }

    private fun addPiece(pieceChar: Char, pos: Int) {
        val pieceType = PieceType.ofChar(pieceChar)
        val color = if (pieceChar.isLowerCase()) Color.BLACK else Color.WHITE
        addPiece(pieceType, color, pos)
    }

    private fun clearBoard() {
        for (index in 0..63) {
            board[index] = emptyField
        }
    }

    fun setStartPos() {
        initByFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0")
    }

    fun initByFen(fenString: String) {
        val fen = FenBoard(fenString)
        fen.fillBoardByFen()
        fen.setCastling()
        colorToMove = if (fen.whiteToMove) Color.WHITE else Color.BLACK
        epFieldIndex = if (fen.epField != null) toBoardIndex(fen.epField) else -1
        halfMoveCount = fen.halfMoveCount
        fullMoveCount = fen.fullMoveCount
    }

    private fun FenBoard.setCastling() {
        kingCastling.clear()
        queenCastling.clear()
        if (this.whiteKingCastlingAllowed) kingCastling += Color.WHITE
        if (this.blackKingCastlingAllowed) kingCastling += Color.BLACK
        if (this.whiteQueenCastlingAllowed) queenCastling += Color.WHITE
        if (this.blackQueenCastlingAllowed) queenCastling += Color.BLACK
    }

    private fun FenBoard.fillBoardByFen() {
        clearBoard()
        this.occupiedFields.forEach { (fieldString, pieceChar) ->
            addPiece(pieceChar, toBoardIndex(fieldString))
        }
    }

    fun toAsciiBoard(): String {
        val asciiBoard = (7 downTo 0).joinToString ("\n"){ row ->
            (0..7).joinToString (""){ col ->
                board[toBoardIndex(row, col)].toString()
            }
        }
        return asciiBoard + "\n" + toFenString()
    }

    fun toFenString(): String {
        val fenPosition = (7 downTo 0).joinToString ("/"){ row ->
            val sb = StringBuilder()
            var emptyCount = 0
            for (col in 0..7) {
                if (board[toBoardIndex(row, col)].isEmpty()) {
                    emptyCount++
                } else {
                    if (emptyCount > 0)
                        sb.append(emptyCount)
                    emptyCount = 0
                    sb.append(board[toBoardIndex(row, col)].toString())
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