package com.example.a3d.shorrChess

interface ChessDelegate {
    fun pieceAt(square: Square) : Piece?
    fun movePiece(from: Square, to: Square)
}