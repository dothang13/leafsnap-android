package fxc.dev.app.ui.calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fxc.dev.app.R
import java.util.Date

data class CalendarDay(
    val dayNumber: String,
    val date: Date,
    val isCurrentMonth: Boolean,
    val isSelected: Boolean
)

class CalendarAdapter(
    private var days: List<CalendarDay>,
    private val onDayClick: (CalendarDay) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_day, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(days[position])
    }

    override fun getItemCount(): Int = days.size

    fun submitList(newDays: List<CalendarDay>) {
        days = newDays
        notifyDataSetChanged()
    }

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDayNumber: TextView = itemView.findViewById(R.id.tvDayNumber)

        fun bind(day: CalendarDay) {
            tvDayNumber.text = day.dayNumber

            if (day.isSelected) {
                // Selected day: green circle, white text
                tvDayNumber.setBackgroundResource(R.drawable.bg_calendar_selected_day)
                tvDayNumber.setTextColor(Color.WHITE)
            } else {
                tvDayNumber.background = null
                if (day.isCurrentMonth) {
                    // Current month active days: dark grey text
                    tvDayNumber.setTextColor(Color.parseColor("#333333"))
                } else {
                    // Neighboring months: light grey text
                    tvDayNumber.setTextColor(Color.parseColor("#CCCCCC"))
                }
            }

            itemView.setOnClickListener {
                onDayClick(day)
            }
        }
    }
}
