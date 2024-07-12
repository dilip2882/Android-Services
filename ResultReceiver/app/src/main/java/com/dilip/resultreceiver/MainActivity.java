package com.dilip.resultreceiver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dilip.resultreceiver.services.MyDownloadService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyTag";
    public static final String MESSAGE_KEY = "message_key";
    private ScrollView mScroll;
    private TextView mLog;
    private ProgressBar mProgressBar;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        mHandler = new Handler();

    }

    public void runCode(View v) {
        log("Running code");
        displayProgressBar(true);

        //send intent to download service

        ResultReceiver resultReceiver = new MyDownloadReceiver(null);

        for (String song : Playlist.songs) {
            Intent intent = new Intent(MainActivity.this, MyDownloadService.class);
            intent.putExtra(MESSAGE_KEY, song);
            intent.putExtra(Intent.EXTRA_RESULT_RECEIVER, resultReceiver);

            startService(intent);
        }

    }

    private void initViews() {
        mScroll = findViewById(R.id.scrollLog);
        mLog = findViewById(R.id.tvLog);
        mProgressBar = findViewById(R.id.progress_bar);
    }

    public void clearOutput(View v) {
        Intent intent = new Intent(MainActivity.this, MyDownloadService.class);
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

    public class MyDownloadReceiver extends ResultReceiver {

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public MyDownloadReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            if (resultCode == RESULT_OK && resultData != null) {

                Log.d(TAG, "onReceiveResult: Thread Name: " + Thread.currentThread().getName());

                String songName = resultData.getString(MESSAGE_KEY);
//                MainActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        log(songName+ " Downloaded");
//                    }
//                });

                // alternative to runOnUiThread
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        log(songName + " Downloaded");

                    }
                });

            }
        }
    }

}
