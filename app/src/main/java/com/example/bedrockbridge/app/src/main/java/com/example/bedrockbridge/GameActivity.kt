package com.example.bedrockbridge

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class GameActivity : Activity() {
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

        // Создаем интерфейс вручную, без R.id
        val layout = LinearLayout(this).apply { 
            orientation = LinearLayout.VERTICAL
            setPadding(50, 50, 50, 50)
        }
        
        tvTimer = TextView(this).apply { 
            text = "00:00"
            textSize = 24f 
        }
        
        val btnJump = Button(this).apply { text = "Прыжок" }
        val btnAttack = Button(this).apply { text = "Удар" }
        val btnExit = Button(this).apply { text = "Выход" }
        
        layout.addView(tvTimer)
        layout.addView(btnJump)
        layout.addView(btnAttack)
        layout.addView(btnExit)
        
        setContentView(layout)

        handler.post(tick)

        // Обработчики нажатий для наших кнопок
        btnJump.setOnClickListener { Toast.makeText(this, "Прыжок", Toast.LENGTH_SHORT).show() }
        btnAttack.setOnClickListener { Toast.makeText(this, "Удар", Toast.LENGTH_SHORT).show() }
        btnExit.setOnClickListener { finish() }
    }

    override fun onDestroy() {
        handler.removeCallbacks(tick)
        super.onDestroy()
    }
}
