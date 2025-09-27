package com.example.heatalert.Sensors

data class SensorDetailModel(
    val sensorId: String,
    val sensorType: String,
    val status: String,
    val lastReading: String,
    val location: String,
    val battery: Int,
    val accuracy: Int,
    val value: String
)