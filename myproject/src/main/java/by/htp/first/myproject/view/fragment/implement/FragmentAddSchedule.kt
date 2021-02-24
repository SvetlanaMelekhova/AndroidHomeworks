package by.htp.first.myproject.view.fragment.implement

import android.app.DatePickerDialog
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
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar

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

        binding.buttonDate.setOnClickListener {

            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                context as Context,
                R.style.DateAndTimePicker,
                { view, year, monthOfYear, dayOfMonth ->
                    binding.buttonDate.text = SimpleDateFormat("EEE, d MMM yyyy").format(cal.time)
                }, y, m, d
            )
            datePickerDialog.show()
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

    /*suspend fun Context.openDateTimePicker(calendar: Calendar = Calendar.getInstance()): Instant =
        suspendCoroutine { continuation ->
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                    calendar
                        .apply { set(year, month, day, hour, minute) }
                        .run { Instant.ofEpochMilli(timeInMillis).truncatedTo(ChronoUnit.MINUTES) }
                        .let { continuation.resume(it) }
                }

                TimePickerDialog(
                    this,
                    timeSetListener,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    DateFormat.is24HourFormat(this)
                ).show()
            }

            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }*/

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