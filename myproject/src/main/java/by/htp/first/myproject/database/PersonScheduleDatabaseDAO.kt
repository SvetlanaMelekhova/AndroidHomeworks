package by.htp.first.myproject.database


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import by.htp.first.myproject.entity.PersonScheduleData


@Dao
interface PersonScheduleDatabaseDAO {

    @Insert
    fun addPersonSchedule(personScheduleData: PersonScheduleData)

    @Delete
    fun deletePersonSchedule(personScheduleData: PersonScheduleData)

    @Query("SELECT * FROM person_schedule WHERE id = :id")
    fun getPersonSchedule(id: Long): PersonScheduleData

    @Query("SELECT * FROM person_schedule WHERE personId LIKE :personId")
    fun getAllPersonSchedule(personId: Long): List<PersonScheduleData>

    @Query("SELECT * FROM person_schedule")
    fun getPersonScheduleList(): List<PersonScheduleData>

    @Update
    fun updatePersonSchedule(personScheduleData: PersonScheduleData)

}
