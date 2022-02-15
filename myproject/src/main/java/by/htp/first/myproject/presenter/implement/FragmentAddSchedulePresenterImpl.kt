package by.htp.first.myproject.presenter.implement

import by.htp.first.myproject.model.entity.PersonScheduleData
import by.htp.first.myproject.model.repository.DatabaseRepository
import by.htp.first.myproject.model.repository.impl.DatabaseRepositoryImpl
import by.htp.first.myproject.presenter.FragmentAddSchedulePresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FragmentAddSchedulePresenterImpl: FragmentAddSchedulePresenter {

    private val  repository: DatabaseRepository = DatabaseRepositoryImpl()
    private val scope: CoroutineScope = CoroutineScope (Dispatchers.Main + Job())

    override fun addData(personScheduleData: PersonScheduleData) {
        scope.launch { repository.addPersonSchedule(personScheduleData)}
    }

    override fun close() {

    }
}