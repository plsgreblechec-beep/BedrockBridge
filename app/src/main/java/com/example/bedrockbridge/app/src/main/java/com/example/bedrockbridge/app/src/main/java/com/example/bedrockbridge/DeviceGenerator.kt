package com.example.bedrockbridge

import android.content.Context

object DeviceGenerator {
    private const val PREFS_NAME = "hardware_prefs"

    // Только актуальные модели ПК (2023-2024)
    private val pcModels = listOf(
        "Windows 11", 
        "Windows 10 Pro", 
        "ASUS ROG Strix G16", 
        "MSI Katana 15", 
        "Lenovo Legion Pro 5", 
        "Acer Nitro V15", 
        "HP Omen 16"
    )
    
    // Только новые телефоны
    private val phoneModels = listOf(
        "Pixel 8 Pro", 
        "Samsung Galaxy S24 Ultra", 
        "Xiaomi 14 Pro", 
        "OnePlus 12", 
        "Sony Xperia 1 V"
    )
    
    // Актуальные консоли
    private val consoleModels = listOf(
        "Xbox Series X", 
        "PlayStation 5", 
        "Nintendo Switch OLED"
    )

    fun getCurrentPC(context: Context): String = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString("pc", "Windows 11") ?: "Windows 11"
    fun getCurrentPhone(context: Context): String = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString("phone", "Pixel 8 Pro") ?: "Pixel 8 Pro"
    fun getCurrentConsole(context: Context): String = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString("console", "PlayStation 5") ?: "PlayStation 5"

    fun changePC(context: Context) { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().putString("pc", pcModels.random()).apply() }
    fun changePhone(context: Context) { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().putString("phone", phoneModels.random()).apply() }
    fun changeConsole(context: Context) { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().putString("console", consoleModels.random()).apply() }
}
