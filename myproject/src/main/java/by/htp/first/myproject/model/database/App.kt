package by.htp.first.myproject.model.database

import android.app.Application
import by.htp.first.myproject.model.repository.impl.DatabaseRepositoryImpl

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseRepositoryImpl.initDatabase(this)
    }
}