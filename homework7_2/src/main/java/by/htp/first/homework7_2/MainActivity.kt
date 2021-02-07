package by.htp.first.homework7_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.htp.first.homework7_2.adapter.CarAdapter
import by.htp.first.homework7_2.entity.Car
import by.htp.first.homework7_2.database.DatabaseRepository
import by.htp.first.homework7_2.function.setVisibileOrNot
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var carAdapter: CarAdapter
    private lateinit var databaseRepository: DatabaseRepository
    private lateinit var carAdapterListener: CarAdapter.OnCarClickListener
    private lateinit var logoTextView: TextView
    private lateinit var recycler: RecyclerView
    private lateinit var addActionButton: FloatingActionButton
    private lateinit var toolbar: Toolbar
    private lateinit var searchView: SearchView
    private val positionCarInDatabase = "carId"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolBarActivityAddCar)
        setSupportActionBar(toolbar)
        writeLog()
        recycler = findViewById(R.id.recyclerView)
        addActionButton = findViewById(R.id.addNewCar)
        logoTextView = findViewById(R.id.listIsEmptyTextView)
        addActionButton.setColorFilter(getColor(R.color.white))

        databaseRepository = DatabaseRepository(applicationContext)

        addActionButton.setOnClickListener {
            val intent = Intent(this, AddCarActivity::class.java)
            startActivityForResult(intent, 1)
        }

        carAdapterListener = object : CarAdapter.OnCarClickListener {
            override fun onCarClick(car: Car, position: Int, flag: Int) {
                when (flag) {
                    1 -> {
                        Intent(applicationContext, EditCarActivity::class.java).apply {
                            putExtra(positionCarInDatabase, car.id)
                            startActivityForResult(this, 2)
                        }
                    }
                    2 -> {
                        Intent(applicationContext, WorkActivity::class.java).apply {
                            putExtra("modelName", car.carModelName)
                            putExtra(positionCarInDatabase, car.id)
                            startActivityForResult(this, 3)
                        }
                    }
                }
            }
        }

        val localLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        carAdapter = CarAdapter(ArrayList(), carAdapterListener)
        recycler.apply {
            layoutManager = localLayoutManager
            adapter = carAdapter
        }
        checkDataBase()
    }

    private fun checkDataBase() {
        databaseRepository.mainScope().launch {
            val carList = databaseRepository.getCarsList()
            if (carList.isNotEmpty()) {
                carAdapter.apply {
                    cars = carList as ArrayList<Car>
                    carsCopy = carList
                    sortByCarBrand()
                    notifyDataSetChanged()
                }
                logoTextView.setVisibileOrNot(carAdapter.cars.isNotEmpty())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        checkDataBase()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean { return false }
            override fun onQueryTextChange(chars: String): Boolean {
                carAdapter.filter.filter(chars)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun writeLog() {
        val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
            .format(Date()).toString()
        openFileOutput("appLog.txt", MODE_APPEND).apply {
            write(("\n" + time).toByteArray())
            close()
        }
    }
}
