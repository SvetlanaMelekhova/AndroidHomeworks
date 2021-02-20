package by.htp.first.myproject.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import by.htp.first.myproject.R
import by.htp.first.myproject.adapter.PersonAdapter
import by.htp.first.myproject.database.DatabaseRepository
import by.htp.first.myproject.databinding.FragmentPersonListBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FragmentPersonList : Fragment(R.layout.fragment_person_list) {

    private lateinit var loader: FragmentLoader
    private lateinit var personAdapter: PersonAdapter
    private lateinit var databaseRepository: DatabaseRepository
    private lateinit var binding: FragmentPersonListBinding
    private lateinit var activityScope: CoroutineScope

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = requireActivity() as FragmentLoader

        databaseRepository = DatabaseRepository(context as Context)
        activityScope = CoroutineScope(Dispatchers.Main + Job())
        binding = FragmentPersonListBinding.bind(view)
        binding.recyclerViewPersonList.apply {
            personAdapter = PersonAdapter()
            activityScope.launch { personAdapter.updateList(databaseRepository.getPersonList()) }
            adapter = personAdapter
            layoutManager = GridLayoutManager(context as Context, 2)

            personAdapter.onEditIconClickListener = {
                 loader.loadFragment(FragmentEditPerson::class.java,
                    FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
                    bundleOf("personData" to it)
                )
            }
            personAdapter.onPersonScheduleInfoListListClickListener = {
                 loader.loadFragment(FragmentScheduleList::class.java,
                    FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
                    bundleOf("personData" to it)
                )
            }
        }

        binding.fabAddPerson.setOnClickListener {
            loader.loadFragment(FragmentAddPerson(), FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
        }
    }
}


