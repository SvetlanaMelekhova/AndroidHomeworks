package by.htp.first.homework6_2

import android.app.Activity
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class AllImageActivity : AppCompatActivity() {
    private lateinit var allImage: ImageView
    private lateinit var buttonBack: ImageButton
    private lateinit var imageName: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_image)
        allImage = findViewById(R.id.ivWholeImage)
        buttonBack = findViewById(R.id.buttonBack)
        imageName = findViewById(R.id.imageName)
        loadImage()
        setButtonListener()
    }

    private fun getPathFromIntent(): Uri? {
        val path = intent.getParcelableExtra<Uri>("path")
        if (path != null) {
            return path
        }
        return null
    }

    private fun loadImage() {
        val path = getPathFromIntent()
        if (path != null) {
            Glide.with(this).load(path).into(allImage)
            imageName.text = path.lastPathSegment
        }
    }

    private fun setButtonListener() {
        buttonBack.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}