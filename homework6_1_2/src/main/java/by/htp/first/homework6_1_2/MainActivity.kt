package by.htp.first.homework6_1_2

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val path = "content://by.htp.first.homework6_1_1.adapter.WorkContentProvider/database"
    private lateinit var logoTextView: TextView
    private lateinit var localAdapter: WorkAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var recycler: RecyclerView
    private lateinit var carNameInToolbar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        carNameInToolbar = findViewById(R.id.carNameInToolbar)
        setSupportActionBar(toolbar)
        recycler = findViewById(R.id.recyclerView)
        logoTextView = findViewById(R.id.worksIsEmptyTextView)
        val localLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        localAdapter = WorkAdapter(this, ArrayList())
        recycler.apply {
            layoutManager = localLayoutManager
            adapter = localAdapter
            localAdapter.updateLists(getDataFromProvider())
        }
        getDataFromProvider()
    }

    private fun getDataFromProvider(): ArrayList<WorkInfo> {
        val workList = arrayListOf<WorkInfo>()
        val cursor = contentResolver.query(Uri.parse(path), null, null, null, null)
        cursor?.run {
            while (moveToNext()) {
                workList.add(
                    WorkInfo(
                        getString(getColumnIndex("workName")),
                        getString(getColumnIndex("workDescription")),
                        getString(getColumnIndex("time")),
                        getString(getColumnIndex("progress")),
                        getString(getColumnIndex("cost")),
                        getInt(getColumnIndex("color"))
                    )
                )
            }
            cursor.close()
            logoTextView.setVisibileOrNot(localAdapter.works.isNotEmpty())
        }
        return workList
    }
}