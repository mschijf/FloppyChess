package floppychess.model

enum class PieceType(val pieceChar: Char) {
    KING('k'),
    QUEEN('q'),
    ROOK('r'),
    BISHOP('b'),
    KNIGHT('n'),
    PAWN('p');

    companion object {
        fun ofChar(pieceChar: Char): PieceType = PieceType.entries.first{ pt -> pt.pieceChar == pieceChar.lowercaseChar() }
    }
}


