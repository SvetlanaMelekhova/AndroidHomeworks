package by.htp.first.myproject.presenter.implement

import by.htp.first.myproject.model.repository.DatabaseRepository
import by.htp.first.myproject.model.repository.impl.DatabaseRepositoryImpl
import by.htp.first.myproject.presenter.FragmentPersonListPresenter
import by.htp.first.myproject.view.fragment.FragmentPersonList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FragmentPersonListPresenterImpl(
    private var view: FragmentPersonList?): FragmentPersonListPresenter {

    private val repository: DatabaseRepository = DatabaseRepositoryImpl()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main + Job())

    override fun fetchData() {
        scope.launch {
            val list = repository.getPersonList()
            view?.showData(list)}
    }

    override fun close() {
        view = null
        scope.cancel()
    }
}