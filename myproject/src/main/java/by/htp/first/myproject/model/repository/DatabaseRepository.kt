package by.htp.first.myproject.model.repository

import by.htp.first.myproject.model.entity.PersonData
import by.htp.first.myproject.model.entity.PersonScheduleData

interface DatabaseRepository {

    suspend fun addPerson(personData: PersonData)

    suspend fun deletePerson(personData: PersonData)

    suspend fun getPerson(id: Long): PersonData

    suspend fun getPersonList(): List<PersonData>

    suspend fun updatePerson(personData: PersonData)

    suspend fun addPersonSchedule(personScheduleData: PersonScheduleData)

    suspend fun deletePersonSchedule(personScheduleData: PersonScheduleData)

    suspend fun getPersonSchedule(id: Long): PersonScheduleData

    suspend fun getAllPersonSchedule(personId: Long): List<PersonScheduleData>

    suspend fun getPersonScheduleList(): List<PersonScheduleData>

    suspend fun updatePersonSchedule(personScheduleData: PersonScheduleData)

}