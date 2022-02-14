package by.htp.first.homework8_1.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact implements  Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    private Long id;
    @ColumnInfo
    private final int image;
    @ColumnInfo
    private final String name;
    @ColumnInfo
    private final String phoneNumberOrEmail;

    public Contact(int image, String name, String phoneNumberOrEmail) {
        this.image = image;
        this.name = name;
        this.phoneNumberOrEmail = phoneNumberOrEmail;
    }

    private Contact(Parcel in) {
        id = in.readLong();
        image = in.readInt();
        name = in.readString();
        phoneNumberOrEmail = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            int image = source.readInt();
            String name = source.readString();
            String phoneNumberOrEmail = source.readString();
            return new Contact(image, name, phoneNumberOrEmail);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public int getImage() {
        return image;
    }

    public String getName() { return name; }

    public String getPhoneNumberOrEmail() {
        return phoneNumberOrEmail;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image);
        dest.writeString(name);
        dest.writeString(phoneNumberOrEmail);
    }
}
