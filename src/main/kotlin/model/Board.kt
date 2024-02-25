package org.example.model

import org.example.model.piece.*

class Board {

    private val emptyField = NoPiece(this)
    private val board = Array<Piece>(64) { emptyField }

    operator fun get(index: Int): Piece {
        return board[index]
    }

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

    private fun toBoardIndex(row: Int, col: Int) = row*8 + col

    fun init() {
        for (row in 0..7) {
            for (col in 0..7) {
                board[toBoardIndex(row, col)] = emptyField
            }
        }
        addPiece(PieceType.ROOK, Color.WHITE, toBoardIndex(0,0))
        addPiece(PieceType.KNIGHT, Color.WHITE, toBoardIndex(0,1))
        addPiece(PieceType.BISHOP, Color.WHITE, toBoardIndex(0,2))
        addPiece(PieceType.QUEEN, Color.WHITE, toBoardIndex(0,3))
        addPiece(PieceType.KING, Color.WHITE, toBoardIndex(0,4))
        addPiece(PieceType.BISHOP, Color.WHITE, toBoardIndex(0,5))
        addPiece(PieceType.KNIGHT, Color.WHITE, toBoardIndex(0,6))
        addPiece(PieceType.ROOK, Color.WHITE, toBoardIndex(0,7))
        for (col in 0..7) {
            addPiece(PieceType.PAWN, Color.WHITE, toBoardIndex(1,col))
        }

        addPiece(PieceType.ROOK, Color.BLACK, toBoardIndex(7,0))
        addPiece(PieceType.KNIGHT, Color.BLACK, toBoardIndex(7,1))
        addPiece(PieceType.BISHOP, Color.BLACK, toBoardIndex(7,2))
        addPiece(PieceType.QUEEN, Color.BLACK, toBoardIndex(7,3))
        addPiece(PieceType.KING, Color.BLACK, toBoardIndex(7,4))
        addPiece(PieceType.BISHOP, Color.BLACK, toBoardIndex(7,5))
        addPiece(PieceType.KNIGHT, Color.BLACK, toBoardIndex(7,6))
        addPiece(PieceType.ROOK, Color.BLACK, toBoardIndex(7,7))
        for (col in 0..7) {
            addPiece(PieceType.PAWN, Color.BLACK, toBoardIndex(6,col))
        }
    }

    fun toAscciiBoard(): String {
        val sb = StringBuilder()

        for (row in 7 downTo 0) {
            for (col in 0..7) {
                sb.append(board[toBoardIndex(row, col)].toString())
            }
            sb.append("\n")
        }
        return sb.toString()
    }

}