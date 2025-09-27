package com.example.heatalert.Home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.heatalert.Sensors.SensorDataModel
import com.example.heatalert.Sensors.SensorDetailModel
import com.example.heatalert.Login.LoginActivity
import com.example.heatalert.R
import com.google.android.material.navigation.NavigationView
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.example.heatalert.Alert.AlertActivity
import com.example.heatalert.History.HistoryActivity

class HomeActivity : AppCompatActivity(), HomeView, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var presenter: HomePresenter
    private lateinit var tvTemperature: TextView
    private lateinit var tvSmoke: TextView
    private lateinit var tvFlame: TextView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        presenter = HomePresenter(this, this)

        // Initialize drawer components
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        setupNavigationDrawer()
        // Initialize views
        tvTemperature = findViewById(R.id.tvTemperature)
        tvSmoke = findViewById(R.id.tvSmoke)
        tvFlame = findViewById(R.id.tvFlame)

        // Set up hamburger menu click
        findViewById<ImageView>(R.id.ivBurger).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Set up close drawer button in header
        val navHeaderView = navigationView.getHeaderView(0)
        navHeaderView.findViewById<ImageView>(R.id.ivCloseDrawer).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

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

        // Set user info in navigation header
        setNavigationHeaderInfo()
    }

    private fun setNavigationHeaderInfo() {
        val headerView = navigationView.getHeaderView(0)
        val tvUserName = headerView.findViewById<TextView>(R.id.tvUserName)
        val tvUserEmail = headerView.findViewById<TextView>(R.id.tvUserEmail)
        val tvUserAvatar = headerView.findViewById<TextView>(R.id.tvUserAvatar)

        // Set user information (you can get this from SharedPreferences or user session)
        tvUserName.text = "John Smith"
        tvUserEmail.text = "johnsmith@gmail.com"
        tvUserAvatar.text = "JS"
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_dashboard -> {
                // Already on dashboard, just close drawer
                Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_alerts -> {
                // Navigate to Alerts activity
                Toast.makeText(this, "Alerts", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AlertActivity::class.java))
            }
            R.id.nav_history -> {
                // Navigate to History activity
                Toast.makeText(this, "com/example/heatalert/History", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HistoryActivity::class.java))
            }
            R.id.nav_settings -> {
                // Navigate to Settings activity
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                // startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.nav_profile -> {
                // Navigate to Profile activity
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                // startActivity(Intent(this, ProfileActivity::class.java))
            }
            R.id.nav_logout -> {
                // Show logout confirmation dialog
                showLogoutDialog()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                // Clear user session and navigate to login
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
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
        when (sensorDetail.sensorType) {
            "Temperature" -> {
                dialogView.findViewById<TextView>(R.id.tvTempValue)?.text = sensorDetail.value
            }
            "Smoke" -> {
                dialogView.findViewById<TextView>(R.id.tvSmokeValue)?.text = sensorDetail.value
            }
            "Flame" -> {
                dialogView.findViewById<TextView>(R.id.tvFlameValue)?.text = sensorDetail.value
            }
        }

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        // Set up close buttons
        dialogView.findViewById<ImageView>(R.id.ivClose)?.setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<android.widget.Button>(R.id.btnClose)?.setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<android.widget.Button>(R.id.btnViewHistory)?.setOnClickListener {
            Toast.makeText(this, "View History for ${sensorDetail.sensorType}", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }
    private fun setupNavigationDrawer() {
        // Remove icon tinting to show original colors
        navigationView.itemIconTintList = null

        // Set text colors
        val textColors = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf()
            ),
            intArrayOf(
                ContextCompat.getColor(this, android.R.color.holo_orange_dark), // Selected
                ContextCompat.getColor(this, android.R.color.black)              // Normal
            )
        )
        navigationView.itemTextColor = textColors
    }
    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}