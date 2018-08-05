package com.prajwaldcunha.mup;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;

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


    public static MultipartBody uploadRequestBody(HashMap<String,String> maps,ArrayList<Bitmap> bitmaps, Context ctx) {


        File file;


        MultipartBody.Builder mBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

                for(String key:maps.keySet()){
                    mBody.addFormDataPart(key,maps.get(key).toString());
                }
        mBody.addFormDataPart("no_of_images", String.valueOf(bitmaps.size()));


        Log.i("TAG", "uri b4   ");

        for (int i = 0; i < bitmaps.size(); i++) {

            file = new File(String.valueOf(getImageUri(ctx, bitmaps.get(i))));
            String content_type = "jpeg";
            String file_path = file.getAbsolutePath();

            RequestBody file_body = RequestBody.create(MediaType.parse(content_type), file);


            Log.i("TAG", "typee   : " + getMimeType(file_path));
            Log.i("TAG", "file_body: " + file_body.toString());

            Log.i("TAG", "file_path: " + file_path);
            Log.i("TAG", "file_pathsssf: " + file_path.substring(file_path.lastIndexOf("/") + 1));

            mBody.addFormDataPart("file" + i, file_path.substring(file_path.lastIndexOf("/") + 1) + "." + content_type, file_body);

        }


        return mBody.build();
    }

    private static String getMimeType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

    }

    public static File getImageUri(Context inContext, Bitmap inImage) {

        File pictureFile = getOutputMediaFile(inContext);
        if (pictureFile == null) {
            Log.i("TAG",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return null;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            inImage.compress(Bitmap.CompressFormat.JPEG, 70, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.i("TAG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.i("TAG", "Error accessing file: " + e.getMessage());
        }

        return pictureFile;


    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(Context ctx) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + MUPPick.folderName
                + "/Uploads");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmssSSS").format(new Date());
        File mediaFile;
        String mImageName = "IMAGES_" + timeStamp + ".jpeg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

}
