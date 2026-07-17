package fxc.dev.app.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import fxc.dev.app.databinding.ActivityMainBinding
import fxc.dev.app.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainVM>() {
    override val viewModel: MainVM by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            Toast.makeText(
                this,
                "Notification permission is granted = $isGranted",
                Toast.LENGTH_SHORT
            ).show()
        }

    private val takePicturePreviewLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            if (bitmap != null) {
                showToast("Photo captured successfully!")
                binding.ivDiagnosePlant.setImageBitmap(bitmap)
            } else {
                showToast("Photo capture cancelled.")
            }
        }

    override fun setupViewBinding(inflater: LayoutInflater) = ActivityMainBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        window.setBackgroundDrawableResource(fxc.dev.app.R.drawable.default_background)
        askNotificationPermission()
        setupBottomNavigation()
        setupExploreTabs()
        setupClickListeners()
        setupDiagnoseTabLogic()
        startPremiumCountdown()
    }

    // Bottom Navigation Logic
    private fun setupBottomNavigation() {
        val activeColor = ContextCompat.getColor(this, fxc.dev.app.R.color.colorPrimary)
        val inactiveColor = android.graphics.Color.parseColor("#A0A0A0")

        val menuItems = listOf(
            Triple(binding.btnHome, binding.ivHome, binding.tvHome),
            Triple(binding.btnMyPlant, binding.ivMyPlant, binding.tvMyPlant),
            Triple(binding.btnDiagnoseTab, binding.ivDiagnoseTab, binding.tvDiagnoseTab),
            Triple(binding.btnMore, binding.ivMore, binding.tvMore)
        )

        binding.btnHome.setOnClickListener {
            selectNavigationItem(0, menuItems, activeColor, inactiveColor)
            binding.layoutHomeContent.visibility = android.view.View.VISIBLE
            binding.layoutMyPlantsContent.visibility = android.view.View.GONE
            binding.layoutDiagnoseContent.visibility = android.view.View.GONE
        }

        binding.btnMyPlant.setOnClickListener {
            selectNavigationItem(1, menuItems, activeColor, inactiveColor)
            binding.layoutHomeContent.visibility = android.view.View.GONE
            binding.layoutMyPlantsContent.visibility = android.view.View.VISIBLE
            binding.layoutDiagnoseContent.visibility = android.view.View.GONE
        }

        binding.btnDiagnoseTab.setOnClickListener {
            selectNavigationItem(2, menuItems, activeColor, inactiveColor)
            binding.layoutHomeContent.visibility = android.view.View.GONE
            binding.layoutMyPlantsContent.visibility = android.view.View.GONE
            binding.layoutDiagnoseContent.visibility = android.view.View.VISIBLE
            binding.layoutDiagnoseMain.visibility = android.view.View.VISIBLE
            binding.layoutDiagnoseSearch.visibility = android.view.View.GONE
            binding.etSearchDiseases.setText("")
        }

        binding.btnMore.setOnClickListener {
            selectNavigationItem(3, menuItems, activeColor, inactiveColor)
            binding.layoutHomeContent.visibility = android.view.View.GONE
            binding.layoutMyPlantsContent.visibility = android.view.View.GONE
            binding.layoutDiagnoseContent.visibility = android.view.View.GONE
            showToast("Navigation: More")
        }

        binding.fabCamera.setOnClickListener {
            takePicturePreviewLauncher.launch(null)
        }
    }

    private fun selectNavigationItem(
        selectedIndex: Int,
        items: List<Triple<android.view.View, ImageView, TextView>>,
        activeColor: Int,
        inactiveColor: Int
    ) {
        // Active/Inactive Icons Map
        val activeIcons = listOf(
            fxc.dev.app.R.drawable.ic_home_active_v2,
            fxc.dev.app.R.drawable.ic_my_plant_active_v2,
            fxc.dev.app.R.drawable.ic_diagnose_active_v2,
            fxc.dev.app.R.drawable.ic_more_active_v2
        )
        val inactiveIcons = listOf(
            fxc.dev.app.R.drawable.ic_home_inactive_v2,
            fxc.dev.app.R.drawable.ic_my_plant_inactive_v2,
            fxc.dev.app.R.drawable.ic_diagnose_inactive_v2,
            fxc.dev.app.R.drawable.ic_more_inactive_v2
        )

        val dots = listOf(
            binding.activeDotHome,
            binding.activeDotMyPlant,
            binding.activeDotDiagnose,
            binding.activeDotMore
        )

        for (i in items.indices) {
            val isSelected = i == selectedIndex
            val item = items[i]
            item.second.setImageResource(if (isSelected) activeIcons[i] else inactiveIcons[i])
            item.third.setTextColor(if (isSelected) activeColor else inactiveColor)
            dots[i].visibility = if (isSelected) android.view.View.VISIBLE else android.view.View.INVISIBLE
        }
    }

    // Explore Tabs Logic
    private fun setupExploreTabs() {
        val activeBgColor = ContextCompat.getColor(this, fxc.dev.app.R.color.colorPrimary)
        val inactiveBgColor = android.graphics.Color.parseColor("#F2F2F7")
        val activeTextColor = android.graphics.Color.WHITE
        val inactiveTextColor = android.graphics.Color.parseColor("#555555")

        val tabs = listOf(binding.tabSeedling, binding.tabCareGuides, binding.tabHousePlants)

        tabs.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                tabs.forEachIndexed { idx, tv ->
                    val isSelected = idx == index
                    tv.setBackgroundResource(fxc.dev.app.R.drawable.default_background)
                    tv.backgroundTintList = android.content.res.ColorStateList.valueOf(if (isSelected) activeBgColor else inactiveBgColor)
                    tv.setTextColor(if (isSelected) activeTextColor else inactiveTextColor)
                }
                showToast("Explore Tab: " + textView.text)
            }
        }
    }

    // Click Listeners for Mock interactions
    private fun setupClickListeners() {
        binding.btnProfileDropdown.setOnClickListener { showToast("Opening profile details...") }
        binding.cardStats.setOnClickListener { showToast("Opening plant statistics...") }
        
        binding.actionCalendar.setOnClickListener { showToast("Opening Calendar...") }
        binding.actionDiagnose.setOnClickListener { showToast("Opening Diagnose Camera...") }
        binding.actionTasks.setOnClickListener { showToast("Opening Tasks Manager...") }
        binding.actionIdentify.setOnClickListener { showToast("Opening Leaf Identifier...") }

        binding.cardPremium.setOnClickListener { showToast("Redirecting to Premium Billing...") }
        binding.cardSearch.setOnClickListener { showToast("Opening Search bar...") }

        binding.catFlowers.setOnClickListener { showToast("Category: Flowers") }
        binding.catVegetables.setOnClickListener { showToast("Category: Vegetables") }
        binding.catCacti.setOnClickListener { showToast("Category: Cacti & Succulents") }
        binding.catTree.setOnClickListener { showToast("Category: Tree") }

        binding.cardGettingStarted.setOnClickListener { showToast("Opening Getting Started tutorial...") }
        
        binding.toolWaterCalculator.setOnClickListener { showToast("Opening Water Calculator...") }
        binding.toolSmartFinder.setOnClickListener { showToast("Opening Smart Finder...") }
        
        binding.btnExploreMore.setOnClickListener { showToast("Exploring more articles...") }

        // My Plants screen listeners
        binding.btnMyPlantsHistory.setOnClickListener { showToast("Opening History...") }
        binding.btnMyPlantsSort.setOnClickListener { showToast("Opening Sort Options...") }
        binding.btnMyPlantsViewMode.setOnClickListener { showToast("Toggling View Mode...") }
        binding.btnCloudSync.setOnClickListener { showToast("Syncing with cloud...") }
        binding.btnCreateCollection.setOnClickListener { showToast("Creating new collection...") }
        binding.btnAddPlant.setOnClickListener { showToast("Adding new plant...") }
    }

    // Timer countdown logic
    private var timeRemainingSeconds = 5 * 60 + 48 // 5 minutes 48 seconds
    private val timerHandler = android.os.Handler(android.os.Looper.getMainLooper())
    private val timerRunnable = object : Runnable {
        override fun run() {
            if (timeRemainingSeconds > 0) {
                timeRemainingSeconds--
                val minutes = timeRemainingSeconds / 60
                val seconds = timeRemainingSeconds % 60
                binding.tvMinutes.text = String.format("%02d", minutes)
                binding.tvSeconds.text = String.format("%02d", seconds)
                timerHandler.postDelayed(this, 1000)
            }
        }
    }

    private fun startPremiumCountdown() {
        timerHandler.postDelayed(timerRunnable, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        timerHandler.removeCallbacks(timerRunnable)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupDiagnoseTabLogic() {
        binding.btnViewAllDiseases.setOnClickListener {
            binding.layoutDiagnoseMain.visibility = android.view.View.GONE
            binding.layoutDiagnoseSearch.visibility = android.view.View.VISIBLE
        }

        binding.btnBackFromSearch.setOnClickListener {
            binding.layoutDiagnoseMain.visibility = android.view.View.VISIBLE
            binding.layoutDiagnoseSearch.visibility = android.view.View.GONE
        }

        binding.btnAutoDiagnose.setOnClickListener {
            takePicturePreviewLauncher.launch(null)
        }

        binding.btnHelpMain.setOnClickListener {
            showToast("Opening Diagnose Help...")
        }

        binding.etSearchDiseases.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString()?.lowercase() ?: ""
                val items = listOf(
                    Pair(binding.cardSearchAbiotic, "abiotic"),
                    Pair(binding.cardSearchWaterRelated, "water-related issue"),
                    Pair(binding.cardSearchNutrientRelated, "nutrient-related issue"),
                    Pair(binding.cardSearchWaterExcess, "water excess or uneven watering"),
                    Pair(binding.cardSearchNutrientDeficiency, "nutrient deficiency"),
                    Pair(binding.cardSearchWaterDeficiency, "water deficiency"),
                    Pair(binding.cardSearchFungi, "fungi"),
                    Pair(binding.cardSearchAnimalia, "animalia"),
                    Pair(binding.cardSearchSenescence, "senescence"),
                    Pair(binding.cardSearchLightRelated, "light-related issue"),
                    Pair(binding.cardSearchDeadPlant, "dead plant"),
                    Pair(binding.cardSearchInsecta, "insecta"),
                    Pair(binding.cardSearchMechanicalDamage, "mechanical damage"),
                    Pair(binding.cardSearchFeedingDamage, "feeding damage by insects"),
                    Pair(binding.cardSearchLightExcess, "light excess")
                )
                for ((card, name) in items) {
                    if (name.contains(query)) {
                        card.visibility = android.view.View.VISIBLE
                    } else {
                        card.visibility = android.view.View.GONE
                    }
                }
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
