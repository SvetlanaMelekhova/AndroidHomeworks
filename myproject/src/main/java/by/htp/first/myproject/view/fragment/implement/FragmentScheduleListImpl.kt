package by.htp.first.myproject.view.fragment.implement

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import by.htp.first.myproject.R
import by.htp.first.myproject.view.fragment.adapter.PersonScheduleAdapter
import by.htp.first.myproject.databinding.FragmentScheduleListBinding
import by.htp.first.myproject.model.entity.PersonData
import by.htp.first.myproject.function.setVisibileOrNot
import by.htp.first.myproject.model.entity.PersonScheduleData
import by.htp.first.myproject.presenter.FragmentScheduleListPresenter
import by.htp.first.myproject.presenter.implement.FragmentScheduleListPresenterImpl
import by.htp.first.myproject.view.fragment.FragmentLoader
import by.htp.first.myproject.view.fragment.FragmentScheduleList
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentScheduleListImpl : Fragment(R.layout.fragment_schedule_list), FragmentScheduleList{

    private val presenter: FragmentScheduleListPresenter = FragmentScheduleListPresenterImpl(this)

    private lateinit var binding: FragmentScheduleListBinding
    private lateinit var loader: FragmentLoader
    private lateinit var personScheduleAdapter: PersonScheduleAdapter
    private lateinit var fab: FloatingActionButton
   private var personId: Long = 0
   // private var personName: String = ""
    private lateinit var personData: PersonData


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = requireActivity() as FragmentLoader
        binding = FragmentScheduleListBinding.bind(view)
        fab = view.rootView.findViewById(R.id.fabAdd)
        personScheduleAdapter = PersonScheduleAdapter()
        //databaseRepository = DatabaseRepository()
        getData(requireArguments())
      //  scope = CoroutineScope(Dispatchers.Main + Job())
        binding.recyclerViewSchedule.apply {

            //scope.launch { personScheduleAdapter.updateLists(databaseRepository.getAllPersonSchedule(personId))}
           // scope.launch { personScheduleAdapter.updateLists(databaseRepository.getAllPersonSchedule(personName))}

            adapter = personScheduleAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


            personScheduleAdapter.onScheduleInfoItemClickListener = {

            }
            presenter.fetchData(personId)
        }



        /*binding.recyclerViewPersonList.apply {
            personAdapter = PersonAdapter()
            activityScope.launch { personAdapter.updateList(databaseRepository.getPersonList()) }
            adapter = personAdapter
            layoutManager = GridLayoutManager(context as Context, 2)*/


            fab.setOnClickListener{
            loader.loadFragment(
                FragmentAddSchedule::class.java, FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
            bundleOf("personId" to personId, "personData" to personData))

                /*loader.loadFragment(FragmentAddWork::class.java,
                    FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
                    bundleOf("currentCarId" to currentCarId, "carInfo" to currentCar)
                )*/
        }


    }

    private fun getData(bundle: Bundle) {

            personData = bundle.getParcelable("personData") ?: PersonData("", "")
            personId = personData.id

    }

    override fun showData(list: List<PersonScheduleData>) {
        personScheduleAdapter.updateLists(list)
        binding.tvNoScheduleAdded.setVisibileOrNot(personScheduleAdapter.itemCount != 0)
    }
}