package by.htp.first.myproject.view.fragment.implement

import android.annotation.SuppressLint
import android.app.Activity
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
import by.htp.first.myproject.databinding.FragmentEditPersonBinding
import by.htp.first.myproject.function.createDirectory
import by.htp.first.myproject.function.saveImage
import by.htp.first.myproject.function.setVisibileOrNot
import by.htp.first.myproject.model.entity.PersonData
import by.htp.first.myproject.presenter.FragmentEditPersonPresenter
import by.htp.first.myproject.presenter.implement.FragmentEditPersonPresenterImpl
import by.htp.first.myproject.view.fragment.FragmentLoader
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import java.io.File

private const val REQUEST_CODE_PHOTO_FROM_CAMERA = 1
private const val REQUEST_CODE_PHOTO_FROM_GALLERY = 2

class FragmentEditPerson : Fragment(R.layout.fragment_edit_person) {

    private val presenter: FragmentEditPersonPresenter = FragmentEditPersonPresenterImpl()
    private lateinit var loader: FragmentLoader
    private lateinit var binding: FragmentEditPersonBinding
    private lateinit var currentPersonData: PersonData
    private var personId: Long = 0
    private var photoWasLoaded: Boolean = false
    private lateinit var pathToPicture: String
    private lateinit var personPhotoDirectory: File

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = requireActivity() as FragmentLoader
        binding = FragmentEditPersonBinding.bind(view)
        loadDataFromIntent(requireArguments())
        binding.noPhoto.setVisibileOrNot(photoWasLoaded)
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
                val selectedImageUri = pathToPicture
                val file = File(path)
                if (file.exists()) {
                    if (path == "") {
                        photoWasLoaded = false
                        binding.imageViewPhoto.setImageResource(R.drawable.ic_twotone_face_24)
                    } else {
                        Glide.with(context as Context).load(path).into(binding.imageViewPhoto)
                        photoWasLoaded = true
                        this@FragmentEditPerson.pathToPicture = path
                    }
                } else {
                    Glide.with(context as Context).load(selectedImageUri)
                        .into(binding.imageViewPhoto)
                    photoWasLoaded = true
                    this@FragmentEditPerson.pathToPicture = selectedImageUri
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
            choiceImageUploadOption()
        }
    }

    private fun addPersonInfo() {
        val name = binding.etPersonName.text.toString()
        if (name.isNotEmpty()) {
            if (!photoWasLoaded) {
                pathToPicture = ""
            }
            val personData = PersonData(pathToPicture, name).also { it.id = personId }
            presenter.updateData(personData)
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
        if (requestCode == REQUEST_CODE_PHOTO_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
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