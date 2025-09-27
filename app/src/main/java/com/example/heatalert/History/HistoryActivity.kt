package com.example.heatalert.History

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heatalert.Alert.AlertActivity
import com.example.heatalert.Home.HomeActivity
import com.example.heatalert.Login.LoginActivity
import com.example.heatalert.R
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AlertDialog
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.example.heatalert.Sensors.SensorLogAdapter
import com.example.heatalert.Sensors.SensorLogExpandableAdapter
import com.example.heatalert.Sensors.SensorLog
class HistoryActivity : AppCompatActivity(), HistoryContract.View, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var presenter: HistoryContract.Presenter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var ivBurger: ImageView
    private lateinit var ivSettings: ImageView
    private lateinit var etSearch: EditText
    private lateinit var spinnerFilter: Spinner
    private lateinit var recyclerViewLogs: RecyclerView
    private lateinit var adapter: SensorLogExpandableAdapter
    private lateinit var temperatureTrendCard: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        initViews()
        setupNavigationDrawer()
        setupPresenter()
        setupListeners()
        setupRecyclerView()
        setupFilterSpinner()

        presenter.loadSensorLogs()
    }

    private fun initViews() {
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        ivBurger = findViewById(R.id.ivBurger)
        ivSettings = findViewById(R.id.ivSettings)
        etSearch = findViewById(R.id.etSearch)
        spinnerFilter = findViewById(R.id.spinnerFilter)
        recyclerViewLogs = findViewById(R.id.recyclerViewLogs)
        temperatureTrendCard = findViewById(R.id.temperatureTrendCard)
    }

    private fun setupNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(this)

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

        // Set current item as selected
        navigationView.setCheckedItem(R.id.nav_history)

        // Set user info in navigation header
        setNavigationHeaderInfo()
    }

    private fun setNavigationHeaderInfo() {
        val headerView = navigationView.getHeaderView(0)
        val tvUserName = headerView.findViewById<TextView>(R.id.tvUserName)
        val tvUserEmail = headerView.findViewById<TextView>(R.id.tvUserEmail)
        val tvUserAvatar = headerView.findViewById<TextView>(R.id.tvUserAvatar)

        // Set user information (you can get this from SharedPreferences or user session)
        tvUserName?.text = "John Smith"
        tvUserEmail?.text = "johnsmith@gmail.com"
        tvUserAvatar?.text = "JS"

        // Set up close drawer button in header
        headerView.findViewById<ImageView>(R.id.ivCloseDrawer)?.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_dashboard -> {
                // Navigate back to Home/Dashboard
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
            }
            R.id.nav_alerts -> {
                // Navigate to Alerts activity
                val intent = Intent(this, AlertActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
            }
            R.id.nav_history -> {
                // Already on history, just close drawer
                Toast.makeText(this, "Already on History", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_settings -> {
                // Navigate to Settings activity (when created)
                Toast.makeText(this, "Settings - Coming Soon", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_profile -> {
                // Navigate to Profile activity (when created)
                Toast.makeText(this, "Profile - Coming Soon", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
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
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
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

    private fun setupPresenter() {
        val model = HistoryModel()
        presenter = HistoryPresenter(this, model)
    }

    private fun setupListeners() {
        // Burger menu opens drawer
        ivBurger.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        ivSettings.setOnClickListener {
            presenter.onSettingsClicked()
        }

        temperatureTrendCard.setOnClickListener {
            presenter.onTemperatureTrendClicked()
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.onSearchTextChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupRecyclerView() {
        adapter = SensorLogExpandableAdapter()  // No click listener needed
        recyclerViewLogs.layoutManager = LinearLayoutManager(this)
        recyclerViewLogs.adapter = adapter
    }

    private fun setupFilterSpinner() {
        val filterOptions = arrayOf(
            "All Events",
            "High Temperature",
            "Smoke Detected",
            "Flame Events",
            "Critical Alerts"
        )

        val spinnerAdapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, filterOptions
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFilter.adapter = spinnerAdapter

        spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.onFilterChanged(filterOptions[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    // Contract implementation methods
    override fun showSensorLogs(logs: List<SensorLog>) {
        adapter.updateLogs(logs)
    }

    override fun showLoading() {
        // Show loading indicator
    }

    override fun hideLoading() {
        // Hide loading indicator
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToSettings() {
        Toast.makeText(this, "Settings - Coming Soon", Toast.LENGTH_SHORT).show()
    }

    override fun showTemperatureTrendDetails() {
        Toast.makeText(this, "Temperature Trend Details", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}