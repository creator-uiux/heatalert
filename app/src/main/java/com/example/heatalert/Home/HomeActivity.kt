package com.example.heatalert.Home

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.heatalert.R
import com.example.heatalert.model.SensorDataModel
import com.example.heatalert.model.SensorDetailModel

class HomeActivity : AppCompatActivity(), HomeView {

    private lateinit var presenter: HomePresenter
    private lateinit var tvTemperature: TextView
    private lateinit var tvSmoke: TextView
    private lateinit var tvFlame: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home) // Your main layout

        presenter = HomePresenter(this, this)

        // Initialize views
        tvTemperature = findViewById(R.id.tvTemperature)
        tvSmoke = findViewById(R.id.tvSmoke)
        tvFlame = findViewById(R.id.tvFlame)

        // Set click listeners for sensor cards
        findViewById<LinearLayout>(R.id.temperatureCard).setOnClickListener {
            presenter.onTemperatureClicked()
        }

        findViewById<LinearLayout>(R.id.smokeCard).setOnClickListener {
            presenter.onSmokeClicked()
        }

        findViewById<LinearLayout>(R.id.flameCard).setOnClickListener {
            presenter.onFlameClicked()
        }

        // Load initial sensor data
        presenter.loadSensorData()
    }

    override fun displaySensorData(data: SensorDataModel) {
        tvTemperature.text = "${data.temperature}°C"
        tvSmoke.text = "${data.smokeLevel}%"
        tvFlame.text = data.flameStatus
    }

    override fun showSensorDetail(sensorDetail: SensorDetailModel) {
        // Create and show modal dialog
        val dialogView = layoutInflater.inflate(
            when (sensorDetail.sensorType) {
                "Temperature" -> R.layout.temperature_detail_modal
                "Smoke" -> R.layout.smoke_detail_modal
                "Flame" -> R.layout.flame_detail_modal
                else -> R.layout.temperature_detail_modal
            }, null
        )

        // Populate dialog with sensor data
        dialogView.findViewById<TextView>(R.id.tvTempValue)?.text = sensorDetail.value
        // Add other field updates as needed

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        // Set up close button
        dialogView.findViewById<android.widget.ImageView>(R.id.ivClose)?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}