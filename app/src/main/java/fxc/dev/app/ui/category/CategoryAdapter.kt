package fxc.dev.app.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fxc.dev.app.databinding.ItemPlantBinding

class CategoryAdapter(
    private var plants: List<Plant>,
    private val onPlantClick: (Plant) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.PlantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val binding = ItemPlantBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(plants[position])
    }

    override fun getItemCount(): Int = plants.size

    fun submitList(newPlants: List<Plant>) {
        plants = newPlants
        notifyDataSetChanged()
    }

    inner class PlantViewHolder(private val binding: ItemPlantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plant: Plant) {
            binding.tvCommonName.text = plant.commonName
            binding.tvScientificName.text = plant.scientificName

            Glide.with(binding.ivPlantImage.context)
                .load(plant.imageUrl)
                .placeholder(fxc.dev.app.R.drawable.bg_no_image_avaiable)
                .error(fxc.dev.app.R.drawable.bg_no_image_avaiable)
                .into(binding.ivPlantImage)

            binding.root.setOnClickListener {
                onPlantClick(plant)
            }
        }
    }
}
