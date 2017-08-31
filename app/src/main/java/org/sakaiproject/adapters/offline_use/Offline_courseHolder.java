package org.sakaiproject.adapters.offline_use;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import org.sakaiproject.sakai.R;

/**
 * Created by SyleSakis on 30/08/2017.
 */

public class Offline_courseHolder extends RecyclerView.ViewHolder {
    TextView courseName;
    ImageButton courseDL;

    Offline_unitRecyclerAdapter unitAdapter;

    public Offline_courseHolder(View view) {
        super(view);
        courseName = (TextView) view.findViewById(R.id.offline_course_id);
        courseDL = (ImageButton) view.findViewById(R.id.offline_courseDL_id);

        RecyclerView unitRV = (RecyclerView) view.findViewById(R.id.offline_recycler_unit);
        unitRV.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        unitRV.setLayoutManager(layoutManager);
        unitAdapter = new Offline_unitRecyclerAdapter();
        unitRV.setAdapter(unitAdapter);
    }
}
