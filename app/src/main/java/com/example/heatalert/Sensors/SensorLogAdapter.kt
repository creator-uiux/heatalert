package com.example.heatalert.Sensors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.heatalert.R

class SensorLogAdapter(
    private val onItemClick: (SensorLog) -> Unit = {}
) : RecyclerView.Adapter<SensorLogAdapter.ViewHolder>() {

    private var sensorLogs = mutableListOf<SensorLog>()

    fun updateLogs(logs: List<SensorLog>) {
        sensorLogs.clear()
        sensorLogs.addAll(logs)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sensor_log, parent, false)
        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sensorLogs[position])
    }

    override fun getItemCount(): Int = sensorLogs.size

    class ViewHolder(
        itemView: View,
        private val onItemClick: (SensorLog) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        private val tvTemp: TextView = itemView.findViewById(R.id.tvTemp)
        private val tvSmoke: TextView = itemView.findViewById(R.id.tvSmoke)
        private val tvFlame: TextView = itemView.findViewById(R.id.tvFlame)
        private val itemBackground: View = itemView.findViewById(R.id.itemBackground)

        fun bind(log: SensorLog) {
            tvDate.text = log.date
            tvTime.text = log.time
            tvTemp.text = log.temperature
            tvSmoke.text = log.smoke
            tvFlame.text = log.flame

            // Set click listener
            itemView.setOnClickListener {
                onItemClick(log)
            }

            // Add ripple effect for better UX
            itemView.isClickable = true
            itemView.isFocusable = true
            itemView.background = ContextCompat.getDrawable(
                itemView.context,
                android.R.drawable.list_selector_background
            )

            // Set colors and background based on log type
            when (log.logType) {
                SensorLog.LogType.NORMAL -> {
                    itemBackground.setBackgroundColor(
                        ContextCompat.getColor(itemView.context, android.R.color.white)
                    )
                    tvSmoke.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.green_safe)
                    )
                    tvFlame.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.green_safe)
                    )
                }
                SensorLog.LogType.WARNING -> {
                    itemBackground.setBackgroundColor(
                        ContextCompat.getColor(itemView.context, R.color.light_yellow)
                    )
                    tvSmoke.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.orange_warning)
                    )
                    tvFlame.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.orange_warning)
                    )
                }
                SensorLog.LogType.CRITICAL -> {
                    itemBackground.setBackgroundColor(
                        ContextCompat.getColor(itemView.context, R.color.light_pink)
                    )
                    tvSmoke.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.red_danger)
                    )
                    tvFlame.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.red_danger)
                    )
                }
            }
        }
    }
}