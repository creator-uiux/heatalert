package com.example.heatalert.Sensors

data class SensorDataModel(
    val temperature: Float,
    val smokeLevel: Int,
    val flameStatus: String,
    val lastUpdate: String
)
