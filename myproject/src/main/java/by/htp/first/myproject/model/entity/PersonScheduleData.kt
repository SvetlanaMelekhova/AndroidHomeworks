package by.htp.first.myproject.model.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_schedule")
data class PersonScheduleData(
   @ColumnInfo val date: String,
   // @ColumnInfo var date: Date = Date(),
    @ColumnInfo var time: String,
    @ColumnInfo val plan: String,
    //@ColumnInfo val personId: Long = -1
    @ColumnInfo val personName: String
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Long = 0



    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        //parcel.readDate(),
       // parcel.readSerializable() as Date,
        parcel.readString().toString(),
        parcel.readString().toString(),
       // parcel.readLong()
    parcel.readString().toString()
    ) {
        id = parcel.readLong()
        //date = parcel.read
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        //parcel.writeDate(date)
        //parcel.writeSerializable(date)
        parcel.writeString(time)
        parcel.writeString(plan)
        //parcel.writeLong(personId)
        parcel.writeString(personName)
        parcel.writeLong(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PersonScheduleData> {
        override fun createFromParcel(parcel: Parcel): PersonScheduleData {
            return PersonScheduleData(parcel)
        }

        override fun newArray(size: Int): Array<PersonScheduleData?> {
            return arrayOfNulls(size)
        }
    }

    /*fun Parcel.writeDate(date: Date?) {
        writeLong(date?.time ?: -1)
    }

    fun Parcel.readDate(): Date? {
        val long = readLong()
        return if (long != -1L) Date(long) else null
    }*/
}
