package com.example.bedrockbridge

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import bridge.Bridge
import kotlin.concurrent.thread

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

        val ip = intent.getStringExtra("IP") ?: ""
        val nick = intent.getStringExtra("NICK") ?: ""
        val os = intent.getIntExtra("OS", 1)
        val model = intent.getStringExtra("MODEL") ?: ""

        window.decorView.setBackgroundColor(Color.BLACK)

        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
        }

        val circleLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER // Выравниваем содержимое по центру
            val drawable = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setStroke(10, Color.WHITE)
                setColor(Color.TRANSPARENT)
            }
            background = drawable
            layoutParams = LinearLayout.LayoutParams(700, 700)
            setOnClickListener { finish() }
        }

        tvTimer = TextView(this).apply {
            text = "00:00"
            textSize = 48f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER // Центрируем текст
        }

        circleLayout.addView(tvTimer)
        mainLayout.addView(circleLayout)
        setContentView(mainLayout)

        handler.post(tick)

        Toast.makeText(this, "Подключение...", Toast.LENGTH_SHORT).show()
        thread {
            val res = Bridge.connectToServer(ip, nick, os, model)
            runOnUiThread {
                if (res != "Успешно подключено!") {
                    Toast.makeText(this, res, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroy() {
        handler.removeCallbacks(tick)
        super.onDestroy()
    }
}
