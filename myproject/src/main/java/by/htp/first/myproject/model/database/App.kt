package by.htp.first.myproject.model.database

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseRepository.initDatabase(this)
    }
}