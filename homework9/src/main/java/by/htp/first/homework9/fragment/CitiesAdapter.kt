package by.htp.first.homework9.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import by.htp.first.homework9.database.Cities
import by.htp.first.homework9.databinding.ItemRecycleLayoutBinding

class CitiesAdapter(private var listener: OnCityClickListener) : RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder>() {
    private var cityList: List<Cities> = emptyList()

    fun addCity(cityName: List<Cities>) {
        cityList = cityName
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CitiesViewHolder(ItemRecycleLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.bind(cityList[position], listener)
    }

    override fun getItemCount() = cityList.size

    class CitiesViewHolder(private val binding: ItemRecycleLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: Cities, listener: OnCityClickListener) {
            binding.cityName.text = city.city
            binding.root.setOnClickListener {
                binding.submitCheck.visibility = View.VISIBLE
                listener.onCityClick(city = city, viewNeedToCheck = binding.submitCheck, false)
            }

            binding.root.setOnLongClickListener {
                listener.onCityClick(city = city, viewNeedToCheck = binding.submitCheck, true)
                return@setOnLongClickListener true
            }
        }
    }

    interface OnCityClickListener {
        fun onCityClick(city: Cities, viewNeedToCheck: ImageView, isLongClick: Boolean)
    }
}