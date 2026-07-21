package fxc.dev.app.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import fxc.dev.app.databinding.ActivityCalendarBinding
import fxc.dev.app.ui.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class CalendarActivity : BaseActivity<ActivityCalendarBinding, CalendarVM>() {
    override val viewModel: CalendarVM by viewModels()

    private lateinit var adapter: CalendarAdapter
    private val calendar = Calendar.getInstance()
    private var selectedDate = Calendar.getInstance().time

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivityCalendarBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        setupToolbar()
        setupCalendarView()
        rebuildCalendarDays()
        updateSelectedDateDetails()
    }

    private fun setupToolbar() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupCalendarView() {
        adapter = CalendarAdapter(emptyList()) { day ->
            selectedDate = day.date
            updateSelectedDateDetails()
            rebuildCalendarDays()
        }
        binding.rvCalendarDays.adapter = adapter

        binding.btnPrevMonth.setOnClickListener {
            changeMonth(-1)
        }

        binding.btnNextMonth.setOnClickListener {
            changeMonth(1)
        }
    }

    private fun changeMonth(amount: Int) {
        calendar.add(Calendar.MONTH, amount)
        rebuildCalendarDays()
    }

    private fun rebuildCalendarDays() {
        val tempCal = Calendar.getInstance().apply {
            time = calendar.time
            set(Calendar.DAY_OF_MONTH, 1)
        }

        val firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK)
        val daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH)

        // Previous month padding
        val prevDaysCount = firstDayOfWeek - 1
        val prevCal = Calendar.getInstance().apply {
            time = tempCal.time
            add(Calendar.MONTH, -1)
        }
        val prevDaysInMonth = prevCal.getActualMaximum(Calendar.DAY_OF_MONTH)

        val list = mutableListOf<CalendarDay>()

        // 1. Previous month days (padding)
        for (i in (prevDaysInMonth - prevDaysCount + 1)..prevDaysInMonth) {
            prevCal.set(Calendar.DAY_OF_MONTH, i)
            list.add(CalendarDay(i.toString(), prevCal.time, false, isSameDate(prevCal.time, selectedDate)))
        }

        // 2. Current month days
        for (i in 1..daysInMonth) {
            tempCal.set(Calendar.DAY_OF_MONTH, i)
            list.add(CalendarDay(i.toString(), tempCal.time, true, isSameDate(tempCal.time, selectedDate)))
        }

        // 3. Next month days (padding to complete 6 weeks grid, 42 items)
        val nextCal = Calendar.getInstance().apply {
            time = tempCal.time
            add(Calendar.MONTH, 1)
        }
        val remaining = 42 - list.size
        for (i in 1..remaining) {
            nextCal.set(Calendar.DAY_OF_MONTH, i)
            list.add(CalendarDay(i.toString(), nextCal.time, false, isSameDate(nextCal.time, selectedDate)))
        }

        adapter.submitList(list)

        // Update month year text
        val sdf = SimpleDateFormat("MMMM yyyy", Locale.US)
        binding.tvMonthYear.text = sdf.format(calendar.time)
    }

    private fun updateSelectedDateDetails() {
        val sdf = SimpleDateFormat("EEEE, d MMM, yyyy", Locale.US)
        binding.tvSelectedDateDetails.text = sdf.format(selectedDate)
    }

    private fun isSameDate(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance().apply { time = date1 }
        val cal2 = Calendar.getInstance().apply { time = date2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
    }
}
