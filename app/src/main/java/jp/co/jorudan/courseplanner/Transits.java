package jp.co.jorudan.courseplanner;

import android.os.Parcel;
import android.os.Parcelable;

public class Transits implements Parcelable{

    private String id;
    private Point origin;
    private Point destination;

    public Transits() {
    }

    public Transits(String id, Point origin, Point destination) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
    }

    protected Transits(Parcel in) {
        id = in.readString();
        origin = in.readParcelable(Point.class.getClassLoader());
        destination = in.readParcelable(Point.class.getClassLoader());
    }

    public static final Creator<Transits> CREATOR = new Creator<Transits>() {
        @Override
        public Transits createFromParcel(Parcel in) {
            return new Transits(in);
        }

        @Override
        public Transits[] newArray(int size) {
            return new Transits[size];
        }
    };

    public String getId() {
        return id;
    }

    public Point getOrigin() {
        return origin;
    }

    public Point getDestination() {
        return destination;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(origin, flags);
        dest.writeParcelable(destination, flags);
    }
}
