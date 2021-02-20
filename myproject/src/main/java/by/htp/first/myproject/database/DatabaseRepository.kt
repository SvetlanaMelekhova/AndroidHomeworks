package by.htp.first.myproject.database

import android.content.Context
import by.htp.first.myproject.entity.PersonData
import by.htp.first.myproject.entity.PersonScheduleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseRepository(context: Context) {

    private val database = PersonDatabase.getDatabase(context)
    private val threadIO = Dispatchers.IO

    suspend fun addPerson(personData: PersonData) {
        withContext(threadIO) {
            database.getPersonDatabaseDAO().addPerson(personData)
        }
    }

    suspend fun deletePerson(personData: PersonData) {
        withContext(threadIO) {
            database.getPersonDatabaseDAO().deletePerson(personData)
        }
    }

    suspend fun getPerson(id: Long): PersonData {
        return withContext(threadIO) {
            database.getPersonDatabaseDAO().getPerson(id)
        }
    }

    suspend fun getPersonList(): List<PersonData> {
        return withContext(threadIO) {
            database.getPersonDatabaseDAO().getPersonList()
        }
    }

    suspend fun updatePerson(personData: PersonData) {
        withContext(threadIO) {
            database.getPersonDatabaseDAO().updatePerson(personData)
        }
    }

    suspend fun addPersonSchedule(personScheduleData: PersonScheduleData) {
        withContext(threadIO) {
            database.getPersonScheduleDatabaseDAO().addPersonSchedule(personScheduleData)
        }
    }

    suspend fun deletePersonSchedule(personScheduleData: PersonScheduleData) {
            withContext(threadIO) {
                database.getPersonScheduleDatabaseDAO().deletePersonSchedule(personScheduleData)
            }
    }

    suspend fun getPersonSchedule(id: Long): PersonScheduleData{
        return withContext(threadIO) {
            database.getPersonScheduleDatabaseDAO().getPersonSchedule(id)
        }
    }

    /*suspend fun getAllPersonSchedule(personId: Long): List<PersonScheduleData>{
        return withContext(threadIO) {
            database.getPersonScheduleDatabaseDAO().getAllPersonSchedule(personId)
        }
    }*/

    suspend fun getAllPersonSchedule(personName: String): List<PersonScheduleData>{
        return withContext(threadIO) {
            database.getPersonScheduleDatabaseDAO().getAllPersonSchedule(personName)
        }
    }

    suspend fun getPersonScheduleList(): List<PersonScheduleData> {
        return withContext(threadIO) {
            database.getPersonScheduleDatabaseDAO().getPersonScheduleList()
        }
    }

    suspend fun updatePersonSchedule(personScheduleData: PersonScheduleData) {
            withContext(threadIO) {
                database.getPersonScheduleDatabaseDAO().updatePersonSchedule(personScheduleData)
            }
    }
}
