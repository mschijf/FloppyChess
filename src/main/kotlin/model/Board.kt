package model

import Tools.Fen.FenBoard
import model.Color
import model.piece.*

//  132	133	134	135	136	137	138	139	140	141	142	143
//  120	121	122	123	124	125	126	127	128	129	130	131
//  108	109	110	111	112	113	114	115	116	117	118	119
//  96	97	98	99	100	101	102	103	104	105	106	107
//  84	85	86	87	88	89	90	91	92	93	94	95
//  72	73	74	75	76	77	78	79	80	81	82	83
//  60	61	62	63	64	65	66	67	68	69	70	71
//  48	49	50	51	52	53	54	55	56	57	58	59
//  36	37	38	39	40	41	42	43	44	45	46	47
//  24	25	26	27	28	29	30	31	32	33	34	35
//  12	13	14	15	16	17	18	19	20	21	22	23
//  0	1	2	3	4	5	6	7	8	9	10	11

class Board {

    private val emptyField = NoPiece(this)
    private val board = Array<Piece>(144) { emptyField }
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

    companion object {
        private fun toBoardIndex(row: Int, col: Int) = (2+row) * 12 + (col + 2)
        private fun toBoardIndex(field: String) = toBoardIndex(field[1]-'1', field[0]-'a')
        fun toFieldString(fieldIndex: Int) = ('a' + (fieldIndex % 12) - 2 ).toString() + ('1' + (fieldIndex / 12) - 2).toString()
        fun onBoard(fieldIndex: Int): Boolean = fieldIndex / 12 in (2..9) && fieldIndex % 12 in (2..9)

        val knightDirections = listOf(10, 23, 25, 14, -10, -23, -25, -14)
        val orthogonalDirections = listOf(12, 1, -12, -1)
        val diagonalDirections = listOf(11, 13, -11, -13)
        val allDirections = orthogonalDirections + diagonalDirections
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

    private fun addPiece(pieceChar: Char, pos: Int) {
        val pieceType = PieceType.ofChar(pieceChar)
        val color = if (pieceChar.isLowerCase()) Color.BLACK else Color.WHITE
        addPiece(pieceType, color, pos)
    }

    private fun clearBoard() {
        for (index in board.indices) {
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

    private fun getLegalMoves(): List<Move> {
        return board
            .filter { it.hasColor(colorToMove) }
            .filter{it.pieceType == PieceType.KING }
            .flatMap { piece: Piece -> piece.getMoveCandidates() }
    }

    //-----------------------------------------------------------------------------------------------------------------

    fun toAsciiBoard(): String {
        val asciiBoard = (7 downTo 0).joinToString ("\n"){ row ->
            (0..7).joinToString (""){ col ->
                board[toBoardIndex(row, col)].toString()
            }
        }
        return asciiBoard + "\n" + getLegalMoves()
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