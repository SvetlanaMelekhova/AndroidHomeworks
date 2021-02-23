package by.htp.first.myproject.view.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import by.htp.first.myproject.databinding.ItemViewScheduleBinding
import by.htp.first.myproject.model.entity.PersonScheduleData

class PersonScheduleAdapter () : RecyclerView.Adapter<PersonScheduleAdapter.PersonScheduleViewHolder>(), Filterable {
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

    fun updateLists(list: List<PersonScheduleData>) {
        allSchedule = ArrayList(list as ArrayList<PersonScheduleData>)
        allSchedule.sortBy { it.plan.toLowerCase() }
        scheduleListForFilter = ArrayList(list)
        scheduleListCopyForOrder = ArrayList(list)

        notifyDataSetChanged()
    }


    class PersonScheduleViewHolder(
        private val itemViewScheduleBinding: ItemViewScheduleBinding,
        private val listener: (personScheduleData: PersonScheduleData) -> Unit
    ) : RecyclerView.ViewHolder(itemViewScheduleBinding.root) {
        fun bind(personScheduleData: PersonScheduleData) {
            with(itemViewScheduleBinding) {

                textViewTime.text = personScheduleData.time
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

    private val filter: Filter = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList = arrayListOf<PersonScheduleData>()
            if (p0 == null || p0.isEmpty()) {
                filteredList.addAll(scheduleListForFilter)
            } else {
                val filterPattern = p0.toString().toLowerCase().trim()
                scheduleListForFilter.forEach {
                    if (it.date?.toLowerCase()?.contains(filterPattern)!!) {
                        filteredList.add(it)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            allSchedule.clear()
            allSchedule.addAll(p1?.values as ArrayList<PersonScheduleData>);
            notifyDataSetChanged()
        }
    }

    override fun getFilter() = filter
    fun showByOrder(context: Context, order: String) {
        var list = arrayListOf<PersonScheduleData>()
        /*when (order) {
            context.resources.getString(R.string.pending) -> {
                workInfoList = workInfoListCopyForOrder
                list = getListByOrder(order)
            }
            context.resources.getString(R.string.in_progress_lowe_case) -> {
                workInfoList = workInfoListCopyForOrder
                list = getListByOrder(order)
            }
            context.resources.getString(R.string.completed_in_lower_case) -> {
                workInfoList = workInfoListCopyForOrder
                list = getListByOrder(order)
            }
            "all" -> {
                list = workInfoListCopyForOrder
            }
        }
        workInfoList = ArrayList(list)
        workInfoListForFilter = ArrayList(list)
        notifyDataSetChanged()*/
    }

    /*private fun getListByOrder(order: String): ArrayList<PersonScheduleData> {
        val list = arrayListOf<PersonScheduleData>()
        workInfoList.forEach {
            if (it.status == order) list.add(it)
        }
        return list
    }
*/

}