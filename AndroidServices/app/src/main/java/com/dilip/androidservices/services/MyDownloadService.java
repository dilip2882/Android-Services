package com.dilip.androidservices.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.dilip.androidservices.DownloadThread;
import com.dilip.androidservices.MainActivity;

public class MyDownloadService extends Service {

    private static final String TAG = "MyTag";
    private DownloadThread mDownloaderThread;

    // this is started service
    public MyDownloadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: called");
        mDownloaderThread = new DownloadThread();
        mDownloaderThread.start();

        while (mDownloaderThread.mHandler == null) {

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand: called " + Thread.currentThread().getName());
        String songName = intent.getStringExtra(MainActivity.MESSAGE_KEY);

        Message message = Message.obtain();
        message.obj = songName;
        mDownloaderThread.mHandler.sendMessage(message);

        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: called");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
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

}