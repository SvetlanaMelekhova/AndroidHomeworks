package by.htp.first.myproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.htp.first.myproject.entity.PersonData
import by.htp.first.myproject.entity.PersonScheduleData

@Database (entities = [PersonData::class, PersonScheduleData::class], version = 1, exportSchema = false)
abstract class PersonDatabase : RoomDatabase() {

    abstract fun getPersonDatabaseDAO() : PersonDatabaseDAO
    abstract fun getPersonScheduleDatabaseDAO() : PersonScheduleDatabaseDAO

    companion object{
        private var INSTANCE: PersonDatabase? = null
        fun getDatabase (context: Context) : PersonDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    PersonDatabase::class.java,
                    "person_info")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE as PersonDatabase
        }
    }
}
