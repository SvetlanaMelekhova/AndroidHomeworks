package by.htp.first.myproject.view.fragment.implement

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import by.htp.first.myproject.R
import by.htp.first.myproject.databinding.FragmentAddPersonBinding
import by.htp.first.myproject.function.createDirectory
import by.htp.first.myproject.function.saveImage
import by.htp.first.myproject.function.setVisibileOrNot
import by.htp.first.myproject.model.entity.PersonData
import by.htp.first.myproject.presenter.implement.FragmentAddPersonPresenterImpl
import by.htp.first.myproject.view.fragment.FragmentLoader
import com.google.android.material.snackbar.Snackbar
import java.io.File


private const val REQUEST_CODE_PHOTO = 1

class FragmentAddPerson : Fragment(R.layout.fragment_add_person) {

    private val presenter: FragmentAddPersonPresenterImpl = FragmentAddPersonPresenterImpl()
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
        /* fragmentAddPersonBinding..setOnClickListener {
            backToMainFragment()
        }*/
        binding.buttonAdd.setOnClickListener {
            addPersonInfo()
        }
        binding.buttonPhotoCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CODE_PHOTO)
        }
    }

    private fun addPersonInfo() {
        if (!photoWasLoaded) { pathToPicture = "" }
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
        data?.extras?.get("data")?.run {
            pathToPicture = saveImage(this as Bitmap, binding.imageViewPhoto, personPhotoDirectory)
            photoWasLoaded = true
            binding.imageViewPhoto.setVisibileOrNot(photoWasLoaded)
        }
    }

    private fun createDirectoryForPictures() {
        createDirectory(context as Context)?.run {
            personPhotoDirectory = this
        }
    }
}