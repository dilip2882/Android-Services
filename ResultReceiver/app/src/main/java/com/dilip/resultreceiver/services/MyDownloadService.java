package com.dilip.resultreceiver.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;

import com.dilip.resultreceiver.DownloadThread;
import com.dilip.resultreceiver.MainActivity;

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
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand: called with Song Name: " +
                intent.getStringExtra(MainActivity.MESSAGE_KEY) + " Intent Id: " + startId);
        String songName = intent.getStringExtra(MainActivity.MESSAGE_KEY);

        mDownloadThread.mHandler.setResultReceiver(intent.getParcelableExtra(Intent.EXTRA_RESULT_RECEIVER));

        Message message = Message.obtain();
        message.obj = songName;
        message.arg1 = startId;

        downloadSong(songName);

        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void downloadSong(final String songName) {
        Log.d(TAG, "run: staring download");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "downloadSong: " + songName + " Downloaded...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}