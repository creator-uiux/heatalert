package com.example.heatalert.Sensors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.heatalert.R

class SensorLogExpandableAdapter : RecyclerView.Adapter<SensorLogExpandableAdapter.ViewHolder>() {

    private var sensorLogs = mutableListOf<SensorLog>()
    private var expandedPosition = -1 // Track which item is expanded

    fun updateLogs(logs: List<SensorLog>) {
        sensorLogs.clear()
        sensorLogs.addAll(logs)
        expandedPosition = -1 // Reset expanded state when updating data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sensor_log_expandable, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sensorLogs[position], position == expandedPosition)
    }

    override fun getItemCount(): Int = sensorLogs.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        private val tvTemp: TextView = itemView.findViewById(R.id.tvTemp)
        private val tvSmoke: TextView = itemView.findViewById(R.id.tvSmoke)
        private val tvFlame: TextView = itemView.findViewById(R.id.tvFlame)
        private val mainRow: View = itemView.findViewById(R.id.mainRow)
        private val expandedDetails: View = itemView.findViewById(R.id.expandedDetails)
        private val tvHumidity: TextView = itemView.findViewById(R.id.tvHumidity)
        private val tvSmokePPM: TextView = itemView.findViewById(R.id.tvSmokePPM)
        private val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        private val tvSensorId: TextView = itemView.findViewById(R.id.tvSensorId)

        fun bind(log: SensorLog, isExpanded: Boolean) {
            // Bind main row data
            tvDate.text = log.date
            tvTime.text = log.time
            tvTemp.text = log.temperature
            tvSmoke.text = log.smoke
            tvFlame.text = log.flame

            // Bind detailed data
            tvHumidity.text = log.humidity
            tvSmokePPM.text = log.smokePPM
            tvLocation.text = log.location
            tvSensorId.text = log.sensorId

            // Set click listener for expansion
            itemView.setOnClickListener {
                val previousExpandedPosition = expandedPosition

                if (expandedPosition == adapterPosition) {
                    // Collapse if clicking the same item
                    expandedPosition = -1
                } else {
                    // Expand new item
                    expandedPosition = adapterPosition
                }

                // Notify changes with animation
                if (previousExpandedPosition != -1) {
                    notifyItemChanged(previousExpandedPosition)
                }
                notifyItemChanged(adapterPosition)
            }

            // Show/hide expanded section
            expandedDetails.visibility = if (isExpanded) View.VISIBLE else View.GONE

            // Set colors and background based on log type and expansion state
            when (log.logType) {
                SensorLog.LogType.NORMAL -> {
                    val backgroundColor = if (isExpanded)
                        ContextCompat.getColor(itemView.context, R.color.gray_light)
                    else
                        ContextCompat.getColor(itemView.context, android.R.color.white)

                    mainRow.setBackgroundColor(backgroundColor)
                    tvSmoke.setTextColor(ContextCompat.getColor(itemView.context, R.color.green_safe))
                    tvFlame.setTextColor(ContextCompat.getColor(itemView.context, R.color.green_safe))
                }
                SensorLog.LogType.WARNING -> {
                    val backgroundColor = if (isExpanded)
                        ContextCompat.getColor(itemView.context, R.color.light_yellow)
                    else
                        ContextCompat.getColor(itemView.context, R.color.light_yellow)

                    mainRow.setBackgroundColor(backgroundColor)
                    tvSmoke.setTextColor(ContextCompat.getColor(itemView.context, R.color.orange_warning))
                    tvFlame.setTextColor(ContextCompat.getColor(itemView.context, R.color.orange_warning))
                }
                SensorLog.LogType.CRITICAL -> {
                    val backgroundColor = if (isExpanded)
                        ContextCompat.getColor(itemView.context, R.color.light_pink)
                    else
                        ContextCompat.getColor(itemView.context, R.color.light_pink)

                    mainRow.setBackgroundColor(backgroundColor)
                    tvSmoke.setTextColor(ContextCompat.getColor(itemView.context, R.color.red_danger))
                    tvFlame.setTextColor(ContextCompat.getColor(itemView.context, R.color.red_danger))
                }
            }

            // Add subtle visual feedback for expanded state
            if (isExpanded) {
                mainRow.elevation = 4f
            } else {
                mainRow.elevation = 0f
            }
        }
    }
}