package org.sakaiproject.api.offline_use.Model;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SyleSakis on 21/08/2017.
 */

public class Level implements FolderHandler, Serializable {
    private String level;
    private List<Course> courses;
    private long size = 0;
    private int noUnits = 0;

    public Level() {
        courses = new ArrayList<>();
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
    public Level readFromFolder(String path) {
        File directory = new File(path);
        this.setLevel(directory.getName());

        for (File folder : directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        })) {
            Course course = new Course();
            course = course.readFromFolder(folder.getPath());
            this.noUnits += course.getNoUnits();
            this.size += course.getSize();
            this.courses.add(course);
        }

        return this;
    }
}
