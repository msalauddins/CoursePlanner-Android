package jp.co.jorudan.courseplanner.myCourses;

import java.io.File;

public class CacheListItem {
    public String cacheCourseId;
    public File cacheRouteFileName;
    public String cacheRouteIndex;
    public String uniqueId;

    public String toString(){
        return cacheCourseId + " - " + cacheRouteFileName + " - " + cacheRouteIndex;
    }

    public CacheListItem(String cacheCourseId, File cacheRouteFileName, String cacheRouteIndex, String uniqueId) {
        this.cacheCourseId = cacheCourseId;
        this.cacheRouteFileName = cacheRouteFileName;
        this.cacheRouteIndex = cacheRouteIndex;
        this.uniqueId = uniqueId;
    }

    public String getCacheCourseId() {
        return cacheCourseId;
    }

    public void setCacheCourseId(String cacheCourseId) {
        this.cacheCourseId = cacheCourseId;
    }

    public File getCacheRouteFileName() {
        return cacheRouteFileName;
    }

    public void setCacheRouteFileName(File cacheRouteFileName) {
        this.cacheRouteFileName = cacheRouteFileName;
    }

    public String getCacheRouteIndex() {
        return cacheRouteIndex;
    }

    public void setCacheRouteIndex(String cacheRouteIndex) {
        this.cacheRouteIndex = cacheRouteIndex;
    }

    public String getUniqueId() {
        return uniqueId;
    }
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
