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
        //"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0"
        val fen = FenBoard(fenString)
        fen.fillBoardByFen()
        colorToMove = if (fen.whiteToMove) Color.WHITE else Color.BLACK
        fen.setCastling()
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
        val sb = StringBuilder()

        for (row in 7 downTo 0) {
            for (col in 0..7) {
                sb.append(board[toBoardIndex(row, col)].toString())
            }
            sb.append("\n")
        }
        sb.append("$colorToMove to move\n")
        sb.append("ep: $epFieldIndex\n")
        sb.append("king  castling: $kingCastling\n")
        sb.append("queen castling: $kingCastling\n")
        sb.append("Half Move count: $halfMoveCount\n")
        sb.append("Full Move count: $fullMoveCount\n")
        return sb.toString()
    }

}