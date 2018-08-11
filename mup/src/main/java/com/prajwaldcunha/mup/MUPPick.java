package com.prajwaldcunha.mup;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;

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


    public void upload(Activity ctx, String url, ArrayList<Uri> images, ResponseListener listener, Options options) {
        new UploadTask(ctx, images, true, new HashMap<String, String>(), listener, options).execute();
    }


}
