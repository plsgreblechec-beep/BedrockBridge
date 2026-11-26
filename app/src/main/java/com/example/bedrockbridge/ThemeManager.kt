package com.example.bedrockbridge

import android.content.Context
import android.graphics.Color

object ThemeManager {
    private const val PREFS_NAME = "theme_prefs"
    private const val KEY_COLOR = "accent_color"

    // Цвета на выбор
    val colors = listOf(
        "Зеленый" to Color.parseColor("#4CAF50"),
        "Синий" to Color.parseColor("#2196F3"),
        "Красный" to Color.parseColor("#F44336"),
        "Фиолетовый" to Color.parseColor("#9C27B0"),
        "Оранжевый" to Color.parseColor("#FF9800")
    )

    fun getAccentColor(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_COLOR, Color.parseColor("#4CAF50")) // По умолчанию зеленый
    }

    fun setAccentColor(context: Context, color: Int) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(KEY_COLOR, color).apply()
    }
}
