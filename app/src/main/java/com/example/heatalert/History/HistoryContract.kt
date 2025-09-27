package com.example.heatalert.History

import com.example.heatalert.Sensors.SensorLog
interface HistoryContract {

    interface View {
        fun showSensorLogs(logs: List<SensorLog>)
        fun showLoading()
        fun hideLoading()
        fun showError(message: String)
        fun navigateToSettings()
        fun showTemperatureTrendDetails()
    }

    interface Presenter {
        fun loadSensorLogs()
        fun onSearchTextChanged(searchText: String)
        fun onFilterChanged(filterType: String)
        fun onSettingsClicked()
        fun onTemperatureTrendClicked()
        fun onDestroy()
    }

    interface Model {
        fun getSensorLogs(callback: OnDataLoadedCallback<List<SensorLog>>)
        fun searchLogs(query: String, callback: OnDataLoadedCallback<List<SensorLog>>)
        fun filterLogs(filterType: String, callback: OnDataLoadedCallback<List<SensorLog>>)
    }

    interface OnDataLoadedCallback<T> {
        fun onSuccess(data: T)
        fun onError(error: String)
    }
}