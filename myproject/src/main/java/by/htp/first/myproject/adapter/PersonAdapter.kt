package by.htp.first.myproject.adapter

import android.app.Person
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.htp.first.myproject.R
import by.htp.first.myproject.databinding.ItemViewPersonBinding
import by.htp.first.myproject.entity.PersonData
import by.htp.first.myproject.entity.PersonScheduleData
import com.bumptech.glide.Glide

class PersonAdapter (): RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {
    constructor(savedInfo: List<PersonData>) : this() {
        personInfoList = ArrayList(savedInfo)
        personInfoListForFilter = ArrayList(savedInfo)
    }

    private var personInfoList: ArrayList<PersonData> = arrayListOf()
    private var personInfoListForFilter: ArrayList<PersonData> = arrayListOf()
    lateinit var onEditIconClickListener: (personData: PersonData) -> Unit
    lateinit var onPersonScheduleInfoListListClickListener: (personData: PersonData) -> Unit


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PersonViewHolder(
            itemViewPersonBinding = ItemViewPersonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listenerPersonData = onEditIconClickListener,
            listenerShowPersonScheduleData = onPersonScheduleInfoListListClickListener
        )

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(personInfoList[position])
    }

    override fun getItemCount() = personInfoList.size

    class PersonViewHolder(
        private val itemViewPersonBinding: ItemViewPersonBinding,
        private val listenerPersonData: (personData: PersonData) -> Unit,
        private val listenerShowPersonScheduleData: (personData: PersonData) -> Unit
    ) : RecyclerView.ViewHolder(itemViewPersonBinding.root) {

        fun bind(personData: PersonData) {
            if (personData.pathToPicture.isEmpty())
                itemViewPersonBinding.personPhoto.setImageResource(R.drawable.ic_twotone_face_24)
            else Glide.with(itemViewPersonBinding.root.context)
                .load(personData.pathToPicture)
                .into(itemViewPersonBinding.personPhoto)

            with(itemViewPersonBinding) {
                personName.text = personData.personName

                root.setOnClickListener { listenerShowPersonScheduleData.invoke(personData) }
                editButton.setOnClickListener { listenerPersonData.invoke(personData) }
            }

        }
    }
}
