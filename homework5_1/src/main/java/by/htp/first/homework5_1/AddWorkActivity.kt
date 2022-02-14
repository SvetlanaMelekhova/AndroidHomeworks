package by.htp.first.homework5_1

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import by.htp.first.homework5_1.database.CarDatabase
import by.htp.first.homework5_1.database.WorkDatabaseDAO
import by.htp.first.homework5_1.entity.Work
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class AddWorkActivity : AppCompatActivity() {

    private lateinit var dao: WorkDatabaseDAO
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

        dao = CarDatabase.init(this).getWorkDatabaseDAO()
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
                Toast.makeText(this, getString(R.string.selectProgress), Toast.LENGTH_LONG).show()

            } else if (workNameEditText.text.isNotEmpty() && workDescriptionEditText.text.isNotEmpty() && workCostEditText.text.isNotEmpty()) {

                createObject().apply {
                    parentCar = intent.getStringExtra(parentCarName)
                    dao.addCarToDatabase(this)
                    finish()
                }
            } else {
                Toast.makeText(this, getString(R.string.fillAllFields), Toast.LENGTH_LONG).show()
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

    private fun createObject(): Work {

        return Work(workNameEditText.text.toString(),
            workDescriptionEditText.text.toString(),
            timeTextView.text.toString(),
            progress!!,
            workCostEditText.text.toString(),
            color!!)
    }

    private fun getCurrentData(): String {
        return SimpleDateFormat(
            "dd/M/yyyy hh:mm:ss",
            Locale.getDefault())
            .format(Date())
    }
}

