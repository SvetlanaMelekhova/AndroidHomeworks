package by.htp.first.homework6_1_2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

class WorkAdapter(context: Context,
                   var works: ArrayList<WorkInfo>) :
    RecyclerView.Adapter<WorkAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_work_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workData: WorkInfo = works[position]
        holder.bind(workData, holder)
    }

    override fun getItemCount(): Int {
        return works.size
    }

    fun updateLists(list: ArrayList<WorkInfo>) {
        works = ArrayList(list)
        notifyDataSetChanged()
    }

    class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val typeOfWork: TextView = view.findViewById(R.id.typeOfWork)
        private val time: TextView = view.findViewById(R.id.timeTextView)
        private val progress: TextView = view.findViewById(R.id.progressTextView)
        private val cost: TextView = view.findViewById(R.id.coastTextView)
        private val image: ImageView = view.findViewById(R.id.workImage)

        fun bind(workInfo: WorkInfo, holder: ViewHolder) {

            holder.image.setColorFilter(itemView.resources.getColor(workInfo.color, itemView.context.theme))
            holder.typeOfWork.text = workInfo.workName
            holder.time.text = workInfo.time
            holder.progress.text = workInfo.progress
            holder.cost.text = workInfo.cost
        }
    }
}