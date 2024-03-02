package model

import model.piece.Piece


data class Move(val piece: Piece, val from: Int, val to: Int) {
    override fun toString(): String {
        return piece.toString() + Board.toFieldString(from) + Board.toFieldString(to)
    }
}