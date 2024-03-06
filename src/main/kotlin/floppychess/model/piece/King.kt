package floppychess.model.piece

import floppychess.model.Board
import floppychess.model.Color
import floppychess.model.Move
import floppychess.model.PieceType

class King(
    board: Board,
    pos: Int,
    color: Color
): Piece(board, pos, PieceType.KING, color) {

    override fun getMoveCandidates(): List<Move> {
        return board[pos].legalKingFields
            .filter {moveTo -> board[moveTo].isEmpty() || board[moveTo].hasPieceOfColor(color.otherColor())}
            .map{moveTo -> Move(this, pos, moveTo, board[moveTo].getPieceOrNull()) }
    }
}