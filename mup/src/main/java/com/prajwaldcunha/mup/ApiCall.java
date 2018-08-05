package com.prajwaldcunha.mup;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class ApiCall {


    public static String doOkHttpPost(String url, RequestBody body) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(0, TimeUnit.SECONDS)
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
