package com.dilip.androidservices;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DownloadHandler extends Handler {

    private static final String TAG = "MyTag";

    public DownloadHandler() {

    }

    @Override
    public void handleMessage(Message msg) {

        downloadSong(msg.obj.toString());
    }

    private void downloadSong(final String songName) {
        Log.d(TAG, "run: staring download");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        mActivity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mActivity.log("Downlaod Complete " + songName);
//                mActivity.displayProgressBar(false);
//            }
//        });


        Log.d(TAG, "downloadSong: " + songName + " Downloaded...");
    }
}