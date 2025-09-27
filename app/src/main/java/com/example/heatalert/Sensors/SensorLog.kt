package com.example.heatalert.Sensors


data class SensorLog(
    val date: String,
    val time: String,
    val temperature: String,
    val smoke: String,
    val flame: String,
    val logType: LogType,
    // Additional detailed sensor data
    val humidity: String = generateHumidity(),
    val smokePPM: String = generateSmokePPM(smoke),
    val location: String = generateLocation(),
    val sensorId: String = generateSensorId()
) {
    enum class LogType {
        NORMAL, WARNING, CRITICAL
    }

    val smokeLevel: String get() = smoke

    companion object {
        private fun generateHumidity(): String {
            val humidityValues = listOf("35%", "42%", "45%", "48%", "52%", "38%", "41%")
            return humidityValues.random()
        }

        private fun generateSmokePPM(smoke: String): String {
            return when (smoke) {
                "None" -> "0 ppm"
                "Low" -> "${(5..20).random()} ppm"
                "Medium" -> "${(25..50).random()} ppm"
                "High" -> "${(60..100).random()} ppm"
                else -> "0 ppm"
            }
        }

        private fun generateLocation(): String {
            val locations = listOf("Kitchen", "Living Room", "Bedroom", "Garage", "Basement", "Attic")
            return locations.random()
        }

        private fun generateSensorId(): String {
            val ids = listOf("HS-001", "HS-002", "HS-003", "HS-004", "HS-005")
            return ids.random()
        }
    }
}
