package by.htp.first.homework4_1;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements  Parcelable {

    private int imageId;
    private String name;
    private String phoneNumberOrEmail;

    public Contact(int imageId, String name, String phoneNumberOrEmail) {
        this.imageId = imageId;
        this.name = name;
        this.phoneNumberOrEmail = phoneNumberOrEmail;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            int imageId = source.readInt();
            String name = source.readString();
            String phoneNumberOrEmail = source.readString();

            return new Contact(imageId, name, phoneNumberOrEmail);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public int getImageId() {
        return imageId;
    }

    public void setImageId (int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumberOrEmail() {
        return phoneNumberOrEmail;
    }

    public void setPhoneNumberOrEmail (String phoneNumberOrEmail) {
        this.phoneNumberOrEmail = phoneNumberOrEmail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageId);
        dest.writeString(name);
        dest.writeString(phoneNumberOrEmail);
    }
}
