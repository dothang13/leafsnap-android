package fxc.dev.app.ui.identify

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import fxc.dev.app.databinding.ActivityIdentifyBinding
import fxc.dev.app.ui.base.BaseActivity

@AndroidEntryPoint
class IdentifyActivity : BaseActivity<ActivityIdentifyBinding, IdentifyVM>() {
    override val viewModel: IdentifyVM by viewModels()

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivityIdentifyBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnClose.setOnClickListener { finish() }
        binding.btnFlash.setOnClickListener { showToast("Toggling camera flash...") }
        binding.btnFlip.setOnClickListener { showToast("Flipping camera...") }

        binding.btnGallery.setOnClickListener { showToast("Opening Gallery...") }
        binding.btnShutter.setOnClickListener { showToast("Capturing photo for identification...") }
        binding.btnInstruction.setOnClickListener { showToast("Opening Instructions...") }

        binding.modeProId.setOnClickListener {
            updateModeSelection(0)
            showToast("Mode: Pro ID")
        }
        binding.modePlantId.setOnClickListener {
            updateModeSelection(1)
            showToast("Mode: Plant ID")
        }
        binding.modeDiagnose.setOnClickListener {
            updateModeSelection(2)
            showToast("Mode: Diagnose")
        }
    }

    private fun updateModeSelection(selected: Int) {
        val selectedBgTint = android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#E8F5E9"))
        val unselectedColor = android.graphics.Color.parseColor("#888888")
        val selectedColor = android.graphics.Color.parseColor("#307B54")

        when (selected) {
            0 -> {
                binding.modeProId.setTextColor(selectedColor)
                binding.modeProId.backgroundTintList = selectedBgTint
                binding.modePlantId.setTextColor(unselectedColor)
                binding.modePlantId.backgroundTintList = null
                binding.modeDiagnose.setTextColor(unselectedColor)
                binding.modeDiagnose.backgroundTintList = null
            }
            1 -> {
                binding.modePlantId.setTextColor(selectedColor)
                binding.modePlantId.backgroundTintList = selectedBgTint
                binding.modeProId.setTextColor(unselectedColor)
                binding.modeProId.backgroundTintList = null
                binding.modeDiagnose.setTextColor(unselectedColor)
                binding.modeDiagnose.backgroundTintList = null
            }
            2 -> {
                binding.modeDiagnose.setTextColor(selectedColor)
                binding.modeDiagnose.backgroundTintList = selectedBgTint
                binding.modeProId.setTextColor(unselectedColor)
                binding.modeProId.backgroundTintList = null
                binding.modePlantId.setTextColor(unselectedColor)
                binding.modePlantId.backgroundTintList = null
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
