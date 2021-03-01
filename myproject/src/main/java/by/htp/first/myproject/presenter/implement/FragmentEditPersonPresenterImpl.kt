package by.htp.first.myproject.presenter.implement

import by.htp.first.myproject.model.repository.impl.DatabaseRepositoryImpl
import by.htp.first.myproject.model.entity.PersonData
import by.htp.first.myproject.model.repository.DatabaseRepository
import by.htp.first.myproject.presenter.FragmentEditPersonPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FragmentEditPersonPresenterImpl: FragmentEditPersonPresenter {

    private val repository: DatabaseRepository = DatabaseRepositoryImpl()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main + Job())

    override fun updateData(personData: PersonData) {
        scope.launch { repository.updatePerson(personData) }
    }

    override fun close() {
        scope.cancel()
    }
}