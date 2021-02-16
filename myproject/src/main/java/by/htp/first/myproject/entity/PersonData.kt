package by.htp.first.myproject.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_data")
data class PersonData(
    @ColumnInfo val pathToPicture: String,
    @ColumnInfo val personName: String,
    @ColumnInfo val personImage: String?
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Long = 0

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
        id = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pathToPicture)
        parcel.writeString(personName)
        parcel.writeString(personImage)
        parcel.writeLong(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PersonData> {
        override fun createFromParcel(parcel: Parcel): PersonData {
            return PersonData(parcel)
        }

        override fun newArray(size: Int): Array<PersonData?> {
            return arrayOfNulls(size)
        }
    }
}
