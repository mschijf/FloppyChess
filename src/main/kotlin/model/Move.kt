package org.example.model

import org.example.model.Board
import org.example.model.piece.Piece

data class Move(val piece: Piece, val from: Int, val to: Int) {
    override fun toString(): String {
        return piece.toString() + Board.toFieldString(from) + Board.toFieldString(to)
    }
}