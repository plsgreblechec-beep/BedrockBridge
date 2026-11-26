package com.example.bedrockbridge

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Черная тема
        window.decorView.setBackgroundColor(Color.BLACK)

        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 50, 50, 50)
        }

        // Шапка с кнопкой настроек
        val headerLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.END
        }
        
        val btnSettings = Button(this).apply {
            text = "⚙"
            textSize = 24f
            setBackgroundColor(Color.TRANSPARENT)
            setTextColor(Color.WHITE)
            setOnClickListener { showSettingsDialog() }
        }
        
        headerLayout.addView(btnSettings)
        mainLayout.addView(headerLayout)

        // Поля ввода
        val etIp = EditText(this).apply { hint = "IP сервера"; setTextColor(Color.WHITE); setHintTextColor(Color.GRAY) }
        val etPort = EditText(this).apply { hint = "Порт (по умолч. 19132)"; setTextColor(Color.WHITE); setHintTextColor(Color.GRAY) }
        val etNick = EditText(this).apply { hint = "Ваш ник"; setTextColor(Color.WHITE); setHintTextColor(Color.GRAY) }
        
        val accentColor = ThemeManager.getAccentColor(this)

        val btnNext = Button(this).apply {
            text = "Далее ➡"
            textSize = 20f
            setBackgroundColor(accentColor)
            setTextColor(Color.WHITE)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 150
            ).apply { setMargins(0, 50, 0, 0) }
            
            setOnClickListener {
                var ip = etIp.text.toString().trim()
                val port = etPort.text.toString().trim()
                val nick = etNick.text.toString().trim()

                if (ip.isEmpty() || nick.isEmpty()) {
                    Toast.makeText(this@MainActivity, "Заполните IP и ник!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (port.isNotEmpty() && !ip.contains(":")) {
                    ip = "$ip:$port"
                }

                val intent = Intent(this@MainActivity, SelectDeviceActivity::class.java)
                intent.putExtra("IP", ip)
                intent.putExtra("NICK", nick)
                startActivity(intent)
            }
        }

        mainLayout.addView(etIp)
        mainLayout.addView(etPort)
        mainLayout.addView(etNick)
        mainLayout.addView(btnNext)

        setContentView(mainLayout)
    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)
        builder.setTitle("Выберите цвет темы")
        
        val colorNames = ThemeManager.colors.map { it.first }.toTypedArray()
        builder.setItems(colorNames) { _, which ->
            val selectedColor = ThemeManager.colors[which].second
            ThemeManager.setAccentColor(this, selectedColor)
            recreate() // Перезагружаем экран, чтобы применить цвет
        }
        
        builder.setNegativeButton("Отмена", null)
        builder.show()
    }
}
