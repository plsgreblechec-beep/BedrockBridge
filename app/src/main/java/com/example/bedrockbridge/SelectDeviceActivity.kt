package com.example.bedrockbridge

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

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
            setPadding(40, 40, 40, 40)
        }

        // --- БЛОК ПК ---
        val tvPc = TextView(this).apply {
            text = "Железо ПК: ${DeviceGenerator.getCurrentPC(this@SelectDeviceActivity)}"
            setTextColor(Color.GRAY)
            textSize = 14f
            gravity = Gravity.CENTER
        }
        val btnPcConnect = Button(this).apply {
            text = "ПК"
            textSize = 20f
            setBackgroundColor(accentColor)
            setTextColor(Color.WHITE)
            setOnClickListener { goToGame(ip, nick, 7, DeviceGenerator.getCurrentPC(this@SelectDeviceActivity)) }
        }
        val btnPcChange = Button(this).apply {
            text = "Заменить железо ПК"
            textSize = 12f
            setBackgroundColor(Color.DKGRAY)
            setTextColor(Color.WHITE)
            setOnClickListener {
                DeviceGenerator.changePC(this@SelectDeviceActivity)
                Toast.makeText(this@SelectDeviceActivity, "ПК железо изменено", Toast.LENGTH_SHORT).show()
                recreate() // Обновляем экран, чтобы показать новое железо
            }
        }

        // --- БЛОК ТЕЛЕФОНА ---
        val tvPhone = TextView(this).apply {
            text = "Железо Телефона: ${DeviceGenerator.getCurrentPhone(this@SelectDeviceActivity)}"
            setTextColor(Color.GRAY)
            textSize = 14f
            gravity = Gravity.CENTER
            setPadding(0, 40, 0, 0)
        }
        val btnPhoneConnect = Button(this).apply {
            text = "ТЕЛЕФОН"
            textSize = 20f
            setBackgroundColor(accentColor)
            setTextColor(Color.WHITE)
            setOnClickListener { goToGame(ip, nick, 1, DeviceGenerator.getCurrentPhone(this@SelectDeviceActivity)) }
        }
        val btnPhoneChange = Button(this).apply {
            text = "Заменить железо Телефона"
            textSize = 12f
            setBackgroundColor(Color.DKGRAY)
            setTextColor(Color.WHITE)
            setOnClickListener {
                DeviceGenerator.changePhone(this@SelectDeviceActivity)
                Toast.makeText(this@SelectDeviceActivity, "Железо телефона изменено", Toast.LENGTH_SHORT).show()
                recreate()
            }
        }

        // --- БЛОК КОНСОЛИ ---
        val tvConsole = TextView(this).apply {
            text = "Железо Консоли: ${DeviceGenerator.getCurrentConsole(this@SelectDeviceActivity)}"
            setTextColor(Color.GRAY)
            textSize = 14f
            gravity = Gravity.CENTER
            setPadding(0, 40, 0, 0)
        }
        val btnConsoleConnect = Button(this).apply {
            text = "КОНСОЛЬ"
            textSize = 20f
            setBackgroundColor(accentColor)
            setTextColor(Color.WHITE)
            setOnClickListener { goToGame(ip, nick, 13, DeviceGenerator.getCurrentConsole(this@SelectDeviceActivity)) }
        }
        val btnConsoleChange = Button(this).apply {
            text = "Заменить железо Консоли"
            textSize = 12f
            setBackgroundColor(Color.DKGRAY)
            setTextColor(Color.WHITE)
            setOnClickListener {
                DeviceGenerator.changeConsole(this@SelectDeviceActivity)
                Toast.makeText(this@SelectDeviceActivity, "Железо консоли изменено", Toast.LENGTH_SHORT).show()
                recreate()
            }
        }

        // Добавляем все на экран
        layout.addView(tvPc)
        layout.addView(btnPcConnect)
        layout.addView(btnPcChange)

        layout.addView(tvPhone)
        layout.addView(btnPhoneConnect)
        layout.addView(btnPhoneChange)

        layout.addView(tvConsole)
        layout.addView(btnConsoleConnect)
        layout.addView(btnConsoleChange)

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
