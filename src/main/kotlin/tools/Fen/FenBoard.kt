package org.example.Tools.Fen

class FenBoard(fenString: String) {
    private val fenParts = checkFenSyntax(fenString)

    val occupiedFields = getPieces(fenParts[0])

    val halfMoveCount = fenParts[4].toInt()
    val fullMoveCount = fenParts[5].toInt()
    val whiteToMove = fenParts[1] == "w"
    val epField = if (fenParts[3] == "-") null else fenParts[3]

    val whiteKingCastlingAllowed = 'K' in fenParts[2]
    val whiteQueenCastlingAllowed = 'Q' in fenParts[2]
    val blackKingCastlingAllowed = 'k' in fenParts[2]
    val blackQueenCastlingAllowed = 'q' in fenParts[2]

    private fun getPieces(fenPosition: String): Map<String, Char> {
        val result = mutableMapOf<String, Char>()
        fenPosition
            .split("/")
            .reversed()
            .forEachIndexed { rowNum, fenRow ->
                var col = 0
                fenRow.forEach {fenChar ->
                    if (fenChar.isDigit()) {
                        col += (fenChar - '0')
                    } else {
                        result[('a' + col).toString() + (rowNum + 1).toString()] = fenChar
                        col++
                    }
                }
            }
        return result
    }

    private fun legalField(field: String): Boolean =
        field.length == 2 && field[0] in 'a'..'h' && field[1] in '1'..'8'

    private fun legalRow(fenRow: String): Boolean {
        var col = 0
        fenRow.forEach {fenChar ->
            if (col > 7) {
                return false
            }
            if (fenChar.isDigit()) {
                col += (fenChar - '0')
            } else {
                if (fenChar !in "KQRNBPkqrnbp") {
                    return false
                }
                col++
            }
        }
        if (col != 8) {
            return false
        }
        return true
    }

    private fun checkFenSyntax(fenString: String): List<String> {
        //  "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0"

        val correctCastling = setOf(
            "KQkq","KQk","KQq","KQ","Kkq","Kk","Kq","K",
            "Qkq","Qk","Qq","Q","kq","k","q","-")
        val correctColor = setOf("w", "b")

        val fenParts = fenString.split("\\s+".toRegex())
        check(fenParts.size == 6) { "fenstring does not have 6 parts: $fenString" }
        check(fenParts[0].split("/").size == 8) {"fenstring 1st field does not have 8 rows: $fenString"}
        check(fenParts[0].split("/").all{row->legalRow(row)}) {"fenstring 1st field does have an incorrect row: $fenString"}
        check(fenParts[1] in correctColor) {"fenstring 2nd field 'color to move' syntaxt error: $fenString"}
        check(fenParts[2] in correctCastling) {"fenstring 3rd field 'castling' syntax error: $fenString"}
        check(fenParts[3] == "-" || legalField(fenParts[3])) {"fenstring 4th field 'ep' syntax error: $fenString"}
        check(fenParts[4].toIntOrNull() != null) {"fenstring 5th field 'half Move Count' is not a number: $fenString"}
        check(fenParts[5].toIntOrNull() != null) {"fenstring 5th field 'full Move Count' is not a number: $fenString"}

        return fenParts
    }
}

//
//private fun fillBoard(fenStringBoard: String) {
//    clearBoard()
//    fenStringBoard
//        .split("/")
//        .reversed()
//        .forEachIndexed { rowNum, fenRow -> fillBoardRow(rowNum, fenRow) }
//}
//
//private fun fillBoardRow(row: Int, fenRow: String) {
//    var col = 0
//    fenRow.forEach {fenChar ->
//        if (col > 7) {
//            throw FenStringException("Too many pieces/empty spaces on row $row in FenString (row = $fenRow")
//        }
//        if (fenChar.isDigit()) {
//            col += (fenChar - '0')
//        } else {
//            when (fenChar.lowercaseChar()) {
//                'k', 'q', 'r', 'n', 'b', 'p' -> addPiece(fenChar, toBoardIndex(row, col))
//                else -> throw FenStringException("unexpected Char in row $row FenString (row = $fenRow)")
//            }
//            col++
//        }
//    }
//    if (col != 8) {
//        throw FenStringException("Not enough pieces/empty spaces on row $row in FenString (row = $fenRow")
//    }
//}
//
//private fun setColorToMove(colorChar: String) {
//    colorToMove = if (colorChar == "w")
//        Color.WHITE
//    else if (colorChar == "b")
//        Color.BLACK
//    else
//        throw FenStringException("second part of fenstring is not 'b' or 'w'.")
//}
//
//private fun setCastling(fenCastlingString: String) {
//    val tmpKingCastling = mutableSetOf<Color>()
//    if ("K" in fenCastlingString) tmpKingCastling.add(Color.WHITE)
//    if ("k" in fenCastlingString) tmpKingCastling.add(Color.BLACK)
//    kingCastling = tmpKingCastling
//
//    val tmpQueenCastling = mutableSetOf<Color>()
//    if ("Q" in fenCastlingString) tmpQueenCastling.add(Color.WHITE)
//    if ("q" in fenCastlingString) tmpQueenCastling.add(Color.BLACK)
//    queenCastling = tmpQueenCastling
//}
//
//private fun setEpField(fenEpField: String) {
//    epFieldIndex = if (fenEpField == "-")
//        -1
//    else
//        toBoardIndex(fenEpField)
//}
//
//private fun setHalfMoveCount(fenHalfMoveCount: String) {
//    halfMoveCount = fenHalfMoveCount.toInt()
//}
//private fun setFullMoveCount(fenFullMoveCount: String) {
//    fullMoveCount = fenFullMoveCount.toInt()
//}
