package com.example.heatalert.Home

import com.example.heatalert.Sensors.SensorDataModel
import com.example.heatalert.Sensors.SensorDetailModel

interface HomeView {
    fun displaySensorData(data: SensorDataModel)
    fun showSensorDetail(sensorDetail: SensorDetailModel)
    fun showError(message: String)
}