package by.htp.first.myproject.presenter

import by.htp.first.myproject.model.entity.PersonData

interface FragmentEditPersonPresenter {

    fun updateData(personData: PersonData)
    fun close()
}