package jp.co.jorudan.courseplanner.area;

import jp.co.jorudan.courseplanner.Course;

public class SubLocationItem {

    private Course course;

    public SubLocationItem(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

}
