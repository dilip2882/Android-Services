package com.dilip.androidservices;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.dilip.androidservices.services.MyDownloadService;

public class DownloadHandler extends Handler {

    private static final String TAG = "MyTag";
    private MyDownloadService mDownloadService;

    public DownloadHandler() {

    }

    @Override
    public void handleMessage(Message msg) {

        downloadSong(msg.obj.toString());
//        mDownloadService.stopSelf(msg.arg1);
        boolean stopResult = mDownloadService.stopSelfResult(msg.arg1);
        Log.d(TAG, "handleMessage: Service Stop: "+stopResult+ " startId: "+ msg.arg1);

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

    public void setDownloadService(MyDownloadService mDownloadService) {
        this.mDownloadService = mDownloadService;
    }
}