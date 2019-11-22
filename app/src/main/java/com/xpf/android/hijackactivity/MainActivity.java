package com.xpf.android.hijackactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xpf.android.hijackactivity.service.AlarmService;

public class MainActivity extends AppCompatActivity {

    private boolean needAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断程序进入后台是否是用户自身造成的（触摸返回键或HOME键），是则无需弹出警示。
        if ((keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) && event.getRepeatCount() == 0) {
            needAlarm = false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        //若程序进入后台不是用户自身造成的，则需要弹出警示
        if (needAlarm) {
            //弹出警示信息
            Toast.makeText(getApplicationContext(), "您的登陆界面被覆盖，请确认登陆环境是否安全", Toast.LENGTH_SHORT).show();
            //启动我们的AlarmService,用于给出覆盖了正常Activity的类名
            Intent intent = new Intent(this, AlarmService.class);
            startService(intent);
        }
        super.onPause();
    }
}
