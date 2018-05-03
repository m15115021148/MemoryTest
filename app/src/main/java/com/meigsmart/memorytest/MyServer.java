package com.meigsmart.memorytest;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenMeng on 2018/5/2.
 */
public class MyServer extends Service implements Runnable{

    private Bitmap bt ;
    private List<Bitmap> mList = new ArrayList<>();
    private int currPosition = 0;
    private MyReceiver receiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mHandler.removeCallbacks(MyServer.this);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("stop");
        registerReceiver(receiver,intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(this);
        for (Bitmap bt: mList){
            bt.recycle();
        }
        System.gc();
        unregisterReceiver(receiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler.post(this);
        Log.w("result","onStartCommand...");
        return START_REDELIVER_INTENT;
    }

    @Override
    public void run() {
        Log.w("result","size:"+mList.size());
        bt = BitmapFactory.decodeResource(getResources(),R.drawable.sqlash_bg);
        mList.add(bt);

        ImageView img = new ImageView(this);
        img.setImageBitmap(mList.get(currPosition));
        currPosition++;

        if (currPosition == 40){
            mHandler.sendEmptyMessage(1001);
        }

        mHandler.postDelayed(this,1000);
    }

    public class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("stop".equals(action)){
                stopSelf();
            }
        }
    }
}
