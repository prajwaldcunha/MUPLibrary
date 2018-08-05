package com.prajwaldcunha.mup;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;

public abstract class MUP {

    private ProgressDialog progressDialog;

    private boolean isNotificationEnabled = false;
    private int notificationIcon;
    private int notificationColor;

    public static String folderName = "/folder";

    private final String TAG = "MUP";

    protected void setProgress(Context ctx, String title) {
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMax(100);
        progressDialog.setTitle(title);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    protected void setNotification(boolean enabled) {
        isNotificationEnabled = enabled;
    }

    protected void setNotificationOptions(int icon, int color) {
        this.notificationIcon = icon;
        this.notificationColor = color;
    }

    protected static class UploadTask extends AsyncTask<Integer, Integer, String> {

        NotificationManager mNotifyManager;
        NotificationCompat.Builder mBuilder;
        ArrayList<Bitmap> bitmaps;
        ArrayList<Uri> images;
        private WeakReference<Activity> activityWeakReference;
        private HashMap<String, String> postMap;
        private ResponseListener listener;

        UploadTask(Activity context, ArrayList<Uri> images, boolean isNormal, HashMap<String, String> map, ResponseListener listener) {
            activityWeakReference = new WeakReference<>(context);
            bitmaps = new ArrayList<>();
            this.images = images;
            this.postMap = map;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(Integer... params) {
            String response = "";
            try {

                Activity activity = activityWeakReference.get();
                if (activity == null || activity.isFinishing()) {
                    return null;
                }

                for (Uri uri : images) {
                    try {
                        Bitmap bitmap = decodeUri(Uri.parse(getPath(activity, uri)));
                        bitmaps.add(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.i("MUP", e.toString());
                    }
                }


                MultipartBody body = RequestBuilder.uploadRequestBody(bitmaps, postMap);

                CountingRequestBody monitoredRequest = new CountingRequestBody(body, new CountingRequestBody.Listener() {
                    @Override
                    public void onRequestProgress(long bytesWritten, long contentLength) {
                        //Update a progress bar with the following percentage
                        float percentage = 100f * bytesWritten / contentLength;
                        if (percentage >= 0) {
                            publishProgress(Math.round(percentage));
                        }
                    }
                });

                String url = " ";
                response = ApiCall.doOkHttpPost(url, monitoredRequest);

                listener.onResponse(response);
            }
            catch (IOException e) {

                Log.i("MUP", "error....");
                Log.i("MUP", e.toString());
                e.printStackTrace();

            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            mBuilder.setContentText("Upload complete");
            mBuilder.setContentTitle("Uploaded");
            // Removes the progress bar
            mBuilder.setProgress(0, 0, false);
            mNotifyManager.notify(0, mBuilder.build());

        }

        @Override
        protected void onPreExecute() {

            Activity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            String channelId = "progress_channel";
            mNotifyManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(activity, channelId);
            mBuilder.setContentTitle("Uploading")
                    .setContentText("Upload in progress")
                    .setSmallIcon(R.drawable.ic_notification_icon)
                    .setColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                    .setProgress(100, 0, false);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if ((values[0]) % 25 == 0) {
                mBuilder.setProgress(100, values[0], false);
                // Displays the progress bar on notification
                mNotifyManager.notify(0, mBuilder.build());
            }

        }

        private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            File file = new File(selectedImage.toString());


            BitmapFactory.decodeStream(new FileInputStream(file), null, o);

            final int REQUIRED_SIZE = 500;

            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(file), null, o2);
        }

        private String getPath(Context context, Uri uri) {
            String result = null;
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                    result = cursor.getString(column_index);
                }
                cursor.close();
            }
            if (result == null) {
                result = "Not found";
            }
            return result;
        }

    }

    public static boolean doesNotHavePermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
