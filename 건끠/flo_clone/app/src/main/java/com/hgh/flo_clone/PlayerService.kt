package com.hgh.flo_clone

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerService : Service() {

    val CHANNEL_ID = "FG666"
    val NOTI_ID = 66

    private val notificationManager
        get() = getSystemService(NOTIFICATION_SERVICE) as NotificationManager


    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel =
                NotificationChannel(CHANNEL_ID, "FOREGOUND", NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        createNotificationChannel()
        startForeground(NOTI_ID, createDownloadingNotification(0))



        CoroutineScope(Dispatchers.Main).launch {
            for (i in 0..100){
                updateProgres(i)
                delay(100)
            }
            stopForeground(true)
            stopSelf()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun updateProgres(progress: Int) {
        notificationManager.notify(NOTI_ID, createDownloadingNotification(progress))
    }

    private fun createDownloadingNotification(progress: Int) =
        NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentTitle("Loading...")
            setSmallIcon(R.drawable.baseline_youtube_24)
            setProgress(100, progress, false)
        }.build()

    override fun onBind(intent: Intent): IBinder {
        return Binder()
    }
}