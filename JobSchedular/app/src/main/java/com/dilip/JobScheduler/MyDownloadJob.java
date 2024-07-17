package com.dilip.JobScheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class MyDownloadJob extends JobService {

    private static final String TAG = "MyTag";
    private boolean isJobCancelled = false;
    private boolean mSuccess = false;

    public MyDownloadJob() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {

        Log.d(TAG, "onStartJob: called");
        Log.d(TAG, "onStartJob: Thread name: " + Thread.currentThread().getName());

        new Thread(() -> {
            int i = 0;
            Log.d(TAG, "run: Download Started");
            while (i < 10) {
                if (isJobCancelled)
                    return;

                Log.d(TAG, "run: Download Progress: " + (i + 1));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                i++;
            }
            Log.d(TAG, "run: Download Completed");
            mSuccess = true;
            jobFinished(params, true);
        }).start();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        isJobCancelled = true;

        return true;
    }

}