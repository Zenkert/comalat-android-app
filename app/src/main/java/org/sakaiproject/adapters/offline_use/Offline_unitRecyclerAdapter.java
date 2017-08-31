package org.sakaiproject.adapters.offline_use;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import org.sakaiproject.api.offline_use.Model.Course;
import org.sakaiproject.api.offline_use.Model.Unit;
import org.sakaiproject.api.offline_use.Service.OfflineDownloadService;
import org.sakaiproject.sakai.R;

/**
 * Created by SyleSakis on 31/08/2017.
 */

public class Offline_unitRecyclerAdapter extends RecyclerView.Adapter<Offline_unitHolder> {
    private Course course;
    private String lvlName;
    private String langName;

    private Context context;

    public void setData(Course course, String lvlName, String langName){
        this.course = course;
        this.lvlName = lvlName;
        this.langName = langName;
    }

    @Override
    public Offline_unitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.offline_use_units, parent, false);
        Offline_unitHolder holder = new Offline_unitHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Offline_unitHolder holder, int position) {
        final Unit unit = course.getUnits().get(position);
        final String url = context.getResources().getString(R.string.offline_url) + "languages/" + langName + "/levels/" + lvlName + "/courses/" + course.getCourseName() + "/units/";

        holder.unitName.setText(unit.getUnitName());
        holder.fileName.setText(unit.getFilename());
        holder.unitDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = url + unit.getUnitName();
                OfflineDownloadService dl = new OfflineDownloadService(context, holder.unitDL);
                dl.getFile(link, unit.getFilename());
                Toast.makeText(context, unit.getFilename(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return course.getUnits().size();
    }
}
