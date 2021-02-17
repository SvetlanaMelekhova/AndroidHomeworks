package by.htp.first.myproject.fragment

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import by.htp.first.myproject.R
import by.htp.first.myproject.database.DatabaseRepository
import by.htp.first.myproject.databinding.FragmentAddPersonBinding

class FragmentAddPerson: Fragment(R.layout.fragment_add_person) {

    private lateinit var loader: FragmentLoader
    private lateinit var repository: DatabaseRepository
    private lateinit var addPhotoButton: ImageButton
    private lateinit var fragmentAddPersonBinding: FragmentAddPersonBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = requireActivity() as FragmentLoader
        fragmentAddPersonBinding = FragmentAddPersonBinding.bind(view)
        repository = DatabaseRepository(context)


        repository.addPerson()








    }
}