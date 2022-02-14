package by.htp.first.homework5

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

class Car(
        private val ownerName: String?,
        private val producer: String?,
        private val model: String?,
        private val gosNumber: String?,
        private val image: Bitmap?) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Bitmap::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ownerName)
        parcel.writeString(producer)
        parcel.writeString(model)
        parcel.writeString(gosNumber)
        parcel.writeParcelable(image, flags)
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


