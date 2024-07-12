package com.dilip.resultreceiver;

import android.os.Looper;

public class DownloadThread extends Thread {

    private static final String TAG = "MyTag";
    //    private final MainActivity mActivity;
    public DownloadHandler mHandler;

    public DownloadThread() {

    }
//    public DownloadThread(MainActivity activity) {
//        this.mActivity = activity;
//    }

    @Override
    public void run() {

        Looper.prepare();
        mHandler = new DownloadHandler();
        Looper.loop();

    }
}