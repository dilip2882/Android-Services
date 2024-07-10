package com.dilip.androidservices.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.dilip.androidservices.MainActivity;

public class MyDownloadService extends Service {

    private static final String TAG = "MyTag";

    // this is started service
    public MyDownloadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: called");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand: called " + Thread.currentThread().getName());
        String songName = intent.getStringExtra(MainActivity.MESSAGE_KEY);
        MyDownloadTask myDownloadTask = new MyDownloadTask();
        myDownloadTask.execute(songName);
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

    class MyDownloadTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... songs) {
            for (String song : songs) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                publishProgress(song);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.d(TAG, "onProgressUpdate: Song Downloaded: " + values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onProgressUpdate: Result is:" + s);

        }
    }

}