package com.example.a3d.ticTacShorr

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.a3d.R

class TikTakShorrActivity : AppCompatActivity() {
    var xturn = true
    lateinit var grid : List<List<ImageButton>>
    lateinit var checked : List<MutableList<Int>>
    lateinit var text : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiktakshorr)
        var homeButton: ImageView = findViewById(R.id.homeButton)
        homeButton.setOnClickListener {
            finish()
        }

        var b1: ImageButton = findViewById(R.id.b1)
        var b2: ImageButton = findViewById(R.id.b2)
        var b3: ImageButton = findViewById(R.id.b3)
        var b4: ImageButton = findViewById(R.id.b4)
        var b5: ImageButton = findViewById(R.id.b5)
        var b6: ImageButton = findViewById(R.id.b6)
        var b7: ImageButton = findViewById(R.id.b7)
        var b8: ImageButton = findViewById(R.id.b8)
        var b9: ImageButton = findViewById(R.id.b9)
        text = findViewById(R.id.textView4)
        grid = listOf(listOf(b1, b2, b3), listOf(b4, b5, b6), listOf(b7, b8, b9))
        for (y in 0..2) {
            for (x in 0..2) {
                grid[y][x].setOnClickListener {
                    click(grid[y][x], y, x)
                }
            }
        }
        startGame()
    }
    fun startGame(){
        checked = listOf(mutableListOf(0,0,0),mutableListOf(0,0,0),mutableListOf(0,0,0))
        grid.forEach {
            it.forEach {
                it.setImageResource(0)
            }
        }
        text.text = "Tic-Tac-Shorr"
    }
    fun click(b : ImageButton, y : Int, x : Int){
        if(checked[y][x] != 0) return
        if(xturn) {
            b.setImageResource(android.R.drawable.presence_offline)
            checked[y][x] = 1
        }
        else{
            b.setImageResource(android.R.drawable.presence_invisible)
            checked[y][x] = 2
        }
        xturn = !xturn
        checkWin(1)
        checkWin(2)
    }
    fun checkWin(p : Int){
        for(y in 0..2){
            var winning = true
            for(x in 0..2){
                if(checked[y][x] != p){
                    winning = false
                }
            }
            if(winning) win(p)
        }
        for(x in 0..2){
            var winning = true
            for(y in 0..2){
                if(checked[y][x] != p){
                    winning = false
                }
            }
            if(winning) win(p)
        }
        if((checked[0][0]==p && checked[1][1]==p && checked[2][2]==p) ||
            (checked[2][0]==p&&checked[1][1]==p && checked[0][2]==p)) win(p)
    }
    fun win(p : Int){
        if(p == 1){
            text.text = "Player 1 Wins!"
        }
        else{
            text.text = "Player 2 Wins!"
        }
        Handler(Looper.getMainLooper()).postDelayed({
            startGame()
        }, 2000)
    }
}