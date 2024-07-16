package com.dilip.musicplayerforegroundservice.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.dilip.musicplayerforegroundservice.MainActivity;
import com.dilip.musicplayerforegroundservice.R;

public class MusicPlayerService extends Service {

    private static final String TAG = "MyTag";
    public static final String MUSIC_COMPLETE = "MusicComplete";
    private final Binder mBinder = new MyServiceBinder();
    private MediaPlayer mPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        mPlayer = MediaPlayer.create(this, R.raw.daylight_david_kushner);

        mPlayer.setOnCompletionListener(mp -> {
            Intent intent = new Intent(MUSIC_COMPLETE);
            intent.putExtra(MainActivity.MESSAGE_KEY, "done");
            LocalBroadcastManager.getInstance(getApplicationContext())
                    .sendBroadcast(intent);

            stopForeground(true);
            stopSelf();
        });
    }

    public class MyServiceBinder extends Binder {
        public MusicPlayerService getService() {
            return MusicPlayerService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        showNotification();

        Log.d(TAG, "onStartCommand: ");
        return START_NOT_STICKY;
    }

    private void showNotification() {

        // Create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "channelId",
                    "Music Player Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channelId")
                .setContentTitle("Dilip Music Player")
                .setContentText("This is a demo music player")
                .setSmallIcon(R.mipmap.ic_launcher);

        // Start the service in the foreground
        startForeground(123, builder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind: ");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

        mPlayer.release();
    }

    // public client methods

    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    public void play() {
        mPlayer.start();
    }

    public void pause() {
        mPlayer.pause();
    }

//    public String getValue() {
//        return "data from service";
//    }
}

