package org.sakaiproject.adapters.offline_use;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.sakaiproject.sakai.R;

/**
 * Created by SyleSakis on 31/08/2017.
 */

public class Offline_unitHolder extends RecyclerView.ViewHolder{

    TextView unitName;
    TextView fileName;
    ImageButton unitDL;

    public Offline_unitHolder(View view) {
        super(view);
        unitName = (TextView) view.findViewById(R.id.offline_unit_id);
        fileName = (TextView) view.findViewById(R.id.offline_unit_file_id);
        unitDL = (ImageButton) view.findViewById(R.id.offline_unitDL_id);
    }
}
