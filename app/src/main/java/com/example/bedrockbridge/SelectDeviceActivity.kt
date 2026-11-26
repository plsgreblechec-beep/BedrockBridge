package com.example.bedrockbridge

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout

class SelectDeviceActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.setBackgroundColor(Color.BLACK)

        val ip = intent.getStringExtra("IP") ?: ""
        val nick = intent.getStringExtra("NICK") ?: ""
        val accentColor = ThemeManager.getAccentColor(this)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(50, 50, 50, 50)
        }

        val btnPc = Button(this).apply {
            text = "🖥 ПК"
            textSize = 20f
            setBackgroundColor(accentColor)
            setTextColor(Color.WHITE)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 250
            ).apply { setMargins(0, 20, 0, 20) }
            setOnClickListener { goToGame(ip, nick, 7, DeviceGenerator.getRandomPC()) }
        }

        val btnPhone = Button(this).apply {
            text = "📱 Телефон"
            textSize = 20f
            setBackgroundColor(accentColor)
            setTextColor(Color.WHITE)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 250
            ).apply { setMargins(0, 20, 0, 20) }
            setOnClickListener { goToGame(ip, nick, 1, DeviceGenerator.getRandomPhone()) }
        }

        val btnConsole = Button(this).apply {
            text = "🎮 Консоль"
            textSize = 20f
            setBackgroundColor(accentColor)
            setTextColor(Color.WHITE)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 250
            ).apply { setMargins(0, 20, 0, 20) }
            setOnClickListener { goToGame(ip, nick, 13, DeviceGenerator.getRandomConsole()) }
        }

        layout.addView(btnPc)
        layout.addView(btnPhone)
        layout.addView(btnConsole)
        setContentView(layout)
    }

    private fun goToGame(ip: String, nick: String, os: Int, model: String) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("IP", ip)
        intent.putExtra("NICK", nick)
        intent.putExtra("OS", os)
        intent.putExtra("MODEL", model)
        startActivity(intent)
    }
}
