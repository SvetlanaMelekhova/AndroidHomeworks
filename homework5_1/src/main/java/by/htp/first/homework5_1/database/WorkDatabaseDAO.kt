package by.htp.first.homework5_1.database

import androidx.room.*
import by.htp.first.homework5_1.entity.Work

@Dao
interface WorkDatabaseDAO {

    @Query("SELECT * FROM Work")
    fun getCarsList(): List<Work>

    @Query("SELECT * FROM Work WHERE parentCar LIKE :parentCar")
    fun getParentWorks(parentCar: String?): List<Work>

    @Query("SELECT * FROM Work WHERE id = :workId")
    fun getWork(workId: Long): Work

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(work: Work)

    @Delete
    fun delete(car: Work)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCarToDatabase(entity: Work)
}