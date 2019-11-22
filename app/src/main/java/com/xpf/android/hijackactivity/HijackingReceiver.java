package com.xpf.android.hijackactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xpf.android.hijackactivity.service.HijackingService;

/**
 * Created by x-sir on 2019-11-22 :)
 * Function:
 */
public class HijackingReceiver extends BroadcastReceiver {

    private static final String TAG = "HijackingReceiver";
    private static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.w(TAG, "开机启动");
            Intent intent1 = new Intent(context, HijackingService.class);
            context.startService(intent1);
            Log.w(TAG, "启动用来劫持的Service");
        }
    }
}
