package jp.co.jorudan.courseplanner;

import android.os.Parcel;
import android.os.Parcelable;

public class Tags implements Parcelable{

    private  String tags;

    public Tags(String tags) {
        this.tags = tags;
    }

    protected Tags(Parcel in) {
        tags = in.readString();
    }

    public static final Creator<Tags> CREATOR = new Creator<Tags>() {
        @Override
        public Tags createFromParcel(Parcel in) {
            return new Tags(in);
        }

        @Override
        public Tags[] newArray(int size) {
            return new Tags[size];
        }
    };

    public String getTags() {
        return tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tags);
    }
}
