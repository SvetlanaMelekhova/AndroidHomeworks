package by.htp.first.myproject.database

import androidx.room.*
import by.htp.first.myproject.entity.PersonData

@Dao
interface PersonDatabaseDAO {

    @Insert
    fun addPerson(personData: PersonData)

    @Delete
    fun deletePerson(personData: PersonData)

    @Query("SELECT * FROM person_data WHERE id = :id")
    fun getPerson(id: Long): PersonData

    @Query("SELECT * FROM person_data")
    fun getPersonList(): List<PersonData>

    @Update
    fun updatePerson(personData: PersonData)
}
