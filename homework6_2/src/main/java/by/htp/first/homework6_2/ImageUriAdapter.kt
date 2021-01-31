package by.htp.first.homework6_2

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageUriAdapter : RecyclerView.Adapter<ImageUriAdapter.ImageUriViewHolder>() {
    private var listUri = arrayListOf<Uri>()
    lateinit var showAllImageListener: (Uri) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageUriViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ImageUriViewHolder(view, showAllImageListener)
    }

    override fun onBindViewHolder(holder: ImageUriViewHolder, position: Int) {
        holder.bind(listUri[position])
    }

    override fun getItemCount() = listUri.size
    fun updateList(list: ArrayList<Uri>) {
        listUri = ArrayList(list)
        notifyDataSetChanged()
    }

    class ImageUriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        constructor(itemView: View, showWholeImageListener: (Uri) -> Unit) : this(itemView) {
            this.showAllImageListener = showWholeImageListener
        }

        private lateinit var showAllImageListener: (Uri) -> Unit
        private val image = itemView.findViewById<ImageView>(R.id.ivPhoto)
        fun bind(path: Uri) {
            Glide.with(itemView.context).load(path).into(image)
            itemView.setOnClickListener {
                showAllImageListener.invoke(path)
            }
        }
    }
}