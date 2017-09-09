package org.sakaiproject.api.offline_use.Service;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sakaiproject.api.callback.Callback;
import org.sakaiproject.api.internet.NetWork;
import org.sakaiproject.api.offline_use.Model.Course;
import org.sakaiproject.api.offline_use.Model.Language;
import org.sakaiproject.api.offline_use.Model.Level;
import org.sakaiproject.api.offline_use.Model.Unit;
import org.sakaiproject.sakai.AppController;
import org.sakaiproject.sakai.R;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by SyleSakis on 22/08/2017.
 */

public class OfflineDataService {
    private static final String LANGUAGES_TAG = "Languages";
    private static final String LANGUAGE_TAG = "Language";
    private static final String EDUCATION_LEVELS_TAG = "EducationLevels";
    private static final String EDUCATION_LEVEL_TAG = "EducationLevel";
    private static final String COURSES_TAG = "Courses";
    private static final String COURSE_TAG = "Course";
    private static final String UNITS_TAG = "Units";
    private static final String UNIT_TAG = "Unit";
    private static final String FILE_TAG = "File";
    private static final String SKILLS_TAG = "Skills";
    private static final String CONTENTS_TAG = "Contents";
    private static final String LASTUPDATE_TAG = "LastUpdate";
    private static final String SIZE_TAG = "size";
    private static final String NOUNITS_TAG = "noUnits";

    private Context context;
    private Callback callback;

    public OfflineDataService() {
    }

    public OfflineDataService(Context context, Callback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void getData(String url) {
        if (NetWork.getConnectionEstablished()) {
            JsonObjectRequest dataRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    callback.onSuccess(parsingData(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Server unreachable. Load local files", Toast.LENGTH_LONG).show();
                    callback.onSuccess(parsingLocalData());
                }
            });
            AppController.getInstance().addToRequestQueue(dataRequest, LANGUAGES_TAG);
        } else {
            callback.onSuccess(parsingLocalData());
        }
    }


    private List<Language> parsingData(JSONObject jsonObject) {
        List<Language> languages = new ArrayList<>();

        try {
            JSONArray jsonLangs = jsonObject.getJSONArray(LANGUAGES_TAG);
            for (int i = 0; i < jsonLangs.length(); i++) {
                Language tmplang = new Language();
                tmplang.setLanguageName(jsonLangs.getJSONObject(i).getString(LANGUAGE_TAG));
                tmplang.setSize(jsonLangs.getJSONObject(i).getLong(SIZE_TAG));
                tmplang.setNoUnits(jsonLangs.getJSONObject(i).getInt(NOUNITS_TAG));
                tmplang.setLastupdate(jsonLangs.getJSONObject(i).getString(LASTUPDATE_TAG));

                JSONArray lvls = jsonLangs.getJSONObject(i).getJSONArray(EDUCATION_LEVELS_TAG);
                List<Level> levels = new ArrayList<>();
                for (int j = 0; j < lvls.length(); j++) {
                    Level tmplvl = new Level();
                    tmplvl.setSize(lvls.getJSONObject(j).getLong(SIZE_TAG));
                    tmplvl.setLevel(lvls.getJSONObject(j).getString(EDUCATION_LEVEL_TAG));

                    JSONArray jsonCourses = lvls.getJSONObject(j).getJSONArray(COURSES_TAG);
                    List<Course> courses = new ArrayList<>();
                    for (int x = 0; x < jsonCourses.length(); x++) {
                        Course tmpCourse = new Course();
                        tmpCourse.setCourseName(jsonCourses.getJSONObject(x).getString(COURSE_TAG));
                        tmpCourse.setSize(jsonCourses.getJSONObject(x).getLong(SIZE_TAG));

                        JSONArray jsonUnits = jsonCourses.getJSONObject(x).getJSONArray(UNITS_TAG);
                        List<Unit> units = new ArrayList<>();
                        for (int y = 0; y < jsonUnits.length(); y++) {
                            Unit tmpUnit = new Unit();
                            tmpUnit.setUnitName(jsonUnits.getJSONObject(y).getString(UNIT_TAG));
                            tmpUnit.setFilename(jsonUnits.getJSONObject(y).getString(FILE_TAG));
                            tmpUnit.setSize(jsonUnits.getJSONObject(y).getLong(SIZE_TAG));
                            units.add(tmpUnit);
                        }
                        tmpCourse.setUnits(units);
                        courses.add(tmpCourse);
                    }
                    tmplvl.setCourses(courses);
                    levels.add(tmplvl);
                }
                tmplang.setLevels(levels);
                languages.add(tmplang);
            }

        } catch (JSONException ex) {
            Log.e("Error", "ParsingData: " + ex.getMessage(), ex);
        }
        if (languages.size() != 0) {
            return languages;
        }

        return null;
    }

    private List<Language> parsingLocalData() {
        List<Language> languages = new ArrayList<>();
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath()
                + File.separator + context.getResources().getString(R.string.comalatFolder);
        File directory = new File(path);
        if(directory.exists()){
            for(File folder : directory.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory();
                }
            })){
                Language lang = new Language();
                lang.readFromFolder(folder.getPath());

                languages.add(lang);
            }
        }

        if (languages.size() != 0) {
            return languages;
        }
        return null;
    }
}
