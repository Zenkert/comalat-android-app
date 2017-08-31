package org.sakaiproject.adapters.offline_use;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import org.sakaiproject.sakai.R;

/**
 * Created by SyleSakis on 21/08/2017.
 */

public class Offline_levelHolder extends RecyclerView.ViewHolder{

    TextView lvlName;
    ImageButton lvlDL;
    Offline_courseRecyclerAdapter courseAdapter;

    public Offline_levelHolder(View view) {
        super(view);
        lvlName = (TextView) view.findViewById(R.id.offline_lvl_id);
        lvlDL = (ImageButton) view.findViewById(R.id.offline_lvlDL_id);

        RecyclerView courseRV = (RecyclerView) view.findViewById(R.id.offline_recycler_course);
        courseRV.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        courseRV.setLayoutManager(layoutManager);
        courseAdapter = new Offline_courseRecyclerAdapter();
        courseRV.setAdapter(courseAdapter);
    }
}
