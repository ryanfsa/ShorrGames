package com.example.a3d.shorrChess

import kotlin.math.abs

object Clear {
    fun isClearVertically(from: Square, to: Square): Boolean {
        if (from.col != to.col) return false
        val gap = abs(from.row - to.row) - 1
        if (gap == 0 ) return true
        for (i in 1..gap) {
            val nextRow = if (to.row > from.row) from.row + i else from.row - i
            if (ChessGame.pieceAt(Square(from.col, nextRow)) != null) {
                return false
            }
        }
        return true
    }

    fun isClearVerticallyPawn(from: Square, to: Square): Boolean {
        if (from.col != to.col) return false
        val gap = abs(from.row - to.row)
        for (i in 1..gap) {
            val nextRow = if (to.row > from.row) from.row + i else from.row - i
            if (ChessGame.pieceAt(Square(from.col, nextRow)) != null) {
                return false
            }
        }
        return true
    }

    fun isClearHorizontally(from: Square, to: Square): Boolean{
        if(from.row != to.row) return false
        val gap = abs(from.col - to.col) - 1
        if(gap == 0) return true
        for (i in 1..gap)
        {
            val nextCol = if (to.col > from.col) from.col + i else from.col - i
            if (ChessGame.pieceAt(Square(nextCol, from.row)) != null) {
                return false
            }
        }
        return true
    }

    fun isClearDiagonally(from: Square, to: Square): Boolean {
        if (abs(from.col - to.col) != abs(from.row - to.row)) return false
        val gap = abs(from.col - to.col) - 1
        for (i in 1..gap) {
            val nextCol = if (to.col > from.col) from.col + i else from.col - i
            val nextRow = if (to.row > from.row) from.row + i else from.row - i
            if (ChessGame.pieceAt(nextCol, nextRow) != null) {
                return false
            }
        }
        return true
    }
}