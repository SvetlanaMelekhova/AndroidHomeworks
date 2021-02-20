package by.htp.first.myproject.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import by.htp.first.myproject.R
import by.htp.first.myproject.database.DatabaseRepository
import by.htp.first.myproject.databinding.FragmentAddPlanBinding
import by.htp.first.myproject.entity.PersonData
import by.htp.first.myproject.entity.PersonScheduleData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class FragmentAddSchedule : Fragment(R.layout.fragment_add_plan) {

    private lateinit var loader: FragmentLoader
    private lateinit var binding: FragmentAddPlanBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var scope: CoroutineScope
    private lateinit var personName: String
    private  var personData: PersonData = PersonData("", "")



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = requireActivity() as FragmentLoader
        loadDataFromBundle(requireArguments())
        binding = FragmentAddPlanBinding.bind(view)
        repository = DatabaseRepository(context as Context)
        scope = CoroutineScope(Dispatchers.Main + Job())

       binding.buttonDate.setOnClickListener {

            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(context as Context, R.style.DateAndTimePicker,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        binding.buttonDate.text = SimpleDateFormat("EEE, d MMM yyyy").format(cal.time)
                    }, y, m, d
                )

           datePickerDialog.show()
        }

        binding.buttonTime.setOnClickListener {

            val c: Calendar = Calendar.getInstance()
            val hh = c.get(Calendar.HOUR_OF_DAY)
            val mm = c.get(Calendar.MINUTE)
            val timePickerDialog: TimePickerDialog = TimePickerDialog(
                context as Context, R.style.DateAndTimePicker,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    binding.buttonTime.text = "" + hourOfDay + ":" + minute
                }, hh, mm, true
            )
            timePickerDialog.show()
        }

        binding.buttonAdd.setOnClickListener{
            addPlan()
        }
    }

    private fun addPlan() {
        val plan = binding.etPlan.text.toString()
        val time = binding.buttonTime.text.toString()
        val data = binding.buttonDate.text.toString()

       // if (plan.isNotEmpty()) {
            scope.launch { repository.addPersonSchedule(
                PersonScheduleData(
                    data,
                    time,
                    plan,
                    personName
                )
            ) }
            backToMainFragment()
        /*} else {
            Snackbar.make(
                fragmentAddPersonBinding.etPersonName,
                "Fields can't be empty",
                Snackbar.LENGTH_LONG
            )
                .show()
            //Toast.makeText(context, "Fields can't be empty", Toast.LENGTH_SHORT).show()
        }*/
    }


    private fun backToMainFragment() {
        loader.loadFragment(
            FragmentScheduleList::class.java, FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
            bundleOf("personName" to personName, "personData" to personData)
        )
    }

    private fun loadDataFromBundle(bundle: Bundle) {
        personName = bundle.getString("personName", "")
        personData = bundle.getParcelable<PersonData>("personData")!!
    }
}