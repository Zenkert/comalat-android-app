package org.sakaiproject.api.offline_use.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SyleSakis on 21/08/2017.
 */

public class Language implements Serializable{
    private String languageName;
    private List<Level> levels;
    private String lastupdate;
    private int noUnits;
    private long size;

    public Language() {
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

    public int getNoUnits() {
        return noUnits;
    }

    public void setNoUnits(int noUnits) {
        this.noUnits = noUnits;
    }
}
