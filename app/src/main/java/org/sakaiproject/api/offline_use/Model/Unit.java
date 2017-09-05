package org.sakaiproject.api.offline_use.Model;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.List;

/**
 * Created by SyleSakis on 21/08/2017.
 */

public class Unit implements FolderHandler, Serializable {
    private String unitName;
    private List<String> exercisedSkills;
    private String filename;
    private long size = 0;

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

    @Override
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public int getNoUnits() {
        return 1;
    }

    @Override
    public Unit readFromFolder(String path) {
        File directory = new File(path);
        this.setUnitName(directory.getName());

        for (File pdfFile : directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return (pathname.getPath().endsWith(".pdf")
                        && pathname.isFile());
            }
        })) {
            this.setFilename(pdfFile.getName());
            this.size = pdfFile.length();

            return this;
        }

        return null;
    }
}
