package org.sakaiproject.api.offline_use.Model;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by SyleSakis on 21/08/2017.
 */

public class Language implements FolderHandler, Serializable {
    private String languageName;
    private List<Level> levels;
    private String lastupdate;
    private int noUnits = 0;
    private long size = 0;

    public Language() {
        levels = new ArrayList<>();
    }

    public Language(String languageName, List<Level> levels, String lastupdate, int noUnits, long size) {
        this.languageName = languageName;
        this.levels = levels;
        this.lastupdate = lastupdate;
        this.noUnits = noUnits;
        this.size = size;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    @Override
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    @Override
    public int getNoUnits() {
        return noUnits;
    }

    public void setNoUnits(int noUnits) {
        this.noUnits = noUnits;
    }

    private String convertDate(long date) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        return new String(df.format(new Date(date)));
    }


    @Override
    public Language readFromFolder(String path) {
        File directory = new File(path);
        this.setLanguageName(directory.getName());
        this.setLastupdate(convertDate(directory.lastModified()));

        for (File folder : directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        })) {
            Level lvl = new Level();
            lvl = lvl.readFromFolder(folder.getPath());
            this.noUnits += lvl.getNoUnits();
            this.size += lvl.getSize();
            this.levels.add(lvl);
        }

        return this;
    }
}
