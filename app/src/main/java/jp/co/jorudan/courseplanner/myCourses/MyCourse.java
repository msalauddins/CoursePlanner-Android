package jp.co.jorudan.courseplanner.myCourses;

public class MyCourse {

    private String myCourseTitle;
    private String myCourseStartDate;
    private String myCourseEndDate;

    /*public String toString(){
        return "Title : " + myCourseTitle + "\nStart : " + myCourseStartDate + "\nEnd : " + myCourseEndDate;
    }*/

    public MyCourse(String myCourseTitle, String myCourseStartDate, String myCourseEndDate) {
        this.myCourseTitle = myCourseTitle;
        this.myCourseStartDate = myCourseStartDate;
        this.myCourseEndDate = myCourseEndDate;
    }

    public MyCourse() {
    }

    public String getMyCourseTitle() {
        return myCourseTitle;
    }

    public void setMyCourseTitle(String myCourseTitle) {
        this.myCourseTitle = myCourseTitle;
    }

    public String getMyCourseStartDate() {
        return myCourseStartDate;
    }

    public void setMyCourseStartDate(String myCourseStartDate) {
        this.myCourseStartDate = myCourseStartDate;
    }

    public String getMyCourseEndDate() {
        return myCourseEndDate;
    }

    public void setMyCourseEndDate(String myCourseEndDate) {
        this.myCourseEndDate = myCourseEndDate;
    }
}
