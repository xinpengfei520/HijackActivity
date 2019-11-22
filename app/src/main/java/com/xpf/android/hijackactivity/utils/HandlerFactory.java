package com.xpf.android.hijackactivity.utils;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

/**
 * Created by x-sir on 2019-11-22 :)
 * Function:
 */
public class HandlerFactory {

    public static Handler.Callback getHandlerLooperInOtherThread(String anti) {
        return new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                return false;
            }
        };
    }
}
