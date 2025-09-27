package com.example.heatalert.History

import com.example.heatalert.Sensors.SensorLog

class HistoryPresenter(
    private var view: HistoryContract.View?,
    private val model: HistoryContract.Model
) : HistoryContract.Presenter {

    private var currentFilter = "All Events"
    private var currentSearchText = ""

    override fun loadSensorLogs() {
        view?.showLoading()
        model.getSensorLogs(object : HistoryContract.OnDataLoadedCallback<List<SensorLog>> {
            override fun onSuccess(data: List<SensorLog>) {
                view?.hideLoading()
                view?.showSensorLogs(data)
            }

            override fun onError(error: String) {
                view?.hideLoading()
                view?.showError(error)
            }
        })
    }

    override fun onSearchTextChanged(searchText: String) {
        currentSearchText = searchText
        performSearch()
    }

    override fun onFilterChanged(filterType: String) {
        currentFilter = filterType
        performFilter()
    }

    private fun performSearch() {
        if (currentSearchText.isEmpty()) {
            loadSensorLogs()
            return
        }

        model.searchLogs(currentSearchText, object : HistoryContract.OnDataLoadedCallback<List<SensorLog>> {
            override fun onSuccess(data: List<SensorLog>) {
                view?.showSensorLogs(data)
            }

            override fun onError(error: String) {
                view?.showError(error)
            }
        })
    }

    private fun performFilter() {
        model.filterLogs(currentFilter, object : HistoryContract.OnDataLoadedCallback<List<SensorLog>> {
            override fun onSuccess(data: List<SensorLog>) {
                view?.showSensorLogs(data)
            }

            override fun onError(error: String) {
                view?.showError(error)
            }
        })
    }

    override fun onSettingsClicked() {
        view?.navigateToSettings()
    }

    override fun onTemperatureTrendClicked() {
        view?.showTemperatureTrendDetails()
    }

    override fun onDestroy() {
        view = null
    }
}