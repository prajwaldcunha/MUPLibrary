package com.prajwaldcunha.muplibrary;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.prajwaldcunha.mup.MUP;
import com.prajwaldcunha.mup.MUPPick;
import com.prajwaldcunha.mup.Options;
import com.prajwaldcunha.mup.ResponseListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int PERMISSION_ALL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

        if (MUP.doesNotHavePermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            return;
        }



        Intent data = getIntent(); //get the intent that starts this activity, in this case: gallery intent


        if (data != null && data.getExtras() != null) {

            Object something = data.getExtras().get("android.intent.extra.STREAM");
            if (something instanceof Uri) {
                ArrayList<Uri> images = new ArrayList<>();
                Uri i = (Uri) something;
                images.add(i);

                Options options=new Options();
                options.enableNotification(true);
                options.setFolderName("/folderName");
                options.setCompressionRate(50);
                options.setNotificationOptions(R.drawable.baseline_notification_important_black_18,R.color.colorPrimary);



                 MUPPick.init()
                        .setProgressTitle(this, "Uploading Test")
                        .upload(this, "http://example.com/", images, new ResponseListener() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("MUP", "Response: " + response);
                            }

                            @Override
                            public void onError(String error) {

                            }
                        },options);

            } else {
                @SuppressWarnings("unchecked")
                ArrayList<Uri> images = (ArrayList<Uri>) something;

                Options options=new Options();
                options.enableNotification(true);
                options.setFolderName("/folder");
                options.setCompressionRate(50);
                options.setNotificationOptions(R.drawable.baseline_notification_important_black_18,R.color.colorPrimary);



                MUPPick.init()
                        .setProgressTitle(this, "Uploading")
                       .upload(this, "http://example.com/", images,  new ResponseListener() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("MUP", "Response: " + response);
                            }

                            @Override
                            public void onError(String error) {

                            }
                        },options);
            }

        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ALL: {
                // If request is cancelled, the result arrays are empty.
            }
        }
    }
}
