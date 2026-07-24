package fxc.dev.app.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import fxc.dev.app.databinding.ActivityCategoryBinding
import fxc.dev.app.ui.base.BaseActivity

@AndroidEntryPoint
class CategoryActivity : BaseActivity<ActivityCategoryBinding, CategoryVM>() {
    override val viewModel: CategoryVM by viewModels()

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivityCategoryBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        val categoryKey = intent.getStringExtra(EXTRA_CATEGORY_KEY) ?: "flowers"
        val categoryTitle = intent.getStringExtra(EXTRA_CATEGORY_TITLE) ?: "Flowers"

        binding.tvCategoryTitle.text = categoryTitle

        binding.btnCategoryClose.setOnClickListener {
            finish()
        }

        val plants = PlantData.categoriesMap[categoryKey] ?: emptyList()
        val adapter = CategoryAdapter(plants) { plant ->
            Toast.makeText(this, "Clicked on: ${plant.commonName}", Toast.LENGTH_SHORT).show()
        }
        binding.rvPlants.adapter = adapter
    }

    companion object {
        const val EXTRA_CATEGORY_KEY = "extra_category_key"
        const val EXTRA_CATEGORY_TITLE = "extra_category_title"
    }
}
