package fxc.dev.app.ui.tasks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import fxc.dev.app.databinding.ActivityTasksBinding
import fxc.dev.app.ui.base.BaseActivity
import fxc.dev.app.ui.calendar.CalendarActivity

@AndroidEntryPoint
class TasksActivity : BaseActivity<ActivityTasksBinding, TasksVM>() {
    override val viewModel: TasksVM by viewModels()

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivityTasksBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnTasksBack.setOnClickListener { finish() }
        binding.btnTasksSort.setOnClickListener { showToast("Opening Sort & Filter...") }

        // Top right calendar icon opens CalendarActivity without premium restriction
        binding.btnTasksCalendar.setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
        }

        // Tab Segment Switcher (Today / Upcoming)
        binding.tabToday.setOnClickListener {
            selectTab(isTodaySelected = true)
        }

        binding.tabUpcoming.setOnClickListener {
            selectTab(isTodaySelected = false)
        }
    }

    private fun selectTab(isTodaySelected: Boolean) {
        val selectedColor = android.graphics.Color.parseColor("#333333")
        val unselectedColor = android.graphics.Color.parseColor("#888888")

        if (isTodaySelected) {
            binding.tabToday.setTextColor(selectedColor)
            binding.tabToday.setBackgroundResource(fxc.dev.app.R.drawable.bg_white_pill)
            binding.tabToday.elevation = 4f

            binding.tabUpcoming.setTextColor(unselectedColor)
            binding.tabUpcoming.setBackgroundColor(android.graphics.Color.TRANSPARENT)
            binding.tabUpcoming.elevation = 0f

            binding.tvBannerText.text = "You have no task today"
        } else {
            binding.tabUpcoming.setTextColor(selectedColor)
            binding.tabUpcoming.setBackgroundResource(fxc.dev.app.R.drawable.bg_white_pill)
            binding.tabUpcoming.elevation = 4f

            binding.tabToday.setTextColor(unselectedColor)
            binding.tabToday.setBackgroundColor(android.graphics.Color.TRANSPARENT)
            binding.tabToday.elevation = 0f

            binding.tvBannerText.text = "You have no upcoming task"
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
