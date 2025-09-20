package com.example.heatalert.model

data class SensorDetail(
    val sensorId: String,
    val sensorType: String,
    val status: String,
    val lastReading: String,
    val location: String,
    val battery: Int,
    val accuracy: Int,
    val value: String
)