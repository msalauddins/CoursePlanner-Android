package jp.co.jorudan.courseplanner;

import android.os.Parcel;
import android.os.Parcelable;

public class Legs implements Parcelable {
    private String id;
    private String type;
    private String leg_time;

    public Legs() {
    }

    public Legs(String id, String type, String leg_time) {
        this.id = id;
        this.type = type;
        this.leg_time = leg_time;
    }

    protected Legs(Parcel in) {
        id = in.readString();
        type = in.readString();
        leg_time = in.readString();
    }

    public static final Creator<Legs> CREATOR = new Creator<Legs>() {
        @Override
        public Legs createFromParcel(Parcel in) {
            return new Legs(in);
        }

        @Override
        public Legs[] newArray(int size) {
            return new Legs[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getLeg_time() {
        return leg_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(leg_time);
    }
}
