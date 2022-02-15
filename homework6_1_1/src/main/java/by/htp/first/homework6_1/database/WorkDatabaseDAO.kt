package by.htp.first.homework6_1.database

import android.database.Cursor
import androidx.room.*
import by.htp.first.homework6_1.entity.Work

@Dao
interface WorkDatabaseDAO {

    @Query("SELECT * FROM work_info")
    fun getCarsList(): List<Work>

    @Query("SELECT * FROM work_info WHERE parentCar LIKE :parentCar")
    fun getParentWorks(parentCar: String?): List<Work>

    @Query("SELECT * FROM work_info WHERE id = :workId")
    fun getWork(workId: Long): Work

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(work: Work)

    @Delete
    fun delete(car: Work)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCarToDatabase(entity: Work)

    @Query("SELECT * FROM work_info")
    fun getWorkList(): Cursor?
}