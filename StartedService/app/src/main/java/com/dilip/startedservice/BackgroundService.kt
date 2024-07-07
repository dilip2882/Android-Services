package com.dilip.startedservice

import android.app.Service
import android.content.Intent
import android.os.IBinder

class BackgroundService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null // Not a bound service
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Define what the service will do here
        // For now, let's create a thread that prints a message every 2 seconds
        Thread {
            while (true) {
                println("Background service is running...")
                Thread.sleep(2000)
            }
        }.start()

        return START_STICKY // Service will be restarted if killed by the system
    }
}
