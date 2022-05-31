package com.example.a3d.shorrChess
import com.example.a3d.R
import kotlin.math.abs
var turn = 0
var chessArray = mutableSetOf<Piece>()
lateinit var movingPiece: Piece
lateinit var lastMove: LastMove
object ChessGame {

    init {
        reset()
    }

    fun clear() {
        chessArray.clear()
    }

    fun addPiece(piece: Piece) {
        chessArray.add(piece)
    }

    fun movePiece(from: Square, to: Square) {
        movePiece(from.col, from.row, to.col, to.row)
    }

    fun canMove(from: Square, to: Square):Boolean {
        if(from.col == to.col && from.row == to.row)
            return false
        val movingPiece = pieceAt(from)?: return false
        when(movingPiece.types) {
            Types.KING -> return canKingMove(from, to)
            Types.QUEEN -> return canQueenMove(from, to)
            Types.ROOK -> return canRookMove(from, to)
            Types.BISHOP -> return canBishopMove(from, to)
            Types.KNIGHT -> return canKnightMove(from, to)
            Types.PAWN -> return canPawnMove(from, to)
        }
        return true
    }

    private fun movePiece(fromCol : Int, fromRow: Int, toCol: Int, toRow: Int)
    {
        if(fromRow == toRow && fromCol == toCol) return
        movingPiece = pieceAt(fromCol, fromRow) ?: return
        val capturedPiece = pieceAt(toCol, toRow)
        if(turn % 2 == 0 && movingPiece.player == Player.BLACK) {
            return
        }
        if(turn % 2 == 1 && movingPiece.player == Player.WHITE) {
            return
        }
        pieceAt(toCol, toRow)?.let {
            if(it.player == movingPiece.player)
            {
                return
            }
        }
        if(canMove(Square(fromCol,fromRow), Square(toCol, toRow)))
        {
            lastMove.PieceType = movingPiece.types
            lastMove.Player = movingPiece.player
            lastMove.lastMoveStarting = Square(fromCol, fromRow)
            lastMove.lastMoveEnding = Square(toCol, toRow)

            chessArray.remove(capturedPiece)
            chessArray.remove(movingPiece)
            chessArray.add(Piece(toCol, toRow, movingPiece.player, movingPiece.types, movingPiece.resID))
            turn++
        }
    }

    fun reset() {
        turn = 0
        lastMove = LastMove(Square(0,0), Square(0,0), Player.WHITE, Types.PAWN)
        chessArray.removeAll(chessArray)

        chessArray.add(Piece(3, 0, Player.WHITE, Types.QUEEN, R.drawable.queen_white))
        chessArray.add(Piece(3, 7, Player.BLACK, Types.QUEEN, R.drawable.queen_black))
        chessArray.add(Piece(4, 0, Player.WHITE, Types.KING, R.drawable.king_white_shorr))
        chessArray.add(Piece(4, 7, Player.BLACK, Types.KING, R.drawable.king_black_shorr))

        for(i in 0..1)
        {
            chessArray.add(Piece(0 + i * 7, 0, Player.WHITE, Types.ROOK, R.drawable.rook_white))
            chessArray.add(Piece(0 + i * 7, 7, Player.BLACK, Types.ROOK, R.drawable.rook_black))

            chessArray.add(Piece(1 + i * 5, 0, Player.WHITE, Types.KNIGHT, R.drawable.knight_white))
            chessArray.add(Piece(1 + i * 5, 7, Player.BLACK, Types.KNIGHT, R.drawable.knight_black))

            chessArray.add(Piece(2 + i * 3, 0, Player.WHITE, Types.BISHOP, R.drawable.bishop_white))
            chessArray.add(Piece(2 + i * 3, 7, Player.BLACK, Types.BISHOP, R.drawable.bishop_black))

        }

        for (i in 0..7)
        {
            chessArray.add(Piece(i, 1, Player.WHITE, Types.PAWN, R.drawable.pawn_white))
            chessArray.add(Piece(i, 6, Player.BLACK, Types.PAWN, R.drawable.pawn_black))
        }
    }


    override fun toString(): String {
        var desc = " \n"
        for (row in 7 downTo 0) {
            desc += "$row"
            desc += boardRow(row)
            desc += "\n"
        }
        desc += "  0 1 2 3 4 5 6 7"

        return desc
    }

    private fun boardRow(row: Int) : String {
        var desc = ""
        for (col in 0 until 8) {
            desc += " "
            desc += pieceAt(col, row)?.let {
                val white = it.player == Player.WHITE
                when (it.types) {
                    Types.KING -> if (white) "k" else "K"
                    Types.QUEEN -> if (white) "q" else "Q"
                    Types.BISHOP -> if (white) "b" else "B"
                    Types.ROOK -> if (white) "r" else "R"
                    Types.KNIGHT -> if (white) "n" else "N"
                    Types.PAWN -> if (white) "p" else "P"
                }
            } ?: "."
        }
        return desc
    }

    fun pgn(): String {
        var desc = " \n"
        for (row in 7 downTo 0) {
            desc += "${row + 1}"
            desc += boardRow(row)
            desc += "\n"
        }
        desc += "  a b c d e f g h"

        return desc
    }
    fun pieceAt(square: Square): Piece? {
        return pieceAt(square.col, square.row)
    }

    fun pieceAt(col: Int, row: Int) : Piece? {
        for (piece in chessArray)
        {
            if (row == piece.row && col == piece.col)
            {
                return piece
            }
        }
        return null
    }
}