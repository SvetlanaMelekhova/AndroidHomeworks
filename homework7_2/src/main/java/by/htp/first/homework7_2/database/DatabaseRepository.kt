package by.htp.first.homework7_2.database

import android.content.Context
import by.htp.first.homework7_2.entity.Car
import by.htp.first.homework7_2.entity.Work
import kotlinx.coroutines.*

class DatabaseRepository(context: Context) {

    private val mainScope = CoroutineScope(Dispatchers.Main + Job())
    private val database = CarDatabase.init(context)
    private val threadIO = Dispatchers.IO

    fun mainScope() = mainScope


    fun addCar(car: Car) {
        mainScope.launch {
            withContext(threadIO) {
                database.getCarDatabaseDAO().addCarToDatabase(car)
            }
        }
    }

    suspend fun getCarsList(): List<Car> {
        return withContext(threadIO) {
            database.getCarDatabaseDAO().getCarsList()
        }
    }

    suspend fun getCar(carId: Long): Car {
        return withContext(threadIO) {
            database.getCarDatabaseDAO().getCar(carId)
        }
    }

    fun updateCar(carData: Car) {
        mainScope.launch {
            withContext(threadIO) {
                database.getCarDatabaseDAO().update(carData)
            }
        }
    }

    suspend fun getParentWorks(parentCar: String?): List<Work> {
        return withContext(threadIO) {
            database.getWorkDatabaseDAO().getParentWorks(parentCar)
        }
    }

    suspend fun getWork(workId: Long): Work {
        return withContext(threadIO) {
            database.getWorkDatabaseDAO().getWork(workId)
        }
    }

    fun updateWork(workData: Work) {
        mainScope.launch {
            withContext(threadIO) {
                database.getWorkDatabaseDAO().update(workData)
            }
        }
    }

    fun deleteWork(workData: Work) {
        mainScope.launch {
            withContext(threadIO) {
                database.getWorkDatabaseDAO().delete(workData)
            }
        }
    }

    fun addWorkToDatabase(workData: Work) {
        mainScope.launch {
            withContext(threadIO) {
                database.getWorkDatabaseDAO().addWorkToDatabase(workData)
            }
        }
    }
}

