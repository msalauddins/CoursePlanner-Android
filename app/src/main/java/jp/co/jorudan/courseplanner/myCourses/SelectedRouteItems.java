package jp.co.jorudan.courseplanner.myCourses;

public class SelectedRouteItems {

    private String selectedRoute;
    private String coursePosition;
    private String routePos;
    private String uniqueRouteId;

    public String toString(){
        return selectedRoute + "  " + coursePosition;
    }

    public SelectedRouteItems(String selectedRoute, String coursePosition, String routePos, String uniqueRouteId) {
        this.selectedRoute = selectedRoute;
        this.coursePosition = coursePosition;
        this.routePos = routePos;
        this.uniqueRouteId = uniqueRouteId;
    }

    public String getSelectedRoute() {
        return selectedRoute;
    }

    public void setSelectedRoute(String selectedRoute) {
        this.selectedRoute = selectedRoute;
    }

    public String getCoursePosition() {
        return coursePosition;
    }

    public void setCoursePosition(String coursePosition) {
        this.coursePosition = coursePosition;
    }

    public String getRoutePos() {
        return routePos;
    }

    public void setRoutePos(String routePos) {
        this.routePos = routePos;
    }

    public String getUniqueRouteId() {
        return uniqueRouteId;
    }

    public void setUniqueRouteId(String uniqueRouteId) {
        this.uniqueRouteId = uniqueRouteId;
    }
}
