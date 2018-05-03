package com.meigsmart.memorytest;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenMeng on 2018/5/2.
 */
public class MyServer extends Service implements Runnable{
    private Handler mHandler = new Handler();
    public MyBinder binder = new MyBinder();
    private Bitmap bt ;
    private List<Bitmap> mList = new ArrayList<>();

    private MyServer getInstance(){
        Log.w("result","getinstance.......");
        for (int i=0;i<1000;i++){
            if (bt == null){
                bt = BitmapFactory.decodeResource(getResources(),R.drawable.sqlash_bg);
            }
            mList.add(bt);
        }
        for (Bitmap bt:mList){
            ImageView img = new ImageView(this);
            img.setImageBitmap(bt);
        }
        return MyServer.this;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler.post(this);

        return START_REDELIVER_INTENT;
    }

    @Override
    public void run() {
        mHandler.postDelayed(this,1000);
    }

    public class MyBinder extends Binder{
        public MyServer getService(){
            return getInstance();
        }
    }
}
