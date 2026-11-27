package com.example.bedrockbridge

import android.content.Context

object DeviceGenerator {
    private const val PREFS_NAME = "hardware_prefs"

    private val pcModels = listOf("Windows 10", "Windows 11", "ASUS ROG", "MSI Katana", "Lenovo Legion", "Windows 10 Pro")
    private val phoneModels = listOf("Pixel 7", "Samsung Galaxy S23", "Xiaomi 13", "OnePlus 11", "Sony Xperia 1")
    private val consoleModels = listOf("Xbox One", "Xbox Series X", "PlayStation 5", "Nintendo Switch")

    // Получаем текущее сохраненное устройство (или ставим по умолчанию)
    fun getCurrentPC(context: Context): String = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString("pc", "Windows 10") ?: "Windows 10"
    fun getCurrentPhone(context: Context): String = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString("phone", "Pixel 7") ?: "Pixel 7"
    fun getCurrentConsole(context: Context): String = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString("console", "Xbox One") ?: "Xbox One"

    // Меняем устройство и сохраняем
    fun changePC(context: Context) { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().putString("pc", pcModels.random()).apply() }
    fun changePhone(context: Context) { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().putString("phone", phoneModels.random()).apply() }
    fun changeConsole(context: Context) { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().putString("console", consoleModels.random()).apply() }
}
