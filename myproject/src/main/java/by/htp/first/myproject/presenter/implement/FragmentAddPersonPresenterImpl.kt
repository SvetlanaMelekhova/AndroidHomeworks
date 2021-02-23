package by.htp.first.myproject.presenter.implement

import by.htp.first.myproject.model.database.DatabaseRepository
import by.htp.first.myproject.model.entity.PersonData
import by.htp.first.myproject.presenter.FragmentAddPersonPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FragmentAddPersonPresenterImpl: FragmentAddPersonPresenter {

    private val repository: DatabaseRepository = DatabaseRepository()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main + Job())


    override fun addData(personData: PersonData) {
        scope.launch { repository.addPerson(personData) }
    }

    override fun close() {
        scope.cancel()
    }
}