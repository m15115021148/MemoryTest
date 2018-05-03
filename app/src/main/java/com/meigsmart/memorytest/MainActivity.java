package com.meigsmart.memorytest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private MyServer mMyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService(View view) {
        if (intent==null)
            initService();
    }

    private void initService(){
        intent = new Intent(this,MyServer.class);
        bindService(intent,conn, BIND_AUTO_CREATE);
        startService(intent);
    }

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyService = ((MyServer.MyBinder) service).getService();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
