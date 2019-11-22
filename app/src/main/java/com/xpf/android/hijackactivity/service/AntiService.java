package com.xpf.android.hijackactivity.service;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.xpf.android.hijackactivity.constants.AntiConstants;
import com.xpf.android.hijackactivity.utils.HandlerFactory;
import com.xpf.android.hijackactivity.widget.AntiView;

/**
 * Created by x-sir on 2019-11-22 :)
 * Function:
 */
public class AntiService extends Service {

    private boolean shouldLoop = false;
    private Handler handler;
    private ActivityManager am;
    private PackageManager pm;
    private Handler mainHandler;
    private AntiView mAntiView;
    private int circle = 2000;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint({"HandlerLeak", "NewApi"})
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        startForeground(19901008, new Notification());
        if (intent != null) {
            circle = intent.getIntExtra(AntiConstants.CIRCLE, 2000);
        }
        Log.i("circle", circle + "ms");
        if (shouldLoop) {
            return;
        }
        mAntiView = new AntiView(this);
        mainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String name = msg.getData().getString("name");
                mAntiView.setText(name);
            }
        };
        pm = getPackageManager();
        shouldLoop = true;
        am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        handler = new Handler(HandlerFactory.getHandlerLooperInOtherThread("anti")) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String packageName = am.getRunningTasks(1).get(0).topActivity.getPackageName();
                try {
                    String progressName = pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
                    updateText(progressName);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                if (shouldLoop) {
                    handler.sendEmptyMessageDelayed(0, circle);
                }
            }
        };
        handler.sendEmptyMessage(0);
    }

    private void updateText(String name) {
        Message message = new Message();
        Bundle data = new Bundle();
        data.putString("name", name);
        message.setData(data);
        mainHandler.sendMessage(message);
    }

    @Override
    public void onDestroy() {
        shouldLoop = false;
        mAntiView.remove();
        super.onDestroy();
    }
}
