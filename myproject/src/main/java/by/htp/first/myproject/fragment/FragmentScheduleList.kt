package by.htp.first.myproject.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.htp.first.myproject.R
import by.htp.first.myproject.adapter.PersonAdapter
import by.htp.first.myproject.adapter.PersonScheduleAdapter
import by.htp.first.myproject.database.DatabaseRepository
import by.htp.first.myproject.databinding.FragmentScheduleListBinding
import by.htp.first.myproject.entity.PersonData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FragmentScheduleList : Fragment(R.layout.fragment_schedule_list){

    private lateinit var binding: FragmentScheduleListBinding
    private lateinit var loader: FragmentLoader
    private lateinit var personScheduleAdapter: PersonScheduleAdapter
    private lateinit var scope: CoroutineScope
    private lateinit var databaseRepository: DatabaseRepository
   // private var personId: Long = 0
    private var personName: String = ""
    private lateinit var personData: PersonData


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = requireActivity() as FragmentLoader
        binding = FragmentScheduleListBinding.bind(view)
        databaseRepository = DatabaseRepository(context as Context)
        getData(requireArguments())
        scope = CoroutineScope(Dispatchers.Main + Job())
        binding.recyclerViewSchedule.apply {
           personScheduleAdapter = PersonScheduleAdapter()
            //scope.launch { personScheduleAdapter.updateLists(databaseRepository.getAllPersonSchedule(personId))}
            scope.launch { personScheduleAdapter.updateLists(databaseRepository.getAllPersonSchedule(personName))}

            adapter = personScheduleAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            personScheduleAdapter.onScheduleInfoItemClickListener = {

            }
        }

        /*binding.recyclerViewPersonList.apply {
            personAdapter = PersonAdapter()
            activityScope.launch { personAdapter.updateList(databaseRepository.getPersonList()) }
            adapter = personAdapter
            layoutManager = GridLayoutManager(context as Context, 2)*/


            binding.fabAddPlan.setOnClickListener{
            loader.loadFragment(FragmentAddSchedule::class.java, FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
            bundleOf("personName" to personName, "personData" to personData))

                /*loader.loadFragment(FragmentAddWork::class.java,
                    FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
                    bundleOf("currentCarId" to currentCarId, "carInfo" to currentCar)
                )*/
        }
    }

    private fun getData(bundle: Bundle) {
        personData = bundle.getParcelable("personData") ?: PersonData(  "", "")
        personName = personData.personName
    }
}