package org.sakaiproject.api.offline_use.Service;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import org.sakaiproject.api.internet.NetWork;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by SyleSakis on 23/08/2017.
 */

public class OfflineDownloadService {

    private ImageButton button;

    private String link;
    private String subPath;
    private String filename;
    private Context context;

    private static final String DOWNLOAD_FOLDER = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator;

    public OfflineDownloadService() {
    }

    public OfflineDownloadService(Context context, ImageButton button) {
        this.context = context;
        this.button = button;
    }

    public void getFile(String link, String filename, String subPath, long fileSize) {
        this.link = link;
        this.subPath = subPath;
        this.filename = filename;

        if (2 * fileSize >= FolderManager.remainingLocalStorage()) {
            Toast.makeText(context, "Error: Not enough space", Toast.LENGTH_LONG).show();
            return;
        }

        if (NetWork.getConnectionEstablished()) {
            new DownloadingTask().execute();
        }
    }

    private class DownloadingTask extends AsyncTask<Void, Void, Void> {

        private String message = null;

        @Override
        protected Void doInBackground(Void... params) {

            try {

                URL url = new URL(link);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.connect();

                if (c.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    if (!new File(DOWNLOAD_FOLDER).exists()) {
                        new File(DOWNLOAD_FOLDER).mkdir();
                    }

                    File file = new File(DOWNLOAD_FOLDER + File.separator + filename);

                    FileOutputStream fos = new FileOutputStream(file);
                    InputStream is = c.getInputStream();

                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }

                    fos.flush();
                    fos.close();
                    is.close();
                } else {
                    message = "Server Error!";
                }

            } catch (Exception e) {
                if (message == null) {
                    message = "Error!: " + e.getMessage();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            button.setEnabled(true);

            if (message == null) {
                Toast.makeText(context, filename + " downloaded!", Toast.LENGTH_LONG).show();
                if (filename.contains(".zip")) {
                    FolderManager.Decompression(DOWNLOAD_FOLDER + filename, subPath, context);
                } else if (filename.contains(".pdf")) {
                    FolderManager.move(DOWNLOAD_FOLDER + filename, subPath, context);
                } else {
                    Toast.makeText(context, "Error: Invalid file format", Toast.LENGTH_LONG).show();
                }
                Log.d("________PATH__________", "onPostExecute: " + subPath);
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(param);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
            button.setEnabled(false);
        }
    }


}
