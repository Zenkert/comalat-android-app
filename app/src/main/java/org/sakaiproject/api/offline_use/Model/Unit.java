package org.sakaiproject.api.offline_use.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SyleSakis on 21/08/2017.
 */

public class Unit implements Serializable {
    private String unitName;
    private List<String> exercisedSkills;
    private String filename;
    private long size;

    public Unit() {
    }

    public Unit(String unitName, List<String> exercisedSkills, String filename, long size) {
        this.unitName = unitName;
        this.exercisedSkills = exercisedSkills;
        this.filename = filename;
        this.size = size;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<String> getExercisedSkills() {
        return exercisedSkills;
    }

    public void setExercisedSkills(List<String> exercisedSkills) {
        this.exercisedSkills = exercisedSkills;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
