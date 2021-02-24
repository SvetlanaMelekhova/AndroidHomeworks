package by.htp.first.myproject.view.fragment.implement

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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

       // calendarView.setCalendarTitleTextColor(R.color.defaultColorPrimary)
       // calendarView.setDayOfWeekTextColor(R.color.defaultColorPrimary)



        calendarView.refreshCalendar(Calendar.getInstance(Locale.getDefault()))

    }

    /*calendarView.setOnDateLongClickListener(new CalendarView.OnDateLongClickListener() {
        @Override
        public void onDateLongClick(@NonNull Date selectedDate) {

            //OnDateLongClick Action here

        }
    });


    calendarView.setOnMonthChangedListener(new CalendarView.OnMonthChangedListener() {
        @Override
        public void onMonthChanged(@NonNull Date monthDate) {

            //OnMonthChanged Action Here

        }
    });
    calendarView.setOnDateClickListener(new CalendarView.OnDateClickListener() {
        @Override
        public void onDateClick(@NonNull Date selectedDate) {

            //OnDateClick Action Here

        }
    });

    calendarView.setOnMonthTitleClickListener(new CalendarView.OnMonthTitleClickListener() {
        @Override
        public void onMonthTitleClick(@NonNull Date selectedDate) {

            // OnMonthTitleClick Action here

        }
    });*/

}