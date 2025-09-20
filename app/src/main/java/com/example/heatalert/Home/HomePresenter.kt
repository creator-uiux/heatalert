package com.example.heatalert.Home

import android.content.Context
import com.example.heatalert.model.SensorDataModel
import com.example.heatalert.model.SensorDetailModel

class HomePresenter(private val view: HomeView, private val context: Context) {

    fun loadSensorData() {
        try {
            // Mock sensor data - replace with actual sensor readings
            val sensorData = SensorDataModel(
                temperature = 23.9f,
                smokeLevel = 12,
                flameStatus = "Clear",
                lastUpdate = "09:42 AM"
            )
            view.displaySensorData(sensorData)
        } catch (e: Exception) {
            view.showError("Failed to load sensor data: ${e.message}")
        }
    }

    fun onTemperatureClicked() {
        val temperatureDetail = SensorDetailModel(
            sensorId = "TS-001",
            sensorType = "Temperature",
            status = "Online",
            lastReading = "03:58 AM",
            location = "Kitchen Area",
            battery = 95,
            accuracy = 98,
            value = "25.7°C"
        )
        view.showSensorDetail(temperatureDetail)
    }

    fun onSmokeClicked() {
        val smokeDetail = SensorDetailModel(
            sensorId = "SS-001",
            sensorType = "Smoke",
            status = "Online",
            lastReading = "04:02 AM",
            location = "Kitchen Area",
            battery = 95,
            accuracy = 96,
            value = "11%"
        )
        view.showSensorDetail(smokeDetail)
    }

    fun onFlameClicked() {
        val flameDetail = SensorDetailModel(
            sensorId = "FS-002",
            sensorType = "Flame",
            status = "Online",
            lastReading = "04:02 AM",
            location = "Kitchen Area",
            battery = 92,
            accuracy = 99,
            value = "Clear"
        )
        view.showSensorDetail(flameDetail)
    }
}