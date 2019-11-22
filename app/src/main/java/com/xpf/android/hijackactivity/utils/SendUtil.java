package com.xpf.android.hijackactivity.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by x-sir on 2019-11-22 :)
 * Function:
 */
public class SendUtil {

    public static String sendInfo(String param1, String param2, String param3) {
        String url = "http://10.0.0.33:8080/spring/test/receiver.do?a=" + param1 + "&b=" + param2 + "&c=" + param3;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
