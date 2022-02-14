package by.htp.first.homework5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarAdapter : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {
    private val carsList = ArrayList<Car>()
            //private lateinit var onEditIconClickListener: OnEditIconClickListener


    fun add(carInfo: Car) {
        carsList.add(carInfo)
        notifyItemChanged(carsList.indexOf(carInfo))
    }
    fun edit(carInfo: Car, position: Int) {
        carsList[position] = carInfo
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout_car, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(carsList[position])
    }

    override fun getItemCount(): Int {
        return carsList.size
    }


    /*override fun getItemCount() = carsList.size
    fun getList(): ArrayList<Car> {
        return carsList
    }*/

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        constructor(itemView: View, onEditIconClickListener: OnEditIconClickListener) : this(itemView){
            listener = onEditIconClickListener
        }
        private lateinit var listener: OnEditIconClickListener
        private val carImage: ImageView = itemView.findViewById(R.id.imageCar)
        private val editImage: ImageView = itemView.findViewById(R.id.iv_edit_info)
        private val textName: TextView = itemView.findViewById(R.id.tvName)
        private val textProducer: TextView = itemView.findViewById(R.id.tvProducer)
        private val textModel: TextView = itemView.findViewById(R.id.tvModel)
        fun bind(car: Car) {
           /* if (car.carBitmap == null) {
                carImage.setImageResource(R.drawable.default_icon)
            } else {
                carImage.setImageBitmap(carInfo.carBitmap)
            }
            textName.text = car.name
            textProducer.text = car.producer
            textModel.text = car.model
            editImage.setOnClickListener {
                listener.onEditIconClick(car, adapterPosition)
            }*/
        }
    }

    interface OnEditIconClickListener {
        fun onEditIconClick(car: Car, position: Int)
    }
    /*fun setOnEditIconClickListener(listener: OnEditIconClickListener){
        onEditIconClickListener = listener
    }*/
}