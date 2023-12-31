package com.lacuna.foregroundserviceex

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class MyService : Service() {

    private var job: Job? = null
    private var value = 0
    private var notificationBuilder: NotificationCompat.Builder? = null

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        job = CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {// 반복 작업
                delay(1000)
                value++
                updateNotification(value)
                println("$value 번째 실행 중")
            }
        }
        initializeNotification()
        return START_NOT_STICKY
    }

    private fun initializeNotification() {
        notificationBuilder = NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setStyle(
                NotificationCompat.BigTextStyle().bigText("설정을 보려면 누르세요.")
                .setBigContentTitle(null)
                .setSummaryText("서비스 동작중")
            )
            .setContentText(null)
            .setContentTitle(null)
            .setOngoing(true)
            .setWhen(0)
            .setShowWhen(false)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        notificationBuilder?.setContentIntent(pendingIntent)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    "1",
                    "포그라운드 서비스",
                    NotificationManager.IMPORTANCE_NONE
                )
            )
        }
        val notification = notificationBuilder?.build()
        startForeground(1, notification)
    }
    private fun updateNotification(progress: Int) {
        notificationBuilder?.setProgress(1000, progress, false)
        val notification = notificationBuilder?.build()
        notification?.let {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(1, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()// 코루틴을 취소하여 반복작업을 중지합니다.
        println("onDestory")
    }

    private fun println(message: String) {
        Log.d("MyService", message)
    }
}