package by.htp.first.myproject.presenter

import by.htp.first.myproject.model.entity.PersonData

interface FragmentAddPersonPresenter {

        fun addData(personData: PersonData)
        fun close()
}