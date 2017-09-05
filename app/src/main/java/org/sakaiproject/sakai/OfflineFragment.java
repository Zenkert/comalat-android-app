package org.sakaiproject.sakai;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.sakaiproject.api.callback.Callback;
import org.sakaiproject.api.internet.NetWork;
import org.sakaiproject.api.offline_use.Model.Language;
import org.sakaiproject.api.offline_use.Service.OfflineDataService;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineFragment extends Fragment implements Callback {

    private Spinner spinnerView;
    private ImageButton reloadBtn;
    private OfflineDetailsFragment details;
    private List<Language> langs = null;

    private static final int WRITE_REQUEST_CODE = 12;

    public OfflineFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_offline, container, false);
        getActivity().setTitle(getContext().getResources().getString(R.string.offline_use));

        spinnerView = (Spinner) v.findViewById(R.id.offline_spinner_id);
        reloadBtn = (ImageButton) v.findViewById(R.id.offline_reload_id);

        reloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readPermissions();
            }
        });
        fillSpinner();

        readPermissions();

        return v;
    }

    private void readPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String[] permissionsRequested = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissionsRequested, WRITE_REQUEST_CODE);
            } else {
                getData();
            }
        } else {
            getData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Unable to read elements!", Toast.LENGTH_LONG).show();
            } else {
                getData();
            }
        }
    }

    private void fillSpinner() {
        List<String> names = new ArrayList<>();
        if (langs != null) {
            names.add(getContext().getResources().getString(R.string.choose_lang));
            for (Language lang : langs) {
                names.add(lang.getLanguageName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, names);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            spinnerView.setAdapter(adapter);

            spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    changeFragment(spinnerView.getSelectedItemPosition());
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } else {
            names.add(getContext().getResources().getString(R.string.no_languages_offline));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, names);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            spinnerView.setAdapter(adapter);
        }
    }

    private void changeFragment(int pos) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (pos != 0) {
            details = new OfflineDetailsFragment();
            Bundle b = new Bundle();
            b.putSerializable(getContext().getResources().getString(R.string.serial_lang), langs.get(pos - 1));
            details.setArguments(b);
            fragmentTransaction.replace(R.id.offline_details_id, details);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            if (details != null) {
                fragmentTransaction.remove(details);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

        }
    }

    private void getData(){
        OfflineDataService data = new OfflineDataService(getContext(), this);
        data.getData(getContext().getResources().getString(R.string.offline_url) + "data");
    }

    @Override
    public void onSuccess(Object obj) {
        this.langs = (List<Language>) obj;
        fillSpinner();

    }

    @Override
    public void onError(VolleyError er) {
        readPermissions();
        Toast.makeText(getContext(), "Server Error: " + er.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
