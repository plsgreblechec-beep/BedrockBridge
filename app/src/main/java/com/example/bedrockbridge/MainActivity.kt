package com.example.bedrockbridge

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import bridge.Bridge
import bridge.BridgeConfig
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etIp = findViewById<EditText>(R.id.etIp)
        val etNick = findViewById<EditText>(R.id.etNick)
        val tvStatus = findViewById<TextView>(R.id.tvStatus)

        findViewById<Button>(R.id.btnPc).setOnClickListener {
            val device = DeviceGenerator.getRandomPC()
            connect(etIp.text.toString(), etNick.text.toString(), 7, device, tvStatus)
        }

        findViewById<Button>(R.id.btnPhone).setOnClickListener {
            val device = DeviceGenerator.getRandomPhone()
            connect(etIp.text.toString(), etNick.text.toString(), 1, device, tvStatus)
        }

        findViewById<Button>(R.id.btnConsole).setOnClickListener {
            val device = DeviceGenerator.getRandomConsole()
            connect(etIp.text.toString(), etNick.text.toString(), 13, device, tvStatus)
        }
    }

    private fun connect(ip: String, nick: String, os: Int, model: String, tv: TextView) {
        if (ip.isEmpty() || nick.isEmpty()) {
            Toast.makeText(this, "Заполните поля!", Toast.LENGTH_SHORT).show()
            return
        }
        tv.text = "Подключение как $model..."
        
        thread {
            val res = Bridge.connectToServer(BridgeConfig(ip, nick, os, model))
            runOnUiThread {
                tv.text = res
                if (res == "Успешно подключено!") {
                    val intent = Intent(this, GameActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}
