package by.htp.first.myproject.view.fragment.implement

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import by.htp.first.myproject.R
import by.htp.first.myproject.view.fragment.FragmentLoader
import io.mahendra.calendarview.widget.CalendarView
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FragmentCalendarView: Fragment(R.layout.fragment_calendar) {

    private lateinit var loader: FragmentLoader
    private lateinit var calendarView: CalendarView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = requireActivity() as FragmentLoader
        calendarView =  view.findViewById(R.id.cal)
        calendarView.setFirstDayOfWeek(Calendar.MONDAY)
        calendarView.setIsOverflowDateVisible(false)
        calendarView.setCurrentDay( Date(System.currentTimeMillis()))
        calendarView.setBackButtonColor(R.color.colorAccent)
        calendarView.setNextButtonColor(R.color.colorAccent)

        calendarView.refreshCalendar(Calendar.getInstance(Locale.getDefault()))

        calendarView.setOnDateClickListener{
            loader.loadFragment(FragmentPersonListImpl(), FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
        }
    }
}