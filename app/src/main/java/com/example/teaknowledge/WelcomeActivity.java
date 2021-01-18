package com.example.teaknowledge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class WelcomeActivity extends Activity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //接收到消息后跳转
            goMain();
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //延迟两秒发送消息
        handler.sendEmptyMessageDelayed(0,2000);
    }

    private void goMain() {
        //设定调动其他的Activity、Service
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);	//将控制权交给MainActivity
        finish();	//结束
    }

}