package org.sakaiproject.api.offline_use.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SyleSakis on 21/08/2017.
 */

public class Level implements Serializable {
    private String level;
    private List<Course> courses;
    private long size;

    public Level() {

    }

    public Level(String level, List<Course> courses, long size) {
        this.level = level;
        this.courses = courses;
        this.size = size;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
