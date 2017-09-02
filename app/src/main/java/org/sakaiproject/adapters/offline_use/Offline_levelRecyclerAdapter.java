package org.sakaiproject.adapters.offline_use;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.sakaiproject.api.offline_use.Model.Language;
import org.sakaiproject.api.offline_use.Model.Level;
import org.sakaiproject.api.offline_use.Service.OfflineDownloadService;
import org.sakaiproject.sakai.R;
import java.io.File;

/**
 * Created by SyleSakis on 21/08/2017.
 */

public class Offline_levelRecyclerAdapter extends RecyclerView.Adapter<Offline_levelHolder>{

    private Language language;
    private Context context;

    public Offline_levelRecyclerAdapter(Language lang, Context context){
        this.language = lang;
        this.context = context;
    }

    @Override
    public Offline_levelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.offline_use_levels, parent, false);
        Offline_levelHolder reHolderlvl = new Offline_levelHolder(view);
        return reHolderlvl;
    }

    @Override
    public void onBindViewHolder(final Offline_levelHolder holder, int position) {
        final Level lvl = language.getLevels().get(position);
        final String url = context.getResources().getString(R.string.offline_url) + "languages/" + language.getLanguageName() + "/levels/";
        final String subPath = language.getLanguageName() + File.separator;

        holder.lvlName.setText(lvl.getLevel());
        holder.courseAdapter.setData(lvl, language.getLanguageName());
        holder.lvlDL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // check room
                String link = url + lvl.getLevel();
                OfflineDownloadService dl = new OfflineDownloadService(context, holder.lvlDL);
                dl.getFile(link, lvl.getLevel() + ".zip", subPath, lvl.getSize());
            }
        });
    }

    @Override
    public int getItemCount() {
        return language.getLevels().size();
    }

    private double sizeToMegaByte(long size){
        int mb = 1048576;
        return (Math.round((size/mb)*100)/100);
    }
}
