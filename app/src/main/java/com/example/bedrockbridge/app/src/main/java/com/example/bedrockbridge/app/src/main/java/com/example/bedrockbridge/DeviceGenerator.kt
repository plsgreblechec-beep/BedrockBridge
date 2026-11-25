package com.example.bedrockbridge

object DeviceGenerator {
    private val pcModels = listOf("Windows 10", "Windows 11", "ASUS ROG", "MSI Katana", "Lenovo Legion")
    private val phoneModels = listOf("Pixel 7", "Samsung S23", "Xiaomi 13", "OnePlus 11")
    private val consoleModels = listOf("Xbox One", "Xbox Series X", "PlayStation 5")

    fun getRandomPC() = pcModels.random()
    fun getRandomPhone() = phoneModels.random()
    fun getRandomConsole() = consoleModels.random()
}
