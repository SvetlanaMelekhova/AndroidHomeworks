package by.htp.first.myproject.view.fragment.implement

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import by.htp.first.myproject.R
import by.htp.first.myproject.databinding.FragmentAddPersonBinding
import by.htp.first.myproject.function.createDirectory
import by.htp.first.myproject.function.saveImage
import by.htp.first.myproject.function.setVisibileOrNot
import by.htp.first.myproject.model.entity.PersonData
import by.htp.first.myproject.presenter.FragmentAddPersonPresenter
import by.htp.first.myproject.presenter.implement.FragmentAddPersonPresenterImpl
import by.htp.first.myproject.view.fragment.FragmentLoader
import com.google.android.material.snackbar.Snackbar
import java.io.File

private const val REQUEST_CODE_PHOTO_FROM_CAMERA = 1
private const val REQUEST_CODE_PHOTO_FROM_GALLERY = 2

class FragmentAddPerson : Fragment(R.layout.fragment_add_person) {

    private val presenter: FragmentAddPersonPresenter = FragmentAddPersonPresenterImpl()
    private lateinit var loader: FragmentLoader
    private var photoWasLoaded: Boolean = false
    private lateinit var pathToPicture: String
    private lateinit var personPhotoDirectory: File
    private lateinit var binding: FragmentAddPersonBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = requireActivity() as FragmentLoader
        binding = FragmentAddPersonBinding.bind(view)
        createDirectoryForPictures()
        setListeners()
    }

    private fun setListeners() {
        binding.buttonAdd.setOnClickListener { addPersonInfo() }
        binding.buttonPhotoCamera.setOnClickListener { choiceImageUploadOption() }
    }

    private fun addPersonInfo() {
        if (!photoWasLoaded) {
            pathToPicture = ""
        }
        val name = binding.etPersonName.text.toString()
        if (name.isNotEmpty()) {
            presenter.addData(PersonData(pathToPicture, name))
            backToMainFragment()
        } else {
            Snackbar.make(binding.etPersonName, "Fields can't be empty", Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun backToMainFragment() {
        loader.loadFragment(FragmentPersonListImpl(), FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PHOTO_FROM_GALLERY && resultCode == RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            pathToPicture = selectedImageUri.toString()
            binding.imageViewPhoto.setImageURI(selectedImageUri)
            photoWasLoaded = true
            binding.noPhoto.setVisibileOrNot(photoWasLoaded)
        } else {
            data?.extras?.get("data")?.run {
                pathToPicture =
                    saveImage(this as Bitmap, binding.imageViewPhoto, personPhotoDirectory)
                photoWasLoaded = true
                binding.noPhoto.setVisibileOrNot(photoWasLoaded)
            }
        }
    }

    private fun createDirectoryForPictures() {
        createDirectory(context as Context)?.run {
            personPhotoDirectory = this
        }
    }

    @SuppressLint("InflateParams")
    private fun choiceImageUploadOption() {
        val dialog = AlertDialog.Builder(context as Context)
        dialog.apply {
            setTitle(getString(R.string.upload_picture_alert_title))
            setMessage(getString(R.string.alertMessage))
            setNegativeButton(getString(R.string.load_photo_from_gallery)) { _, _ ->
                Intent().apply {
                    type = "image/*"
                    action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(
                        Intent.createChooser(this, "select picture"),
                        REQUEST_CODE_PHOTO_FROM_GALLERY
                    )
                }
            }
            setPositiveButton(getText(R.string.ok_button)) { _, _ ->
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, REQUEST_CODE_PHOTO_FROM_CAMERA)
            }
            create()
            show()
        }
    }
}
