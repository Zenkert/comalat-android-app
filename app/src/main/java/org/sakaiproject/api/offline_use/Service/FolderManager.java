package org.sakaiproject.api.offline_use.Service;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.widget.Toast;
import org.sakaiproject.sakai.R;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by SyleSakis on 02/09/2017.
 */

public class FolderManager {

    private static final String DOCUMENT_FOLDER = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + File.separator;

    public static void Decompression(String source, String subPath, Context context) {
        String comalatFolder = context.getResources().getString(R.string.comalatFolder);
        String destination = DOCUMENT_FOLDER + comalatFolder + File.separator + subPath;

        File file = new File(source);
        if (!file.exists()) {
            Toast.makeText(context, "Error: Not exist " + file.getName(), Toast.LENGTH_LONG).show();
            return;
        }

        if(!new File(destination).exists()){
            new File(destination).mkdirs();
        }

        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {
                String filename = ze.getName();
                String filepath = destination + filename.replaceAll("\\\\", File.separator);

                if (!ze.isDirectory()) {
                    File tmp = new File(filepath).getParentFile();
                    if (!tmp.exists()) {
                        tmp.mkdirs();
                    }
                    String ex = extractFile(zis, filepath);
                    if(ex != null){
                        Toast.makeText(context, "Error: " + ex, Toast.LENGTH_LONG).show();
                    }
                } else {
                    File dir = new File(filepath);
                    dir.mkdir();
                }
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            zis.close();
        } catch (IOException ex) {
            Toast.makeText(context, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            // Delete zip file after unzip
            file.delete();
        }

        return;
    }

    private static String extractFile(ZipInputStream zis, String filepath) {

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filepath));
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = zis.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
            bos.close();
        } catch (IOException ex) {
            return ex.getMessage();
        }

        return null;
    }

    public static void move(String source, String subPath, Context context){
        String comalatFolder = context.getResources().getString(R.string.comalatFolder);
        String destination = DOCUMENT_FOLDER + comalatFolder + File.separator + subPath;

        File file = new File(source);
        if (!file.exists()) {
            Toast.makeText(context, "Error: Not exist " + file.getName(), Toast.LENGTH_LONG).show();
            return;
        }

        if(!new File(destination).getParentFile().exists()){
            new File(destination).getParentFile().mkdirs();
        }


        try {
            OutputStream fos = new FileOutputStream(destination);
            InputStream is = new FileInputStream(source);

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }

            fos.flush();
            fos.close();
            is.close();
        }catch (IOException ex){
            Toast.makeText(context, "Error: Fail to copy " + file.getName(), Toast.LENGTH_LONG).show();
        }finally {
            new File(source).delete();
        }
    }


    public static final void readPDF(String subPath, Context context){
        String comalatFolder = context.getResources().getString(R.string.comalatFolder);
        String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + File.separator + comalatFolder + File.separator + subPath;

        File pdf = new File(destination);

        if(!pdf.exists()){
            Toast.makeText(context, "Download the file: " + pdf.getName(), Toast.LENGTH_LONG).show();
            return;
        }

        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(pdf), "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent = Intent.createChooser(target, "Open " + pdf.getName());
        try {
            context.startActivity(intent);
        }catch (ActivityNotFoundException ex){
            Toast.makeText(context, "Install a PDF reader!", Toast.LENGTH_LONG).show();
        }

    }

    public static final long remainingLocalStorage(){
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        stat.restat(Environment.getDataDirectory().getPath());
        long bytesAvailable = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        }else{
            bytesAvailable = (long)stat.getBlockSize() *(long)stat.getAvailableBlocks();
        }
        return bytesAvailable;
    }
}
