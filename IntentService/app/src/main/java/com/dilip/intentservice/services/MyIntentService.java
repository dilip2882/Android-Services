package com.dilip.intentservice.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.Nullable;

import com.dilip.intentservice.MainActivity;


public class MyIntentService extends IntentService {

    private static final String TAG = "MyTag";

    public MyIntentService() {
        super("MyIntentService");
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: MyIntentService");
        Log.d(TAG, "onCreate: Thread name: "+ Thread.currentThread().getName());

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: MyIntentService");
        Log.d(TAG, "onHandleIntent: Thread name: "+ Thread.currentThread().getName());
        
        String songName = intent.getStringExtra(MainActivity.MESSAGE_KEY);
        downloadSong(songName);
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
        Log.d(TAG, "onDestroy: MyIntentService");
        Log.d(TAG, "onDestroy: Thread name: "+ Thread.currentThread().getName());

    }
}