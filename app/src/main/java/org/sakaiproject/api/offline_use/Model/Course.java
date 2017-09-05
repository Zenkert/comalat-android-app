package org.sakaiproject.api.offline_use.Model;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SyleSakis on 21/08/2017.
 */

public class Course implements FolderHandler, Serializable {
    private String courseName;
    private List<Unit> units;
    private long size = 0;
    private int noUnits = 0;

    public Course() {
        this.units = new ArrayList<>();
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

    @Override
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public int getNoUnits() {
        return this.noUnits;
    }

    @Override
    public Course readFromFolder(String path) {
        File directory = new File(path);
        this.setCourseName(directory.getName());

        for (File folder : directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        })) {
            Unit unit = new Unit();
            unit = unit.readFromFolder(folder.getPath());
            this.size += unit.getSize();
            if(unit != null) {
                this.noUnits += unit.getNoUnits();
                this.units.add(unit);
            }
        }

        return this;
    }
}
