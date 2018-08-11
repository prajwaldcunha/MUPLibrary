package com.prajwaldcunha.mup;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class RequestBuilder {

    private static float compressionRate;
    private static String folderName;


    public static MultipartBody uploadRequestBody(ArrayList<Bitmap> bitmaps, HashMap<String, String> map, float compression, String foldername) {


        File file;

        compressionRate = compression;
        folderName = foldername;

        MultipartBody.Builder mBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        for (String key : map.keySet()) {
            mBody.addFormDataPart(key, map.get(key));
        }

        mBody.addFormDataPart("no_of_images", String.valueOf(bitmaps.size()));

        for (int i = 0; i < bitmaps.size(); i++) {

            file = new File(String.valueOf(getImageUri(bitmaps.get(i))));
            String content_type = "jpeg";
            String file_path = file.getAbsolutePath();

            RequestBody file_body = RequestBody.create(MediaType.parse(content_type), file);


            mBody.addFormDataPart("file" + i, file_path.substring(file_path.lastIndexOf("/") + 1) + ".jpg", file_body);

        }


        return mBody.build();
    }


    private static File getImageUri(Bitmap inImage) {

        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            //Log.i("MUP","Error creating media file, check storage permissions: ");// e.getMessage());
            return null;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            if (compressionRate > 100)
                compressionRate = 95;
            inImage.compress(Bitmap.CompressFormat.JPEG, (int) compressionRate, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.i("MUP", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.i("MUP", "Error accessing file: " + e.getMessage());
        }

        return pictureFile;


    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        if (!folderName.startsWith("/"))
            folderName = "/" + folderName;

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + folderName
                + "/Uploads");

        Log.i("MUP", "msd " + mediaStorageDir.toString());
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmssSSS").format(new Date());
        File mediaFile;
        String mImageName = "IMAGES_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

}
