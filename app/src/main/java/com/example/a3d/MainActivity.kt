package com.example.a3d

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.a3d.flappyShorrs.FlappyMainActivity
import com.example.a3d.flyingShorrs.FlyingMainActivity
import com.example.a3d.shorrChess.ChessMainActivity
import com.example.a3d.ticTacShorr.TikTakShorrActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var flappy : Button = findViewById(R.id.button)
        var chess : Button = findViewById(R.id.button2)
        var flying : Button = findViewById(R.id.button3)
        var tiktac : Button = findViewById(R.id.button4)
        flappy.setOnClickListener {
            val intent = Intent(this, FlappyMainActivity::class.java)
            this.startActivity(intent)
        }
        flying.setOnClickListener{
            val intent = Intent(this, FlyingMainActivity::class.java)
            this.startActivity(intent)
        }
        tiktac.setOnClickListener{
            val intent = Intent(this, TikTakShorrActivity::class.java)
            this.startActivity(intent)
        }
        chess.setOnClickListener {
            val intent = Intent(this, ChessMainActivity::class.java)
            this.startActivity(intent)
        }
    }
}