package com.example.bedrockbridge

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private lateinit var tvTimer: TextView
    private var seconds = 0
    private val handler = Handler(Looper.getMainLooper())

    private val tick = object : Runnable {
        override fun run() {
            seconds++
            tvTimer.text = "%02d:%02d".format(seconds / 60, seconds % 60)
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        tvTimer = findViewById(R.id.tvTimer)
        handler.post(tick)

        findViewById<Button>(R.id.btnExit).setOnClickListener { finish() }
        findViewById<Button>(R.id.btnJump).setOnClickListener { Toast.makeText(this, "Прыжок", Toast.LENGTH_SHORT).show() }
        findViewById<Button>(R.id.btnAttack).setOnClickListener { Toast.makeText(this, "Удар", Toast.LENGTH_SHORT).show() }
    }

    override fun onDestroy() {
        handler.removeCallbacks(tick)
        super.onDestroy()
    }
}
