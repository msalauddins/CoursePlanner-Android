package jp.co.jorudan.courseplanner.area;

public class Area {

    private String areaId;
    private String name;
    private String image;

    public Area() {

    }

    public String getAreaId() {
        return areaId;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setAreaId(String area_id) {
        this.areaId = area_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
