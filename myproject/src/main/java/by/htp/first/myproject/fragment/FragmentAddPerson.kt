package by.htp.first.myproject.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import by.htp.first.myproject.R
import by.htp.first.myproject.database.DatabaseRepository
import by.htp.first.myproject.databinding.FragmentAddPersonBinding
import by.htp.first.myproject.entity.PersonData
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

private const val REQUEST_CODE_PHOTO = 1


class FragmentAddPerson: Fragment(R.layout.fragment_add_person) {

    private lateinit var loader: FragmentLoader
    private lateinit var repository: DatabaseRepository
    private var photoWasLoaded: Boolean = false
    private lateinit var pathToPicture: String
    private lateinit var personPhotoDirectory: File
    private lateinit var fragmentAddPersonBinding: FragmentAddPersonBinding
    private lateinit var activityScope: CoroutineScope


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = requireActivity() as FragmentLoader
        fragmentAddPersonBinding = FragmentAddPersonBinding.bind(view)
        activityScope = CoroutineScope(Dispatchers.Main + Job())
        repository = DatabaseRepository(context as Context)
        createDirectoryForPictures()
        setListeners()
    }

    private fun setListeners() {
        /* fragmentAddPersonBinding..setOnClickListener {
            backToMainFragment()
        }*/
        fragmentAddPersonBinding.buttonAdd.setOnClickListener {
            addPersonInfo()
        }
        fragmentAddPersonBinding.buttonPhotoCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CODE_PHOTO)
        }
    }

    private fun addPersonInfo() {
        if (!photoWasLoaded) {
            pathToPicture = ""
        }
        val name = fragmentAddPersonBinding.etPersonName.text.toString()
        if (name.isNotEmpty()) {
            activityScope.launch { repository.addPerson(PersonData(pathToPicture, name)) }
            backToMainFragment()
        } else {
            Snackbar.make(
                fragmentAddPersonBinding.etPersonName,
                "Fields can't be empty",
                Snackbar.LENGTH_LONG
            )
                .show()
            //Toast.makeText(context, "Fields can't be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun backToMainFragment() {
        loader.loadFragment(FragmentPersonList(), FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.get("data")?.run {
            pathToPicture = saveImage(this as Bitmap, fragmentAddPersonBinding.imageViewPhoto, personPhotoDirectory)
            photoWasLoaded = true
            //noCarPhoto.visibility = View.INVISIBLE
        }
    }

    private fun createDirectoryForPictures() {
        createDirectory(context as Context)?.run {
            personPhotoDirectory = this
        }
    }

    private fun saveImage(photo: Bitmap, iv: ImageView, personPhotoDirectory: File): String {
        val path = "photo_${System.currentTimeMillis()}.jpg"
        val pathToPicture = "${personPhotoDirectory.path}/${path}"
        val file = File(personPhotoDirectory, path)
        file.createNewFile()
        val stream = FileOutputStream(file)
        photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        iv.setImageBitmap(photo)
        stream.flush()
        stream.close()
        return pathToPicture
    }

    private fun createDirectory(context: Context): File? {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val personPhotoDirectory =
                File("${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/PersonPhoto")
            if (!personPhotoDirectory.exists()) {
                personPhotoDirectory.mkdir()
            }
            return personPhotoDirectory
        }
        return null


    }
}