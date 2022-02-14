package by.htp.first.homework5_1

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import by.htp.first.homework5_1.database.CarDatabase
import by.htp.first.homework5_1.database.CarDatabaseDAO
import by.htp.first.homework5_1.entity.Car
import java.io.File
import java.util.UUID

class EditCarActivity : AppCompatActivity() {

    private var carId: Long = 0
    private var photoFile: File? = null
    private var photoUri: Uri? = null
    private var car: Car? = null
    private lateinit var dao: CarDatabaseDAO
    private lateinit var imageBackground: ImageView
    private lateinit var carOwnerName: EditText
    private lateinit var carProducerName: EditText
    private lateinit var carModelName: EditText
    private lateinit var carPlateNumber: EditText
    private lateinit var buttonBack: ImageView
    private lateinit var buttonAdd: ImageView
    private lateinit var photoCamera: ImageView
    private lateinit var noPhotoTextView: TextView
    private val positionCarInDatabase = "carId"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_car)
        val toolbar: Toolbar = findViewById(R.id.toolBarActivityEditCar)
        setSupportActionBar(toolbar)
        dao = CarDatabase.init(this).getCarDatabaseDAO()
        carOwnerName = findViewById(R.id.etOwnerNameActivityEdit)
        carProducerName = findViewById(R.id.etProducerActivityEdit)
        carModelName = findViewById(R.id.etModelActivityEdit)
        carPlateNumber = findViewById(R.id.etPlateNumberActivityEdit)
        buttonBack = findViewById(R.id.backButtonActivityEditCar)
        buttonAdd = findViewById(R.id.addButtonActivityEditCar)
        photoCamera = findViewById(R.id.photoActionButtonActivityEditCar)
        imageBackground = findViewById(R.id.backgroundActivityEditCar)
        noPhotoTextView = findViewById(R.id.tvNoPhotoActivityEditCar)
        photoCamera.setColorFilter(getColor(R.color.white))

        getIntentExtras(intent)

        if (car != null) { fillPage() }

        createFileAndUri()

        buttonBack.setOnClickListener { finish() }

        photoCamera.setOnClickListener {
            val intentGetPhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            getPermissions(intentGetPhoto)
            startActivityForResult(intentGetPhoto, 1)
        }

        buttonAdd.setOnClickListener { addListener() }

    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun getPermissions(intentGetPhoto: Intent) {
        val cameraActivities: List<ResolveInfo> = packageManager.queryIntentActivities(intentGetPhoto, PackageManager.MATCH_DEFAULT_ONLY)

        for (cameraActivity in cameraActivities) {
            this.grantUriPermission(cameraActivity.activityInfo.packageName,
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    }

    private fun createFileAndUri() {
        photoFile = File(this.filesDir, UUID.randomUUID().toString() + " .jpg")
        photoUri = FileProvider.getUriForFile(this, "by.htp.first.homework5_1.fileprovider", photoFile!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageBackground.setImageURI(photoFile?.path?.toUri())
        noPhotoTextView.visibility = View.INVISIBLE
    }

    private fun addListener() {
        if (carOwnerName.text.isNotEmpty() && carProducerName.text.isNotEmpty() && carModelName.text.isNotEmpty() && carPlateNumber.text.isNotEmpty()) {
            val car = fillCarObject()
            dao.update(car)

            Intent().apply {
                putExtra(positionCarInDatabase, carId)
                setResult(RESULT_OK, this)
                finish() }

        } else {
            Toast.makeText(this, getString(R.string.fill_out_form), Toast.LENGTH_LONG).show()
        }
    }

    private fun fillCarObject(): Car {
        return Car(carOwnerName.text.toString(),
                carProducerName.text.toString(),
                carModelName.text.toString(),
                carPlateNumber.text.toString(),
                photoUri.toString()).also { it.id = carId }
    }

    private fun getIntentExtras(intent: Intent) {
        carId = intent.getLongExtra(positionCarInDatabase, 0)
        car = dao.getCar(carId)
    }

    private fun fillPage() {
        carOwnerName.setText(car?.carOwnerName)
        carProducerName.setText(car?.carProducerName)
        carModelName.setText(car?.carModelName)
        carPlateNumber.setText(car?.carPlateNumber)
        if (car?.carImage == null) {
            imageBackground.setImageResource(R.drawable.ic_background_view)
        } else {
            imageBackground.setImageURI(car?.carImage?.toUri())
            noPhotoTextView.visibility = View.INVISIBLE
        }
    }
}

