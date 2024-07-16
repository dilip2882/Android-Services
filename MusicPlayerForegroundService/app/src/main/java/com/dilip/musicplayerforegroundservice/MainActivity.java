package com.dilip.musicplayerforegroundservice;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.dilip.musicplayerforegroundservice.services.MusicPlayerService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyTag";
    public static final String MESSAGE_KEY = "message_key";
    private ScrollView mScroll;
    private TextView mLog;
    private Button mPlayButton;
    private ProgressBar mProgressBar;
    private MusicPlayerService mMusicPlayerService;
    private Boolean mBound = false;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            MusicPlayerService.MyServiceBinder myServiceBinder
                    = (MusicPlayerService.MyServiceBinder) iBinder;
            mMusicPlayerService = myServiceBinder.getService();
            mBound = true;
            Log.d(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");

        }
    };

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String result = intent.getStringExtra(MESSAGE_KEY);

            if (result == "done") {
                mPlayButton.setText(R.string.play);
            }

            Log.d(TAG, "onReceive: Thread Name: " + Thread.currentThread().getName());
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(MainActivity.this, MusicPlayerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mReceiver, new IntentFilter(MusicPlayerService.MUSIC_COMPLETE));
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mBound) {
            unbindService(mServiceConnection);
        }

        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mReceiver);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    public void runCode(View v) {
        log("Playing Music Buddy!");
        displayProgressBar(true);

    }

    private void initViews() {
        mScroll = findViewById(R.id.scrollLog);
        mLog = findViewById(R.id.tvLog);
        mProgressBar = findViewById(R.id.progress_bar);
        mPlayButton = findViewById(R.id.btnPlayMusic);
    }

    public void clearOutput(View v) {
        Intent intent = new Intent(MainActivity.this, MusicPlayerService.class);
        stopService(intent);

        mLog.setText("");
        scrollTextToEnd();
    }

    public void log(String message) {
        Log.i(TAG, message);
        mLog.append(message + "\n");
        scrollTextToEnd();
    }

    private void scrollTextToEnd() {
        mScroll.post(() -> mScroll.fullScroll(ScrollView.FOCUS_DOWN));
    }

    public void displayProgressBar(boolean display) {
        if (display) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void onBtnPlayMusic(View view) {
        if (mBound) {
            if (mMusicPlayerService.isPlaying()) {
                mMusicPlayerService.pause();
                mPlayButton.setText(R.string.play);
            } else {
                Intent intent = new Intent(MainActivity.this, MusicPlayerService.class);
                startService(intent);

                mMusicPlayerService.play();
                mPlayButton.setText(R.string.pause);
            }
        }
    }
}