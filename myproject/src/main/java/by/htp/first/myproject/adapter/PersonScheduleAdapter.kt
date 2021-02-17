package by.htp.first.myproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.htp.first.myproject.R
import by.htp.first.myproject.databinding.ItemViewScheduleBinding
import by.htp.first.myproject.entity.PersonScheduleData
import com.bumptech.glide.Glide

class PersonScheduleAdapter () : RecyclerView.Adapter<PersonScheduleAdapter.PersonScheduleViewHolder>() {
    constructor(listSchedule: List<PersonScheduleData>): this () {
        allSchedule = listSchedule as ArrayList<PersonScheduleData>
        scheduleListForFilter = ArrayList(allSchedule)
        scheduleListCopyForOrder = ArrayList(allSchedule)
    }

    private var allSchedule: ArrayList<PersonScheduleData> = arrayListOf()
    private var scheduleListForFilter: ArrayList<PersonScheduleData> = arrayListOf()
    private var scheduleListCopyForOrder: ArrayList<PersonScheduleData> = arrayListOf()
    lateinit var onScheduleInfoItemClickListener: (personScheduleData: PersonScheduleData) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PersonScheduleViewHolder(
            itemViewScheduleBinding = ItemViewScheduleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener = onScheduleInfoItemClickListener
        )

    override fun onBindViewHolder(holder: PersonScheduleViewHolder, position: Int) {
       holder.bind(allSchedule[position])
    }

    override fun getItemCount() = allSchedule.size


    class PersonScheduleViewHolder(
        private val itemViewScheduleBinding: ItemViewScheduleBinding,
        private val listener: (personScheduleData: PersonScheduleData) -> Unit
    ) : RecyclerView.ViewHolder(itemViewScheduleBinding.root) {
        fun bind(personScheduleData: PersonScheduleData) {
            with(itemViewScheduleBinding) {

                textViewDate.text = personScheduleData.time
                textViewPlan.text = personScheduleData.plan
                textViewDate.text = personScheduleData.date




               root.setOnClickListener { listener.invoke(personScheduleData) }

              /*  if (personScheduleData.pathToPicture.isEmpty()) personScheduleData.setImageResource(R.drawable.ic_twotone_face_24)
                else Glide.with(itemView.context)
                    .load(carInfo.pathToPicture)
                    .into(carImage)

                Glide.with(itemViewScheduleBinding.root.context)
                    .load(item.urlToImage)
                    .centerCrop()
                    .into(itemNewsBinding.newsPreview)*/
            }
        }/*{
            if (carInfo.pathToPicture.isEmpty()) carImage.setImageResource(R.drawable.default_icon)
            else Glide.with(itemView.context).load(carInfo.pathToPicture).into(carImage)
            textName.text = carInfo.name
            textProducer.text = carInfo.producer
            textModel.text = carInfo.model
            editImage.setOnClickListener {
                listenerCarInfo.invoke(carInfo)
            }
            itemView.setOnClickListener {
                listenerShowWorkList.invoke(carInfo)
            }
        }*/
    }


}