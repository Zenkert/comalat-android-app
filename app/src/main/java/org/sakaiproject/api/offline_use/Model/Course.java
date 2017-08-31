package org.sakaiproject.api.offline_use.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SyleSakis on 21/08/2017.
 */

public class Course implements Serializable {
    private String courseName;
    private List<Unit> units;
    private long size;

    public Course() {
    }

    public Course(String courseName, List<Unit> units, long size) {
        this.courseName = courseName;
        this.units = units;
        this.size = size;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
