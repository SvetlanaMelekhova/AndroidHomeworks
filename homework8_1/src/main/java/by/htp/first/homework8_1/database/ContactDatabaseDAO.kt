package by.htp.first.homework8_1.database

import androidx.room.*
import by.htp.first.homework8_1.entity.Contact

@Dao
interface ContactDatabaseDAO {

    @Query("SELECT * FROM Contact")
    fun getContactList(): List<Contact>

    @Query("SELECT * FROM Contact WHERE id = :id")
    fun getContact(id: Long): Contact

    @Delete
    fun delete(contact: Contact)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContact(entity: Contact)


}