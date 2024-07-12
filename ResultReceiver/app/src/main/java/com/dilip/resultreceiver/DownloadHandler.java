package com.dilip.resultreceiver;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;

import com.dilip.resultreceiver.services.MyDownloadService;

public class DownloadHandler extends Handler {

    private static final String TAG = "MyTag";
    private MyDownloadService mService;
    private ResultReceiver mResultReceiver;
//    private final MainActivity mActivity;

    public DownloadHandler() {
//        this.mActivity = activity;
    }

    @Override
    public void handleMessage(Message msg) {

        downloadSong(msg.obj.toString());
        mService.stopSelf(msg.arg1);
        Log.d(TAG, "handleMessage: Song Downloaded: " + msg.obj.toString() + " Intent Id: " + msg.arg1);

        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.MESSAGE_KEY, msg.obj.toString());
        mResultReceiver.send(MainActivity.RESULT_OK, bundle);
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

    public void setService(MyDownloadService mService) {
        this.mService = mService;
    }

    public void setResultReceiver(ResultReceiver mResultReceiver) {
        this.mResultReceiver = mResultReceiver;
    }
}