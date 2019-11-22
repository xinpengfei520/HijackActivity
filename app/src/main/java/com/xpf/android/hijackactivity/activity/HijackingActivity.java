package com.xpf.android.hijackactivity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.xpf.android.hijackactivity.R;
import com.xpf.android.hijackactivity.service.HijackingService;

public class HijackingActivity extends AppCompatActivity {

    private static final String TAG = "HijackingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijacking);
        Intent intent = new Intent(this, HijackingService.class);
        startService(intent);
        Log.w(TAG, "activity启动用来劫持的Service");
    }
}
