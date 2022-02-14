package by.htp.first.homework8_2.database

import androidx.room.*
import by.htp.first.homework8_2.entity.Car

@Dao
interface CarDatabaseDAO {

    @Query("SELECT * FROM Car")
    fun getCarsList(): List<Car>

    @Query("SELECT * FROM Car WHERE id = :carId")
    fun getCar(carId: Long): Car

    @Delete
    fun delete(car: Car)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(car: Car)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCarToDatabase(entity: Car)

}