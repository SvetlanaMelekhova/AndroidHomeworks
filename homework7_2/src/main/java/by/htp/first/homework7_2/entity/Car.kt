package by.htp.first.homework7_2.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Car(@ColumnInfo val carOwnerName: String,
               @ColumnInfo val carProducerName: String,
               @ColumnInfo val carModelName: String,
               @ColumnInfo val carPlateNumber: String,
               @ColumnInfo val carImage: String?) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Long? = null

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()) {
        parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(carOwnerName)
        parcel.writeString(carProducerName)
        parcel.writeString(carModelName)
        parcel.writeString(carPlateNumber)
        parcel.writeString(carImage)
        parcel.writeLong(id ?: -1)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Car> {
        override fun createFromParcel(parcel: Parcel): Car {
            return Car(parcel)
        }

        override fun newArray(size: Int): Array<Car?> {
            return arrayOfNulls(size)
        }
    }
}