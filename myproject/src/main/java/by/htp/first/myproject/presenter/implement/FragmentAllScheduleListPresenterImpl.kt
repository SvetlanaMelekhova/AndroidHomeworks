package by.htp.first.myproject.presenter.implement

import by.htp.first.myproject.model.database.DatabaseRepository
import by.htp.first.myproject.presenter.FragmentAllScheduleListPresenter
import by.htp.first.myproject.view.fragment.FragmentAllScheduleListView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FragmentAllScheduleListPresenterImpl
    (private var view: FragmentAllScheduleListView?): FragmentAllScheduleListPresenter {

    private val repository: DatabaseRepository = DatabaseRepository()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main + Job())

    override fun fetchData() {
        scope.launch {
           val list = repository.getPersonScheduleList()
            view?.showData(list)
        }
    }

    override fun close() {
       scope.cancel()
       view = null
    }
}