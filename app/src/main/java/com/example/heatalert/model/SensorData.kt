package com.example.heatalert.model

data class SensorData(
    val temperature: Float,
    val smokeLevel: Int,
    val flameStatus: String,
    val lastUpdate: String
)
