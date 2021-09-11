package jp.co.jorudan.courseplanner;

import android.os.Parcel;
import android.os.Parcelable;

public class Spots implements Parcelable {

    public  String id;
    private String latitude;
    private String longitude;
    public  String name;
    public  String image;

    public Spots() {
    }

    public Spots(String id, String latitude, String longitude, String name, String image) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.image = image;
    }

    protected Spots(Parcel in) {
        id = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        name = in.readString();
        image = in.readString();
    }

    public static final Creator<Spots> CREATOR = new Creator<Spots>() {
        @Override
        public Spots createFromParcel(Parcel in) {
            return new Spots(in);
        }

        @Override
        public Spots[] newArray(int size) {
            return new Spots[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Creator<Spots> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(name);
        dest.writeString(image);
    }
}
