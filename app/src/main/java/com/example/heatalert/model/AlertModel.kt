package com.example.heatalert.model

import com.example.heatalert.R

// Data class representing an Alert
data class Alert(
    val id: String,
    val type: AlertType,
    val title: String,
    val priority: AlertPriority,
    val timestamp: String,
    val suggestion: String,
    val isActive: Boolean = true
) {
    fun getIconResId(): Int {
        return when (type) {
            AlertType.SMOKE -> R.drawable.ic_warning
            AlertType.TEMPERATURE -> R.drawable.ic_thermostat
            AlertType.GAS -> R.drawable.ic_gas
            AlertType.FLAME -> R.drawable.ic_fire
        }
    }

    fun getPriorityText(): String {
        return when (priority) {
            AlertPriority.LOW -> "Low Priority"
            AlertPriority.MEDIUM -> "Medium Priority"
            AlertPriority.HIGH -> "High Priority"
            AlertPriority.CRITICAL -> "Critical Priority"
        }
    }
}

// Enum for alert types
enum class AlertType {
    SMOKE, TEMPERATURE, GAS, FLAME
}

// Enum for alert priorities
enum class AlertPriority {
    LOW, MEDIUM, HIGH, CRITICAL
}
