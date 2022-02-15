package by.htp.first.homework7_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import by.htp.first.homework7_1.database.DatabaseRepository
import by.htp.first.homework7_1.entity.Work
import com.google.android.material.snackbar.Snackbar

private const val DB_ID_COLUMN_NAME = "carId"

class EditWorkActivity : AppCompatActivity() {

    private lateinit var databaseRepository: DatabaseRepository

    private var workObject: Work? = null
    private var workId: Long = 0
    private var color: Int? = null
    private var progress: String? = null
    private lateinit var backButton: ImageButton
    private lateinit var addButton: ImageButton
    private lateinit var removeButton: ImageButton
    private lateinit var pendingImageView: ImageView
    private lateinit var inProgressImageView: ImageView
    private lateinit var completedImageView: ImageView
    private lateinit var workNameEditText: EditText
    private lateinit var workDescriptionEditText: EditText
    private lateinit var workCostEditText: EditText
    private lateinit var timeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_work)
        val toolbar: Toolbar = findViewById(R.id.toolbarEditWorkCarActivity)
        setSupportActionBar(toolbar)

        databaseRepository = DatabaseRepository(applicationContext)

        getIntentData(intent)

        addButton = findViewById(R.id.ibAddButtonEditWorkCarActivity)
        backButton = findViewById(R.id.ibBackButtonEditWorkCarActivity)
        removeButton = findViewById(R.id.ibRemoveButtonEditWorkCarActivity)
        timeTextView = findViewById(R.id.setTime)
        workNameEditText = findViewById(R.id.etWorkNameEditWorkCarActivity)
        workDescriptionEditText = findViewById(R.id.etDescriptionEditWorkCarActivity)
        workCostEditText = findViewById(R.id.etCostEditWorkCarActivity)
        pendingImageView = findViewById(R.id.ivPendingEditWorkCarActivity)
        inProgressImageView = findViewById(R.id.ivInProgressEditWorkCarActivity)
        completedImageView = findViewById(R.id.ivCompletedEditWorkCarActivity)

        findViewById<TextView>(R.id.titleInToolbar).text = workObject?.workName ?: getString(R.string.edit)

        fillPage()

        pendingImageView.setOnClickListener { pendingSetColor() }

        inProgressImageView.setOnClickListener { inProgressSetColor() }

        completedImageView.setOnClickListener { completedSetColor() }

        backButton.setOnClickListener { finish() }

        removeButton.setOnClickListener { showRemoveDialog() }

        addButton.setOnClickListener {
            if (color == null) {
                Snackbar.make(addButton, getString(R.string.selectProgress), Snackbar.LENGTH_LONG).show()
            } else if (workNameEditText.text.isNotEmpty() && workDescriptionEditText.text.isNotEmpty() && workCostEditText.text.isNotEmpty()) {
                saveDataAndCloseActivity(createObject())
            } else {
                Snackbar.make(addButton, getString(R.string.fillAllFields), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun fillPage() {
        workObject?.let {
            timeTextView.text = workObject?.time
            workNameEditText.setText(workObject?.workName)
            workDescriptionEditText.setText(workObject?.workDescription)
            workCostEditText.setText(workObject?.cost)
            when (progress) {
                getString(R.string.pending) -> pendingImageView.setColorFilter(getColor(R.color.pendingColor))
                getString(R.string.in_progress) -> inProgressImageView.setColorFilter(getColor(R.color.inProgressColor))
                getString(R.string.complete) -> completedImageView.setColorFilter(getColor(R.color.completeColor))
            }
        }
    }

    private fun getIntentData(intent: Intent) {
        workId = intent.getLongExtra(DB_ID_COLUMN_NAME, 0)
        workObject = databaseRepository.getWork(workId)
        progress = workObject?.progress ?: getString(R.string.pending)
        color = workObject?.color
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

    private fun createObject() =
            Work(workNameEditText.text.toString(),
                    workDescriptionEditText.text.toString(),
                    timeTextView.text.toString(),
                    progress!!,
                    workCostEditText.text.toString(),
                    color!!)
                    .also {
                        it.parentCar = workObject?.parentCar
                        it.id = workObject?.id
                    }

    private fun saveDataAndCloseActivity(work: Work) {

        databaseRepository.updateWork(work)
        finish()
    }

    private fun showRemoveDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.remove_work))
            setMessage(getString(R.string.deleteWorkMessage))
            setPositiveButton(getString(R.string.yesButton)) { _, _ ->
                workObject?.let { databaseRepository.deleteWork(it) }
                setResult(RESULT_OK, Intent())
                finish()
            }
            setNegativeButton(getString(R.string.cancelButton)) { dialogInterface, _ -> dialogInterface.cancel() }
            setCancelable(false)
            create()
            show()
        }
    }
}