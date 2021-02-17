package by.htp.first.myproject.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.htp.first.myproject.R
import by.htp.first.myproject.adapter.PersonAdapter
import by.htp.first.myproject.adapter.PersonScheduleAdapter
import by.htp.first.myproject.database.DatabaseRepository
import by.htp.first.myproject.databinding.FragmentPersonListBinding
import by.htp.first.myproject.databinding.ItemViewPersonBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class PersonListFragment : Fragment(R.layout.fragment_person_list) {

    private lateinit var loader: FragmentLoader

    private lateinit var personAdapter: PersonAdapter
    private lateinit var textView: TextView
    private lateinit var imageButton: ImageButton
    private lateinit var fab: FloatingActionButton

    private lateinit var databaseRepository: DatabaseRepository
    private lateinit var binding: FragmentPersonListBinding
    private lateinit var activityScope: CoroutineScope


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = requireActivity() as FragmentLoader
        fab = view.findViewById(R.id.fabAddPerson)
        databaseRepository = DatabaseRepository(context as Context)
        activityScope = CoroutineScope(Dispatchers.Main + Job())
        binding = FragmentPersonListBinding.bind(view)
        binding.recyclerViewPersonList.apply {
            personAdapter = PersonAdapter(ArrayList())
                layoutManager = GridLayoutManager(context as Context, 2)
                adapter = personAdapter
        }

        fab.setOnClickListener {
            loader.loadFragment(FragmentAddPerson(),
                FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }

    }




}


