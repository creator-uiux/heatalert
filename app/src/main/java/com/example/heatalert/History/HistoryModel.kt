package com.example.heatalert.History


import android.os.Handler
import android.os.Looper
import com.example.heatalert.Sensors.SensorLog
import java.util.concurrent.Executors

class HistoryModel : HistoryContract.Model {

    private val executor = Executors.newFixedThreadPool(2)
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun getSensorLogs(callback: HistoryContract.OnDataLoadedCallback<List<SensorLog>>) {
        executor.execute {
            try {
                // Simulate API call or database query
                val logs = generateSampleData()

                mainHandler.post {
                    callback.onSuccess(logs)
                }
            } catch (e: Exception) {
                mainHandler.post {
                    callback.onError(e.message ?: "Unknown error occurred")
                }
            }
        }
    }

    override fun searchLogs(query: String, callback: HistoryContract.OnDataLoadedCallback<List<SensorLog>>) {
        executor.execute {
            try {
                val allLogs = generateSampleData()
                val filteredLogs = allLogs.filter { log ->
                    log.date.contains(query, ignoreCase = true) ||
                            log.time.contains(query, ignoreCase = true) ||
                            log.temperature.contains(query, ignoreCase = true) ||
                            log.smoke.contains(query, ignoreCase = true) ||
                            log.flame.contains(query, ignoreCase = true)
                }

                mainHandler.post {
                    callback.onSuccess(filteredLogs)
                }
            } catch (e: Exception) {
                mainHandler.post {
                    callback.onError(e.message ?: "Search failed")
                }
            }
        }
    }

    override fun filterLogs(filterType: String, callback: HistoryContract.OnDataLoadedCallback<List<SensorLog>>) {
        executor.execute {
            try {
                val allLogs = generateSampleData()
                val filteredLogs = when (filterType) {
                    "All Events" -> allLogs
                    "High Temperature" -> allLogs.filter { log ->
                        log.smokeLevel == "High" ||
                                log.temperature.replace("°C", "").toIntOrNull()?.let { it > 35 } == true
                    }
                    "Smoke Detected" -> allLogs.filter { log ->
                        log.smoke !in listOf("None", "Low")
                    }
                    "Flame Events" -> allLogs.filter { log ->
                        log.flame in listOf("Detected", "Flicker")
                    }
                    "Critical Alerts" -> allLogs.filter { log ->
                        (log.smokeLevel == "High" || log.flame == "Detected") &&
                                log.temperature.replace("°C", "").toIntOrNull()?.let { it > 40 } == true
                    }
                    else -> allLogs
                }

                mainHandler.post {
                    callback.onSuccess(filteredLogs)
                }
            } catch (e: Exception) {
                mainHandler.post {
                    callback.onError(e.message ?: "Filter failed")
                }
            }
        }
    }

    private fun generateSampleData(): List<SensorLog> {
        return listOf(
            SensorLog("06/18", "10:25 AM", "32°C", "Low", "None", SensorLog.LogType.NORMAL),
            SensorLog("06/18", "01:40 PM", "35°C", "Medium", "Flicker", SensorLog.LogType.WARNING),
            SensorLog("06/19", "11:15 AM", "29°C", "None", "None", SensorLog.LogType.NORMAL),
            SensorLog("06/20", "02:50 PM", "42°C", "High", "Detected", SensorLog.LogType.CRITICAL),
            SensorLog("06/21", "09:00 AM", "34°C", "Low", "None", SensorLog.LogType.NORMAL),
            SensorLog("06/22", "03:30 PM", "37°C", "Medium", "Flicker", SensorLog.LogType.WARNING),
            SensorLog("06/23", "11:30 AM", "55°C", "High", "Detected", SensorLog.LogType.CRITICAL)
        )
    }
}