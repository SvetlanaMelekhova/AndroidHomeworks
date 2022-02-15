package by.htp.first.homework8_1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.htp.first.homework8_1.entity.Contact


@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun getContactDatabaseDAO(): ContactDatabaseDAO

    companion object {
        fun init(context: Context) = Room.databaseBuilder(context, ContactDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()
    }
}