package by.htp.first.homework8_2

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import by.htp.first.homework8_2.database.DatabaseRepository
import by.htp.first.homework8_2.entity.Work
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class AddWorkActivity : AppCompatActivity() {

    private lateinit var databaseRepository: DatabaseRepository

    private var color: Int? = null
    private var progress: String? = null
    private lateinit var addButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var timeTextView: TextView
    private lateinit var workNameEditText: EditText
    private lateinit var workDescriptionEditText: EditText
    private lateinit var workCostEditText: EditText
    private lateinit var pendingImageView: ImageView
    private lateinit var inProgressImageView: ImageView
    private lateinit var completedImageView: ImageView
    private val parentCarName = "parentCarName"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_work)
        val toolbar: Toolbar = findViewById(R.id.toolBarAddWorkCarActivity)
        setSupportActionBar(toolbar)

        databaseRepository = DatabaseRepository(applicationContext)

        addButton = findViewById(R.id.ibAddButtonAddWorkCarActivity)
        backButton = findViewById(R.id.ibBackButtonAddWorkCarActivity)
        timeTextView = findViewById(R.id.setTime)
        workNameEditText = findViewById(R.id.etWorkNameAddWorkCarActivity)
        workDescriptionEditText = findViewById(R.id.etDescriptionAddWorkCarActivity)
        workCostEditText = findViewById(R.id.etCostAddWorkCarActivity)
        pendingImageView = findViewById(R.id.ivPendingAddWorkCarActivity)
        inProgressImageView = findViewById(R.id.ivInProgressAddWorkCarActivity)
        completedImageView = findViewById(R.id.ivCompletedAddWorkCarActivity)

        timeTextView.text = getCurrentData()

        pendingImageView.setOnClickListener {
            pendingSetColor()
        }

        inProgressImageView.setOnClickListener {
            inProgressSetColor()
        }

        completedImageView.setOnClickListener {
            completedSetColor()
        }

        backButton.setOnClickListener { finish() }

        addButton.setOnClickListener {
            if (color == null) {
                Snackbar.make(addButton, getString(R.string.selectProgress), Snackbar.LENGTH_LONG).show()
            } else if (workNameEditText.text.isNotEmpty() && workDescriptionEditText.text.isNotEmpty() && workCostEditText.text.isNotEmpty()) {
                createObject().apply {
                    parentCar = intent.getStringExtra(parentCarName)
                    databaseRepository.addWorkToDatabase(this)
                    finish()
                }
            } else {
                Snackbar.make(addButton, getString(R.string.fillAllFields), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun completedSetColor() {
        pendingImageView.setColorFilter(getColor(R.color.defaultColor))
        inProgressImageView.setColorFilter(getColor(R.color.defaultColor))
        completedImageView.setColorFilter(getColor(R.color.completeColor))
        color = R.color.completeColor
        progress = getString(R.string.complete)
    }

    private fun inProgressSetColor() {
        pendingImageView.setColorFilter(getColor(R.color.defaultColor))
        inProgressImageView.setColorFilter(getColor(R.color.inProgressColor))
        completedImageView.setColorFilter(getColor(R.color.defaultColor))
        color = R.color.inProgressColor
        progress = getString(R.string.in_progress)
    }

    private fun pendingSetColor() {
        pendingImageView.setColorFilter(getColor(R.color.pendingColor))
        inProgressImageView.setColorFilter(getColor(R.color.defaultColor))
        completedImageView.setColorFilter(getColor(R.color.defaultColor))
        color = R.color.pendingColor
        progress = getString(R.string.pending)
    }

    private fun createObject()=
        Work(workNameEditText.text.toString(),
            workDescriptionEditText.text.toString(),
            timeTextView.text.toString(),
            progress!!,
            workCostEditText.text.toString(),
            color!!)

    private fun getCurrentData() =
        SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
            .format(Date())
}

