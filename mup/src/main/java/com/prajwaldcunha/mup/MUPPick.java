package com.prajwaldcunha.mup;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.esafirm.imagepicker.features.ImagePicker;

import java.util.ArrayList;
import java.util.HashMap;

public class MUPPick extends MUP {

    private static MUPPick instance;

    public static MUPPick init() {
        if (instance == null) {
            instance = new MUPPick();
        }
        return instance;
    }


    public MUPPick setProgressTitle(Context ctx, String title) {
        super.setProgress(ctx, title);
        return instance;
    }

    public void pickImages(Activity ctx,String toolbarFolderTitleName,String toolbarImageTitleName,int maxNoImages,int theme){
        ImagePicker.create(ctx)
                .folderMode(true)
                .toolbarFolderTitle(toolbarFolderTitleName)
                .toolbarImageTitle(toolbarImageTitleName)
                .multi()
                .limit(maxNoImages)
                .showCamera(true)
                .theme(theme)
                .start();

    }


    public void upload(Activity ctx, String url, ArrayList<Uri> images, ResponseListener listener, Options options) {
        new UploadTask(ctx, images, true, new HashMap<String, String>(), listener, options).execute();
    }


}
