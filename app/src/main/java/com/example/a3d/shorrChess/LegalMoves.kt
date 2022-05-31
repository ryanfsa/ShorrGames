package com.example.a3d.shorrChess

import com.example.a3d.R
import kotlin.math.abs
var hasWhiteKingMoved = false
var hasBlackKingMoved = false
var hasRookA1Moved = false
var hasRookA8Moved = false
var hasRookH1Moved = false
var hasRookH8Moved = false
fun canPawnMove(from: Square, to: Square) : Boolean {
    val space = abs(from.row - to.row)
    var lastMoveSpace = abs(lastMove.lastMoveStarting.row - lastMove.lastMoveEnding.row)
    if (ChessGame.pieceAt(from)?.player == Player.WHITE) {
        if(from.col == to.col && Clear.isClearVerticallyPawn(from, to))
        {
            if(from.row == 1)
            {
                return to.row == 2 || to.row == 3
            }
            else if(to.row > from.row && space == 1)
            {
                return true
            }
        } //Capturing
        else if((from.col + 1 == to.col || from.col - 1 == to.col) && from.row + 1 == to.row
            && ChessGame.pieceAt(to.col, to.row) != null)
        {
            return true
        } //en passant
        else if(lastMoveSpace == 2 && lastMove.PieceType == Types.PAWN && turn % 2 ==0)
        {
            if((lastMove.lastMoveEnding.col + 1 == from.col ||
                        lastMove.lastMoveEnding.col - 1 == from.col) &&
                lastMove.lastMoveEnding.row == from.row)
            {
                if(to.col == lastMove.lastMoveEnding.col &&
                    to.row == lastMove.lastMoveEnding.row + 1)
                {
                    chessArray.remove(ChessGame.pieceAt(to.col, to.row - 1))
                    return true
                }
            }
        }
    }
    else {
        if(from.col == to.col && Clear.isClearVerticallyPawn(from, to))
        {
            if(from.row == 6)
            {
                return to.row == 5 || to.row == 4
            }
            else if(space == 1 && to.row < from.row){
                return true
            }
        } //capturing
        else if((from.col + 1 == to.col || from.col - 1 == to.col) && from.row - 1 == to.row
            && ChessGame.pieceAt(to.col, to.row) != null) {
            return true
        } //en passant
        else if(lastMoveSpace == 2 && lastMove.PieceType == Types.PAWN && turn % 2 == 1)
        {
            if((lastMove.lastMoveEnding.col + 1 == from.col ||
                        lastMove.lastMoveEnding.col - 1 == from.col) &&
                lastMove.lastMoveEnding.row == from.row)
            {
                if(to.col == lastMove.lastMoveEnding.col &&
                    to.row == lastMove.lastMoveEnding.row - 1)
                {
                    chessArray.remove(ChessGame.pieceAt(to.col, to.row + 1))
                    return true
                }
            }
        }
    }
    return false
}

fun canKnightMove(from: Square, to: Square): Boolean {
    if((abs(from.col - to.col) == 2 && abs(from.row - to.row) == 1))
    {
        return true
    }
    else if ((abs(from.col - to.col) == 1 && abs(from.row - to.row) == 2))
    {
        return true
    }
    return false
}

fun canRookMove(from: Square, to: Square): Boolean {
    if((from.col == to.col && Clear.isClearVertically(from, to)
                || from.row == to.row && Clear.isClearHorizontally(from, to)))
    {
        if(from.col == 0 && from.row == 0)
        {
            hasRookA1Moved = true
        }
        else if(from.col == 0 && from.row == 7)
        {
            hasRookA8Moved = true
        }
        else if(from.col == 7 && from.row == 0)
        {
            hasRookH1Moved = true
        }
        else
        {
            hasRookH8Moved = true
        }
        return true
    }
    return false
}

fun canBishopMove(from: Square, to: Square): Boolean {
    if ((abs(from.col - to.col) == abs(from.row - to.row))) {
        return Clear.isClearDiagonally(from, to)
    }
    return false
}

fun canQueenMove(from: Square, to: Square) : Boolean {
    if((canBishopMove(from, to) || canRookMove(from, to))){
        return true
    }
    return false
}

fun canKingMove(from: Square, to: Square) : Boolean {
    var gap = abs(from.col - to.col)
    lateinit var Rook : Piece
    if(abs(from.col - to.col) <= 1 && abs(from.row - to.row) <= 1) {
        val movingPiece = ChessGame.pieceAt(from)
        when(movingPiece?.player)
        {
            Player.WHITE -> hasWhiteKingMoved = true
            Player.BLACK -> hasBlackKingMoved = true
        }
        return true
    } //Castling
    else if(gap == 2 && !hasWhiteKingMoved &&
        Clear.isClearHorizontally(from, to))
    {
        if(to.col == 2 && to.row == 0 && !hasRookA1Moved &&
            ChessGame.pieceAt(from)?.player == Player.WHITE)
        {
            hasWhiteKingMoved = true
            chessArray.remove(ChessGame.pieceAt(0,0))
            chessArray.add(Piece(3, 0, Player.WHITE, Types.ROOK, R.drawable.rook_white))
            return true
        }
        else if (to.col == 6 && to.row == 0 && !hasRookH1Moved &&
            ChessGame.pieceAt(from)?.player == Player.WHITE)
        {
            hasWhiteKingMoved = true
            chessArray.remove(ChessGame.pieceAt(7,0))
            chessArray.add(Piece(5, 0, Player.WHITE, Types.ROOK, R.drawable.rook_white))
            return true
        }
    }
    else if(gap == 2 && !hasBlackKingMoved &&
        Clear.isClearHorizontally(from, to))
    {
        if(to.col == 2 && to.row == 7 && !hasRookA8Moved &&
            ChessGame.pieceAt(from)?.player == Player.BLACK)
        {
            hasBlackKingMoved = true
            chessArray.remove(ChessGame.pieceAt(0,7))
            chessArray.add(Piece(3, 7, Player.BLACK, Types.ROOK, R.drawable.rook_black))
            return true
        }
        else if (to.col == 6 && to.row == 7 && !hasRookH8Moved &&
            ChessGame.pieceAt(from)?.player == Player.BLACK)
        {
            hasBlackKingMoved = true
            chessArray.remove(ChessGame.pieceAt(7,7))
            chessArray.add(Piece(5, 7, Player.BLACK, Types.ROOK, R.drawable.rook_black))
            return true
        }
    }
    return false
}