package org.sakaiproject.api.offline_use.Model;

/**
 * Created by SyleSakis on 05/09/2017.
 */

public interface FolderHandler {

    public int getNoUnits();
    public long getSize();
    public Object readFromFolder(String path);
}
