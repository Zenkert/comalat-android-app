package org.sakaiproject.sakai;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.sakaiproject.adapters.offline_use.Offline_levelRecyclerAdapter;
import org.sakaiproject.api.offline_use.Model.Language;
import org.sakaiproject.api.offline_use.Service.OfflineDownloadService;


/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineDetailsFragment extends Fragment {

    private TextView noUntisView;
    private TextView sizeView;
    private TextView lastupdateView;
    private ImageButton download_lang;
    private OfflineDownloadService dl;
    private RecyclerView recyclerLevelView;
    private RecyclerView.Adapter adapterLvl;
    private RecyclerView.LayoutManager layoutManager;

    private Language lang;
    private String url;

    public OfflineDetailsFragment() {   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_offline_details, container, false);
        noUntisView = (TextView) v.findViewById(R.id.offline_noUnits_id);
        sizeView = (TextView) v.findViewById(R.id.offline_lang_size_id);
        lastupdateView = (TextView) v.findViewById(R.id.offline_lastupdate_id);
        download_lang = (ImageButton) v.findViewById(R.id.offline_download_lang_id);
        recyclerLevelView = (RecyclerView) v.findViewById(R.id.offline_recycler_lvl);

        dl = new OfflineDownloadService(getActivity(), download_lang);
        url = getContext().getResources().getString(R.string.server_offline_use_url) + getContext().getResources().getString(R.string.restAPI);

        download_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = url + "languages/" + lang.getLanguageName();
                dl.getFile(link, lang.getLanguageName() + ".zip", "", lang.getSize());
            }
        });


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            lang = (Language) savedInstanceState.getSerializable(getContext().getResources().getString(R.string.serial_lang));
        } else {
            if (getArguments() != null) {
                savedInstanceState = getArguments();
                lang = (Language) savedInstanceState.getSerializable(getContext().getResources().getString(R.string.serial_lang));
            }
        }
        setLang();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(getContext().getResources().getString(R.string.serial_lang), lang);
    }

    @Override
    public void onDestroy() {
        getActivity().finish();
        super.onDestroy();
    }

    public void setLang() {
        noUntisView.setText(String.valueOf(lang.getNoUnits()));
        sizeView.setText(byte2MegaByte(lang.getSize()));
        lastupdateView.setText(lang.getLastupdate());
        fillDetails();
    }

    private void fillDetails(){
        adapterLvl = new Offline_levelRecyclerAdapter(lang, getContext());
        recyclerLevelView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerLevelView.setLayoutManager(layoutManager);
        recyclerLevelView.setAdapter(adapterLvl);
    }

    private String byte2MegaByte(long size) {
        double mb = 1048576.0;
        double s = Math.floor((size / mb) * 100) / 100;
        return String.valueOf(s);
    }
}
