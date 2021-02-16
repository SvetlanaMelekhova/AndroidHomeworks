package by.htp.first.myproject.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_schedule")
data class PersonScheduleData(
    @ColumnInfo val date: String,
    @ColumnInfo val time: String,
    @ColumnInfo val plan: String,
    @ColumnInfo val personId: Long = -1
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Long = 0



    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readLong()
    ) {
        id = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeString(plan)
        parcel.writeLong(personId)
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
}
