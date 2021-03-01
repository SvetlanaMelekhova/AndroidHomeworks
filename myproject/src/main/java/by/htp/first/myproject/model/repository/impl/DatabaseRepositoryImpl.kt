package by.htp.first.myproject.model.repository.impl

import android.content.Context
import by.htp.first.myproject.model.entity.PersonData
import by.htp.first.myproject.model.entity.PersonScheduleData
import by.htp.first.myproject.model.repository.DatabaseRepository
import by.htp.first.myproject.presenter.database.PersonDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseRepositoryImpl(): DatabaseRepository {

    companion object{
        private lateinit var database: PersonDatabase
        fun initDatabase(context: Context){
            database = PersonDatabase.getDatabase(context)
        }
    }

    private val threadIO = Dispatchers.IO

    override suspend fun addPerson(personData: PersonData) {
        withContext(threadIO) {
            database.getPersonDatabaseDAO().addPerson(personData)
        }
    }

    override suspend fun deletePerson(personData: PersonData) {
        withContext(threadIO) {
            database.getPersonDatabaseDAO().deletePerson(personData)
        }
    }

    override suspend fun getPerson(id: Long): PersonData {
        return withContext(threadIO) {
            database.getPersonDatabaseDAO().getPerson(id)
        }
    }

    override suspend fun getPersonList(): List<PersonData> {
        return withContext(threadIO) {
            database.getPersonDatabaseDAO().getPersonList()
        }
    }

    override suspend fun updatePerson(personData: PersonData) {
        withContext(threadIO) {
            database.getPersonDatabaseDAO().updatePerson(personData)
        }
    }

    override suspend fun addPersonSchedule(personScheduleData: PersonScheduleData) {
        withContext(threadIO) {
            database.getPersonScheduleDatabaseDAO().addPersonSchedule(personScheduleData)
        }
    }

    override suspend fun deletePersonSchedule(personScheduleData: PersonScheduleData) {
            withContext(threadIO) {
                database.getPersonScheduleDatabaseDAO().deletePersonSchedule(personScheduleData)
            }
    }

    override suspend fun getPersonSchedule(id: Long): PersonScheduleData{
        return withContext(threadIO) {
            database.getPersonScheduleDatabaseDAO().getPersonSchedule(id)
        }
    }

    /*suspend fun getAllPersonSchedule(personId: Long): List<PersonScheduleData>{
        return withContext(threadIO) {
            database.getPersonScheduleDatabaseDAO().getAllPersonSchedule(personId)
        }
    }*/

    override suspend fun getAllPersonSchedule(personId: Long): List<PersonScheduleData>{
        return withContext(threadIO) {
            database.getPersonScheduleDatabaseDAO().getAllPersonSchedule(personId)
        }
    }

    override suspend fun getPersonScheduleList(): List<PersonScheduleData> {
        return withContext(threadIO) {
            database.getPersonScheduleDatabaseDAO().getPersonScheduleList()
        }
    }

    override suspend fun updatePersonSchedule(personScheduleData: PersonScheduleData) {
            withContext(threadIO) {
                database.getPersonScheduleDatabaseDAO().updatePersonSchedule(personScheduleData)
            }
    }
}
