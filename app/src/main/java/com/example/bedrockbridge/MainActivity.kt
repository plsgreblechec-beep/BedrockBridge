package com.example.bedrockbridge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import bridge.Bridge
import kotlin.concurrent.thread

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 50, 50, 50)
        }

        val etIp = EditText(this).apply { hint = "IP сервера" }
        val etNick = EditText(this).apply { hint = "Ваш ник" }
        val btnPc = Button(this).apply { text = "🖥 Подключиться как ПК" }
        val btnPhone = Button(this).apply { text = "📱 Подключиться как Телефон" }
        val btnConsole = Button(this).apply { text = "🎮 Подключиться как Консоль" }
        val tvStatus = TextView(this).apply { text = "Статус: Ожидание" }

        layout.addView(etIp)
        layout.addView(etNick)
        layout.addView(btnPc)
        layout.addView(btnPhone)
        layout.addView(btnConsole)
        layout.addView(tvStatus)

        setContentView(layout)

        btnPc.setOnClickListener {
            connect(etIp.text.toString(), etNick.text.toString(), 7, DeviceGenerator.getRandomPC(), tvStatus)
        }
        btnPhone.setOnClickListener {
            connect(etIp.text.toString(), etNick.text.toString(), 1, DeviceGenerator.getRandomPhone(), tvStatus)
        }
        btnConsole.setOnClickListener {
            connect(etIp.text.toString(), etNick.text.toString(), 13, DeviceGenerator.getRandomConsole(), tvStatus)
        }
    }

    private fun connect(ip: String, nick: String, os: Int, model: String, tv: TextView) {
        if (ip.isEmpty() || nick.isEmpty()) {
            Toast.makeText(this, "Заполните поля!", Toast.LENGTH_SHORT).show()
            return
        }
        tv.text = "Подключение как $model..."
        
        thread {
            try {
                // Вызываем функцию напрямую с 4 аргументами
                val res = Bridge.connectToServer(ip, nick, os, model)
                runOnUiThread {
                    tv.text = res
                    if (res == "Успешно подключено!") {
                        val intent = Intent(this, GameActivity::class.java)
                        startActivity(intent)
                    }
                }
            } catch (e: Exception) {
                runOnUiThread { tv.text = "Ошибка: ${e.message}" }
            }
        }
    }
}
