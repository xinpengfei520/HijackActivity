package com.xpf.android.hijackactivity;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by x-sir on 2019-11-22 :)
 * Function:
 */
public class HijackingApplication extends Application {

    private static List<String> hijackings = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void addProgressHijacked(String paramString) {
        hijackings.add(paramString);
    }

    public static void clearProgressHijacked() {
        hijackings.clear();
    }

    public static boolean hasProgressBeHijacked(String paramString) {
        return hijackings.contains(paramString);
    }
}
