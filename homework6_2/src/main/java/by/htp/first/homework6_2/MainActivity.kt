package by.htp.first.homework6_2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

private const val MY_PERMISSION_CODE = 1

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerPhotos: RecyclerView
    private lateinit var adapter: ImageUriAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerPhotos = findViewById(R.id.recyclerPhotos)
        adapter = ImageUriAdapter()
        setRecyclerSettings()
        checkPermissions()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), MY_PERMISSION_CODE)
        } else {
            loadAllPhotos()
            setAdapterListener()
        }
    }

    private fun setRecyclerSettings() {
        recyclerPhotos.adapter = adapter
        recyclerPhotos.layoutManager = GridLayoutManager(this, 3)
    }

    private fun loadAllPhotos() {
        adapter.updateList(ImageLoader.loadAllPhotos(this))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(recyclerPhotos, R.string.permission_granted, Snackbar.LENGTH_LONG ).show()
                loadAllPhotos()
            }
        } else {
            Snackbar.make(recyclerPhotos, R.string.permission_denied, Snackbar.LENGTH_LONG ).show()
        }
    }

    private fun setAdapterListener() {
        adapter.showAllImageListener = {
            val intent = Intent(this, AllImageActivity::class.java)
            intent.putExtra("path", it)
            startActivityForResult(intent, 1)
        }
    }
}

