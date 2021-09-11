package jp.co.jorudan.courseplanner;

import android.os.Parcel;
import android.os.Parcelable;

public class Point implements Parcelable{


    private String name;
    private String type;
    private String latitude;
    private String longitude;

    public Point() {
    }

    public Point(String name, String type, String latitude, String longitude) {
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Point(Parcel in) {
        name = in.readString();
        type = in.readString();
        latitude = in.readString();
        longitude = in.readString();
    }

    public static final Creator<Point> CREATOR = new Creator<Point>() {
        @Override
        public Point createFromParcel(Parcel in) {
            return new Point(in);
        }

        @Override
        public Point[] newArray(int size) {
            return new Point[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(latitude);
        dest.writeString(longitude);
    }
}
