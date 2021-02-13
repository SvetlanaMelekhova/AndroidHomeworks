package by.htp.first.homework7_2.database

import android.content.Context
import by.htp.first.homework7_2.entity.Car
import by.htp.first.homework7_2.entity.Work
import kotlinx.coroutines.*

class DatabaseRepository(context: Context) {


    private val database = CarDatabase.init(context)
    private val threadIO = Dispatchers.IO

    suspend fun addCar(car: Car) {
        withContext(threadIO) {
            database.getCarDatabaseDAO().addCarToDatabase(car)
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

    suspend fun updateCar(carData: Car) {
        withContext(threadIO) {
            database.getCarDatabaseDAO().update(carData)
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

    suspend fun updateWork(workData: Work) {
        withContext(threadIO) {
            database.getWorkDatabaseDAO().update(workData)
        }
    }

    suspend fun deleteWork(workData: Work) {
        withContext(threadIO) {
            database.getWorkDatabaseDAO().delete(workData)
        }
    }

    suspend fun addWorkToDatabase(workData: Work) {
        withContext(threadIO) {
            database.getWorkDatabaseDAO().addWorkToDatabase(workData)
        }
    }
}

