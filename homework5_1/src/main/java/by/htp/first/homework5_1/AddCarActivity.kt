package by.htp.first.homework5_1

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
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

class AddCarActivity : AppCompatActivity() {

    private lateinit var carPhoto: ImageView
    private lateinit var carOwnerName: EditText
    private lateinit var carProducerName: EditText
    private lateinit var carModelName: EditText
    private lateinit var carPlateNumber: EditText
    private lateinit var buttonBack: ImageButton
    private lateinit var buttonAdd: ImageButton
    private lateinit var photoCamera: ImageButton
    private lateinit var dao: CarDatabaseDAO
    private var photoFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)
        val toolbar: Toolbar = findViewById(R.id.toolBarActivityAddCar)
        setSupportActionBar(toolbar)

        dao = CarDatabase.init(this).getCarDatabaseDAO()

        carPhoto = findViewById(R.id.backgroundActivityAddCar)
        carOwnerName = findViewById(R.id.etOwnerNameActivityAdd)
        carProducerName = findViewById(R.id.etProducerActivityAdd)
        carModelName = findViewById(R.id.etModelActivityAdd)
        carPlateNumber = findViewById(R.id.etPlateNumberActivityAdd)
        buttonBack = findViewById(R.id.backButtonActivityAddCar)
        buttonAdd = findViewById(R.id.addButtonActivityAddCar)
        photoCamera = findViewById(R.id.photoActionButtonActivityAddCar)
        photoCamera.setColorFilter(getColor(R.color.white))

        buttonBack.setOnClickListener { finish() }

        buttonAdd.setOnClickListener {
            if (carOwnerName.text.isNotEmpty() && carModelName.text.isNotEmpty() && carPlateNumber.text.isNotEmpty()) {

                val car = createCarObject()
                dao.addCarToDatabase(car)

                val intent = Intent().putExtra("objectId", car.id)
                setResult(RESULT_OK, intent)
                finish()

            } else {
                Toast.makeText(this, getString(R.string.fill_out_form), Toast.LENGTH_LONG).show()
            }
        }

        photoCamera.setOnClickListener {

            photoFile = File(this.filesDir, UUID.randomUUID().toString() + " .jpg")
            val photoUri = FileProvider.getUriForFile(this, "by.htp.first.homework5_1.fileprovider", photoFile!!)
            val intentGetPhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intentGetPhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            getFileWritingPermission(intentGetPhoto, photoUri)
            startActivityForResult(intentGetPhoto, 5)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun getFileWritingPermission(intentGetPhoto: Intent, photoUri: Uri?) {
        val cameraActivities: List<ResolveInfo> = packageManager.queryIntentActivities(intentGetPhoto,
                PackageManager.MATCH_DEFAULT_ONLY)

        for (cameraActivity in cameraActivities) {
            this.grantUriPermission(cameraActivity.activityInfo.packageName,
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    }

    private fun createCarObject(): Car {
        return Car(carOwnerName.text.toString(),
            carProducerName.text.toString(),
            carModelName.text.toString(),
            carPlateNumber.text.toString(),
                photoFile?.path)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        carPhoto.setImageURI(photoFile?.path?.toUri())
    }
}


