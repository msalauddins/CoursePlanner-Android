package jp.co.jorudan.courseplanner.myCourses;

public class MyRoute {

    private String originNodeName;
    private String destinationNodeName;

    public MyRoute(String originNodeName, String destinationNodeName) {
        this.originNodeName = originNodeName;
        this.destinationNodeName = destinationNodeName;
    }

    public String getOriginNodeName() {
        return originNodeName;
    }

    public void setOriginNodeName(String originNodeName) {
        this.originNodeName = originNodeName;
    }

    public String getDestinationNodeName() {
        return destinationNodeName;
    }

    public void setDestinationNodeName(String destinationNodeName) {
        this.destinationNodeName = destinationNodeName;
    }
}
