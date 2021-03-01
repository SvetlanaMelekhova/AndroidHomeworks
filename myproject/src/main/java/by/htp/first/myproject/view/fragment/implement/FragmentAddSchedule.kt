package by.htp.first.myproject.view.fragment.implement

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import by.htp.first.myproject.R
import by.htp.first.myproject.databinding.FragmentAddPlanBinding
import by.htp.first.myproject.model.entity.PersonData
import by.htp.first.myproject.model.entity.PersonScheduleData
import by.htp.first.myproject.presenter.FragmentAddSchedulePresenter
import by.htp.first.myproject.presenter.implement.FragmentAddSchedulePresenterImpl
import by.htp.first.myproject.view.fragment.FragmentLoader
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone

class FragmentAddSchedule : Fragment(R.layout.fragment_add_plan) {

    private val presenter: FragmentAddSchedulePresenter = FragmentAddSchedulePresenterImpl()
    private lateinit var loader: FragmentLoader
    private lateinit var binding: FragmentAddPlanBinding
    private var personId: Long = 0
    private var personData: PersonData = PersonData("", "")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = requireActivity() as FragmentLoader
        loadDataFromBundle(requireArguments())
        binding = FragmentAddPlanBinding.bind(view)

        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Select a Date")
        val picker = builder.build()
        binding.buttonDate.setOnClickListener {
            picker.show(activity?.supportFragmentManager!!, picker.toString())
            picker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                val date = "${calendar.get(Calendar.DAY_OF_MONTH)}/ " +
                        "${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val d = sdf.parse(date)
                sdf.applyPattern("EEE, d MMM yyyy")
                val newDateString = sdf.format(d)
                binding.buttonDate.text = newDateString
            }
        }

        binding.buttonTime.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            val hh = c.get(Calendar.HOUR_OF_DAY)
            val mm = c.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(
                context as Context,
                R.style.DateAndTimePicker,
                { view, hourOfDay, minute ->
                    binding.buttonTime.text = "" + hourOfDay + ":" + minute
                }, hh, mm, true
            )
            timePickerDialog.show()
        }

        binding.buttonAdd.setOnClickListener {
            addPlan()
        }
    }

    private fun addPlan() {
        val plan = binding.etPlan.text.toString()
        val time = binding.buttonTime.text.toString()
        val data = binding.buttonDate.text.toString()

        if (plan.isNotEmpty() && time.isNotEmpty() && data.isNotEmpty() && time != "Time" && data != "Data") {
            presenter.addData(PersonScheduleData(data, time, plan, personId))
            backToMainFragment()
        } else {
            Snackbar.make(binding.etPlan, "Fields can't be empty", Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun backToMainFragment() {
        loader.loadFragment(
            FragmentScheduleListImpl::class.java, FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
            bundleOf("personId" to personId, "personData" to personData)
        )
    }

    private fun loadDataFromBundle(bundle: Bundle) {
        personId = bundle.getLong("personId", 0)
        personData = bundle.getParcelable<PersonData>("personData")!!
    }
}