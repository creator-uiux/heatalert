package com.example.heatalert.view

import com.example.heatalert.model.SensorData
import com.example.heatalert.model.SensorDetail

interface HomeView {
    fun displaySensorData(data: SensorData)
    fun showSensorDetail(sensorDetail: SensorDetail)
    fun showError(message: String)
}