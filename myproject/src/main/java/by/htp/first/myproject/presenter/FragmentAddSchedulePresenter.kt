package by.htp.first.myproject.presenter

import by.htp.first.myproject.model.entity.PersonScheduleData

interface FragmentAddSchedulePresenter {

    fun addData(personScheduleData: PersonScheduleData)
    fun close()
}