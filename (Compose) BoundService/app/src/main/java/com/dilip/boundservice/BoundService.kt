package com.dilip.boundservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlin.random.Random

class BoundService : Service() {

    // Binder to allow clients to communicate with the service
    private val binder = LocalBinder()

    // Inner class that provides access to the service
    inner class LocalBinder : Binder() {
        fun getService(): BoundService = this@BoundService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    // generate a random number between 0 and 199
    fun getRandomNumber(): Int {
        return Random.nextInt(200)
    }
}
