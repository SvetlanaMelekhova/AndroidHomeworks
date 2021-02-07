package by.htp.first.homework7_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.htp.first.homework7_1.adapter.WorkAdapter
import by.htp.first.homework7_1.database.DatabaseRepository
import by.htp.first.homework7_1.entity.Work
import by.htp.first.homework7_1.function.setVisibileOrNot
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WorkActivity : AppCompatActivity() {

    private lateinit var logoTextView: TextView
    private var parentCar: String? = null
    private var carId: Long = 0
    private lateinit var localAdapter: WorkAdapter
    private lateinit var editWorkListener: WorkAdapter.OnWorkClickListener
    private lateinit var databaseRepository: DatabaseRepository
    private lateinit var toolbar: Toolbar
    private lateinit var recycler: RecyclerView
    private lateinit var addWorkActionButton: FloatingActionButton
    private lateinit var carNameInToolbar: TextView
    private lateinit var backButton: ImageView
    private val positionCarInDatabase = "carId"
    private val parentCarName = "parentCarName"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_works)
        toolbar = findViewById(R.id.toolBarActivityAddCar)
        carNameInToolbar = findViewById(R.id.carNameInToolbar)
        setSupportActionBar(toolbar)
        recycler = findViewById(R.id.recyclerView)
        addWorkActionButton = findViewById(R.id.addNewWork)
        addWorkActionButton.setColorFilter(getColor(R.color.white))
        backButton = findViewById(R.id.backButton)
        logoTextView = findViewById(R.id.worksIsEmptyTextView)

        getIntentData(intent, carNameInToolbar)

        databaseRepository = DatabaseRepository(applicationContext)

        addWorkActionButton.setOnClickListener {
            Intent(this, AddWorkActivity::class.java).apply {
                putExtra(parentCarName, parentCar)
                startActivityForResult(this, 1)
            }
        }

        editWorkListener = object : WorkAdapter.OnWorkClickListener {
            override fun onWorkClick(workData: Work, position: Int) {
                Intent(applicationContext, EditWorkActivity::class.java).apply {
                    putExtra(positionCarInDatabase, workData.id)
                    startActivityForResult(this, 2)
                }
            }
        }

        backButton.setOnClickListener { finish() }

        val localLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        localAdapter = WorkAdapter(this, ArrayList(), editWorkListener)
        recycler.apply {
            layoutManager = localLayoutManager
            adapter = localAdapter
        }
        checkDataBase()
    }

    private fun checkDataBase() {
        val workList = databaseRepository.getParentWorks(parentCar)
        if (workList.isNotEmpty()) {
            localAdapter.works = workList as ArrayList<Work>
            logoTextView.setVisibileOrNot(localAdapter.works.isNotEmpty())
            localAdapter.notifyDataSetChanged()
        }
    }

    private fun getIntentData(intent: Intent, carNameInToolbar: TextView) {
        carId = intent.getLongExtra(positionCarInDatabase, 0)
        parentCar = intent.getStringExtra("modelName")
        carNameInToolbar.text = parentCar ?: getString(R.string.car_works)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        checkDataBase()
    }
}