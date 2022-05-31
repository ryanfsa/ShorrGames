package com.example.a3d.shorrChess


import android.graphics.drawable.Drawable
import com.example.a3d.shorrChess.Types

data class Piece(
    val col: Int,
    val row: Int,
    val player: Player,
    val types: Types,
    val resID: Int
) {}