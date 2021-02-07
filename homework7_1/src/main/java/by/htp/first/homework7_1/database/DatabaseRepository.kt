package by.htp.first.homework7_1.database

import android.content.Context
import by.htp.first.homework7_1.entity.Car
import by.htp.first.homework7_1.entity.Work
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class DatabaseRepository(context: Context) {

    private val database = CarDatabase.init(context)

    fun addCar(car: Car) {
        Single.create<Car> {
            database.getCarDatabaseDAO().addCarToDatabase(car)
        }.subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun getCarsList(): List<Car> {
        return Single.create<List<Car>> {
            val list = database.getCarDatabaseDAO().getCarsList()
            it.onSuccess(list)
        }.subscribeOn(Schedulers.io())
                .blockingGet()
    }

    fun getCar(carId: Long): Car {
        return Single.create<Car> {
            val car = database.getCarDatabaseDAO().getCar(carId)
            it.onSuccess(car)
        }.subscribeOn(Schedulers.io())
                .blockingGet()
    }

    fun updateCar(carData: Car) {
        Single.create<Car> {
            database.getCarDatabaseDAO().update(carData)
        }.subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun getParentWorks(parentCar: String?): List<Work> {
        return Single.create<List<Work>> {
            val list = database.getWorkDatabaseDAO().getParentWorks(parentCar)
            it.onSuccess(list)
        }.subscribeOn(Schedulers.io())
                .blockingGet()
    }

    fun getWork(workId: Long): Work {
        return Single.create<Work> {
            val work = database.getWorkDatabaseDAO().getWork(workId)
            it.onSuccess(work)
        }.subscribeOn(Schedulers.io())
                .blockingGet()
    }

    fun updateWork(workData: Work) {
        Single.create<Work> {
            database.getWorkDatabaseDAO().update(workData)
        }.subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun deleteWork(workData: Work) {
        Single.create<Work> {
            database.getWorkDatabaseDAO().delete(workData)
        }.subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun addWorkToDatabase(workData: Work) {
        Single.create<Work> {
            database.getWorkDatabaseDAO().addWorkToDatabase(workData)
        }.subscribeOn(Schedulers.io())
                .subscribe()
    }
}