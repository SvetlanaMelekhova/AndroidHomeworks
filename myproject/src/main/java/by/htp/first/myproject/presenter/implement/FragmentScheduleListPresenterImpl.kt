package by.htp.first.myproject.presenter.implement

import by.htp.first.myproject.model.database.DatabaseRepository
import by.htp.first.myproject.presenter.FragmentScheduleListPresenter
import by.htp.first.myproject.view.fragment.FragmentScheduleList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FragmentScheduleListPresenterImpl(
    private var view: FragmentScheduleList?
) : FragmentScheduleListPresenter {

    private val repository: DatabaseRepository = DatabaseRepository()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main + Job())

    override fun fetchData(personName: String) {
        scope.launch {
            val list = repository.getAllPersonSchedule(personName)
            view?.showData(list)
        }
    }

    override fun close() {
        view = null
        scope.cancel()
    }
}