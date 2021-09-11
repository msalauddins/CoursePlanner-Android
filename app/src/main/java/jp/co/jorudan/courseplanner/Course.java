package jp.co.jorudan.courseplanner;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Course implements  Parcelable{
    private String locImageURL;
    private String locID;
    private String locTitle;
    private String locDescription;
    private String locCourseTime;
    private List<Legs> legs;
    private List<Spots> spots;
    private List<Transits> transits;
    private List<Tags> tags;

    protected Course(Parcel in) {
        locImageURL = in.readString();
        locID = in.readString();
        locTitle = in.readString();
        locDescription = in.readString();
        locCourseTime = in.readString();
        legs = in.createTypedArrayList(Legs.CREATOR);
        spots = in.createTypedArrayList(Spots.CREATOR);
        transits = in.createTypedArrayList(Transits.CREATOR);
        tags = in.createTypedArrayList(Tags.CREATOR);
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public Course() {

    }

    public String getLocImageURL() {
        return locImageURL;
    }

    public void setLocImageURL(String locImageURL) {
        this.locImageURL = locImageURL;
    }

    public String getLocID() {
        return locID;
    }

    public void setLocID(String locID) {
        this.locID = locID;
    }

    public String getLocTitle() {
        return locTitle;
    }

    public void setLocTitle(String locTitle) {
        this.locTitle = locTitle;
    }

    public String getLocDescription() {
        return locDescription;
    }

    public void setLocDescription(String locDescription) {
        this.locDescription = locDescription;
    }

    public String getLocCourseTime() {
        return locCourseTime;
    }

    public void setLocCourseTime(String locCourseTime) {
        this.locCourseTime = locCourseTime;
    }

    public List<Legs> getLegs() {
        return legs;
    }

    public void setLegs(List<Legs> legs) {
        this.legs = legs;
    }

    public List<Spots> getSpots() {
        return spots;
    }

    public void setSpots(List<Spots> spots) {
        this.spots = spots;
    }

    public List<Transits> getTransits() {
        return transits;
    }

    public void setTransits(List<Transits> transits) {
        this.transits = transits;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(locImageURL);
        dest.writeString(locID);
        dest.writeString(locTitle);
        dest.writeString(locDescription);
        dest.writeString(locCourseTime);
        dest.writeTypedList(legs);
        dest.writeTypedList(spots);
        dest.writeTypedList(transits);
        dest.writeTypedList(tags);
    }
}
