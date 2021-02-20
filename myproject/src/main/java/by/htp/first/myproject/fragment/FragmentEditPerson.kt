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
import by.htp.first.myproject.databinding.FragmentEditPersonBinding
import by.htp.first.myproject.entity.PersonData
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

private const val REQUEST_CODE_PHOTO = 1

class FragmentEditPerson: Fragment(R.layout.fragment_edit_person) {

    private lateinit var loader: FragmentLoader
    private lateinit var scope: CoroutineScope
    private lateinit var binding: FragmentEditPersonBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var currentPersonData: PersonData
    private var personId: Long = 0
    private var photoWasLoaded: Boolean = false
    private lateinit var pathToPicture: String
    private lateinit var personPhotoDirectory: File



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = requireActivity() as FragmentLoader
        binding = FragmentEditPersonBinding.bind(view)
        repository = DatabaseRepository(context as Context)
        scope = CoroutineScope(Dispatchers.Main + Job())
        loadDataFromIntent(requireArguments())
        createDirectoryForPictures()
        setListeners()



    }
    private fun loadDataFromIntent(bundle: Bundle) {
        val personData = bundle.getParcelable<PersonData>("personData")
        if (personData != null) {
            with(personData) {
                currentPersonData = this
                personId = id
                val path = pathToPicture
                val file = File(path)
                if (file.exists()) {
                    if (path == "") {
                        binding.imageViewPhoto.setImageResource(R.drawable.ic_twotone_face_24)
                    } else {
                        Glide.with(context as Context).load(path).into(binding.imageViewPhoto)
                        photoWasLoaded = true
                        this@FragmentEditPerson.pathToPicture = path
                    }
                }
                binding.etPersonName.setText(personName)
            }
        }
    }

    private fun setListeners() {
        binding.buttonAdd.setOnClickListener {
            addPersonInfo()
        }
        binding.buttonPhotoCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CODE_PHOTO)
        }
    }

    private fun addPersonInfo() {
        val name = binding.etPersonName.text.toString()
        if (name.isNotEmpty()) {
            if (!photoWasLoaded) {
                pathToPicture = ""
            }
            val personData = PersonData(pathToPicture, name).also { it.id = personId }
            scope.launch { repository.updatePerson(personData) }
            backToMainFragment()
        } else {
            Snackbar.make(binding.etPersonName, "Fields can't be empty", Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun backToMainFragment() {
        loader.loadFragment(FragmentPersonList(), FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.get("data")?.run {
            pathToPicture = saveImage(this as Bitmap, binding.imageViewPhoto, personPhotoDirectory)
            photoWasLoaded = true
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