package jp.co.jorudan.courseplanner.area;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import jp.co.jorudan.courseplanner.Legs;
import jp.co.jorudan.courseplanner.Spots;
import jp.co.jorudan.courseplanner.Transits;

public class CourseList implements Parcelable{
    private String title;
    private String description;
    private String course_time;
    private List<Legs> legs;
    private List<Spots> spots;
    private List<Transits> transits;
    private List<String> tags;

    public CourseList() {
    }

    public CourseList(String title, String description, String course_time, List<Legs> legs, List<Spots> spots, List<Transits> transits, List<String> tags) {
        this.title = title;
        this.description = description;
        this.course_time = course_time;
        this.legs = legs;
        this.spots = spots;
        this.transits = transits;
        this.tags = tags;
    }

    protected CourseList(Parcel in) {
        title = in.readString();
        description = in.readString();
        course_time = in.readString();
        legs = in.createTypedArrayList(Legs.CREATOR);
        spots = in.createTypedArrayList(Spots.CREATOR);
        transits = in.createTypedArrayList(Transits.CREATOR);
        tags = in.createStringArrayList();
    }

    public static final Creator<CourseList> CREATOR = new Creator<CourseList>() {
        @Override
        public CourseList createFromParcel(Parcel in) {
            return new CourseList(in);
        }

        @Override
        public CourseList[] newArray(int size) {
            return new CourseList[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCourse_time() {
        return course_time;
    }

    public List<Legs> getLegs() {
        return legs;
    }

    public List<Spots> getSpots() {
        return spots;
    }

    public List<Transits> getTransits() {
        return transits;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(course_time);
        dest.writeTypedList(legs);
        dest.writeTypedList(spots);
        dest.writeTypedList(transits);
        dest.writeStringList(tags);
    }
}
