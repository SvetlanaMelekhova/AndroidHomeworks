package by.htp.first.homework7_1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import by.htp.first.homework7_1.R
import by.htp.first.homework7_1.entity.Car
import com.bumptech.glide.request.RequestOptions
import java.util.Locale
import kotlin.collections.ArrayList

class CarAdapter(var cars: ArrayList<Car>,
                 private val onCarClickListener: OnCarClickListener) : RecyclerView.Adapter<CarAdapter.ViewHolder>(), Filterable {

    lateinit var carsCopy: ArrayList<Car>

    interface OnCarClickListener {
        fun onCarClick(car: Car, position: Int, flag: Int)
    }

    private fun selector(p: Car): String = p.carModelName

    fun sortByCarBrand() {
        cars.sortBy { selector(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout_car, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val car: Car = cars[position]
        holder.bind(car, holder, onCarClickListener)
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val image: ImageView = view.findViewById(R.id.background)
        private val carOwnerName: TextView = view.findViewById(R.id.driverName)
        private val carModelName: TextView = view.findViewById(R.id.carName)
        private val carGosNumber: TextView = view.findViewById(R.id.carNumber)
        private val parent: CardView = view.findViewById(R.id.parent)
        private val carEditButton: ImageView = view.findViewById(R.id.editCarButton)
        private val cameraNoPhoto: ImageView = view.findViewById(R.id.cameraNoPhoto)

        fun bind(car: Car, holder: ViewHolder, onCarClickListener: OnCarClickListener) {

            Glide.with(itemView)
                    .load(car.carImage)
                    .apply(RequestOptions()
                            .error(R.drawable.ic_background_view))
                    .into(image)
            if (car.carImage != null) cameraNoPhoto.visibility = View.INVISIBLE

            holder.carOwnerName.text = car.carOwnerName
            holder.carModelName.text = car.carModelName
            holder.carGosNumber.text = car.carPlateNumber

            holder.carEditButton.setOnClickListener {
                onCarClickListener.onCarClick(car, adapterPosition, 1)
            }

            holder.parent.setOnClickListener {
                onCarClickListener.onCarClick(car, adapterPosition, 2)
            }
        }
    }

    override fun getFilter(): Filter {
        return filter
    }

    private val filter: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filteredList: ArrayList<Car> = arrayListOf()
            val filterPattern = charSequence.toString().toLowerCase().trim { it <= ' ' }
            for (item in carsCopy) {
                if (item.carModelName.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                    filteredList.add(item)
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            cars.clear()
            cars.addAll(filterResults.values as Collection<Car>)
            notifyDataSetChanged()
        }
    }
}
