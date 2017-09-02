package org.sakaiproject.adapters.offline_use;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.sakaiproject.api.offline_use.Model.Course;
import org.sakaiproject.api.offline_use.Model.Level;
import org.sakaiproject.api.offline_use.Service.OfflineDownloadService;
import org.sakaiproject.sakai.R;
import java.io.File;

/**
 * Created by SyleSakis on 30/08/2017.
 */

public class Offline_courseRecyclerAdapter extends RecyclerView.Adapter<Offline_courseHolder> {

    private Level lvl;
    private String langName;
    private Context context;

    public Offline_courseRecyclerAdapter() {

    }

    public void setData(Level lvl, String langName){
        this.lvl = lvl;
        this.langName = langName;
    }

    @Override
    public Offline_courseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.offline_use_courses, parent, false);
        Offline_courseHolder holder = new Offline_courseHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Offline_courseHolder holder, int position) {
        final Course course = lvl.getCourses().get(position);
        final String url = context.getResources().getString(R.string.offline_url) + "languages/" + langName + "/levels/" + lvl.getLevel() + "/courses/";
        final String subPath = langName + File.separator + lvl.getLevel() + File.separator;

        holder.unitAdapter.setData(course, lvl.getLevel(), langName);
        holder.courseName.setText(course.getCourseName());
        holder.courseDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = url + course.getCourseName();
                OfflineDownloadService dl = new OfflineDownloadService(context, holder.courseDL);
                dl.getFile(link, course.getCourseName() + ".zip", subPath, course.getSize());
            }
        });
    }

    @Override
    public int getItemCount() {
        return lvl.getCourses().size();
    }
}
