package com.example.heatalert.Home

import com.example.heatalert.model.SensorDataModel
import com.example.heatalert.model.SensorDetailModel

interface HomeView {
    fun displaySensorData(data: SensorDataModel)
    fun showSensorDetail(sensorDetail: SensorDetailModel)
    fun showError(message: String)
}