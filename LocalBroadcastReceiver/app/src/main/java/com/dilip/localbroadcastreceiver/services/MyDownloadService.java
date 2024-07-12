package com.dilip.localbroadcastreceiver.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.dilip.localbroadcastreceiver.DownloadThread;
import com.dilip.localbroadcastreceiver.MainActivity;

public class MyDownloadService extends Service {
    private static final String TAG = "MyTag";
    public DownloadThread mDownloadThread;


    //this is started service

    public MyDownloadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: called");

        mDownloadThread = new DownloadThread();
        mDownloadThread.start();

        while (mDownloadThread.mHandler == null) {

        }
        mDownloadThread.mHandler.setService(this);
        mDownloadThread.mHandler.setContext(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand: called with Song Name: " +
                intent.getStringExtra(MainActivity.MESSAGE_KEY) + " Intent Id: " + startId);
        String songName = intent.getStringExtra(MainActivity.MESSAGE_KEY);

        Message message = Message.obtain();
        message.obj = songName;
        message.arg1 = startId;

        mDownloadThread.mHandler.sendMessage(message);

        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}