package com.example.heatalert.model

data class SensorDataModel(
    val temperature: Float,
    val smokeLevel: Int,
    val flameStatus: String,
    val lastUpdate: String
)
