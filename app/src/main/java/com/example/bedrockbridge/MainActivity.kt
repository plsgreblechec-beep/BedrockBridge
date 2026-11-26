package com.example.bedrockbridge

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import android.view.View

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        window.decorView.setBackgroundColor(Color.BLACK)

        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 50, 50, 50)
        }

        // Шапка с шестеренкой
        val headerLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.END
        }
        
        val btnSettings = ImageButton(this).apply {
            setImageResource(R.drawable.ic_settings)
            setBackgroundColor(Color.TRANSPARENT)
            setColorFilter(Color.WHITE)
            setOnClickListener { showColorPicker() }
        }
        headerLayout.addView(btnSettings)
        mainLayout.addView(headerLayout)

        val etIp = EditText(this).apply { hint = "IP сервера"; setTextColor(Color.WHITE); setHintTextColor(Color.GRAY) }
        val etPort = EditText(this).apply { hint = "Порт (19132)"; setTextColor(Color.WHITE); setHintTextColor(Color.GRAY) }
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

    private fun showColorPicker() {
        val builder = AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)
        builder.setTitle("Выберите цвет кнопок")

        val colorLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(20, 40, 20, 40)
        }

        // Берем цвета из ThemeManager
        for (colorPair in ThemeManager.colors) {
            val colorInt = colorPair.second
            
            // Используем View вместо Button (чтобы не было вылетов)
            val colorCircle = View(this).apply {
                val drawable = GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    setColor(colorInt)
                }
                background = drawable
                
                // Размер кружка 80x80
                layoutParams = LinearLayout.LayoutParams(80, 80).apply {
                    setMargins(10, 0, 10, 0)
                }
                
                setOnClickListener {
                    ThemeManager.setAccentColor(this@MainActivity, colorInt)
                    recreate() // Обновляем экран
                }
            }
            colorLayout.addView(colorCircle)
        }

        builder.setView(colorLayout)
        builder.setNegativeButton("Отмена", null)
        builder.show()
    }
}
