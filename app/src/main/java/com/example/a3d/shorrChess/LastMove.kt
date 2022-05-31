package com.example.a3d.shorrChess

data class LastMove(
    var lastMoveStarting: Square,
    var lastMoveEnding: Square,
    var Player: Player,
    var PieceType: Types
)