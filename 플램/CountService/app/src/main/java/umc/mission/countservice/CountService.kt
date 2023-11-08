package umc.mission.countservice

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        createNotificationChannel()
        val builder = Notification.Builder(baseContext, "count")
            .setSmallIcon(R.drawable.baseline_123_24)
            .setContentTitle("1부터 1000까지 카운트")
            .setContentText("0/1000")
            .setProgress(1000, 0, false)
            .setOnlyAlertOnce(true)

        val notification = builder.build()
        val notificationManagerCompat = NotificationManagerCompat.from(this)
        CoroutineScope(Dispatchers.Main).launch {
            var count = 0
            for(i in 0..1000) {
                builder.setProgress(1000, count++, false)
                builder.setContentText("$count/1000")
                if (ActivityCompat.checkSelfPermission(
                        baseContext,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                }
                notificationManagerCompat.notify(10, notification)
                Log.d("count", "$count")
                delay(500L)
                }
            }

        startForeground(10, notification)
    }

    private fun createNotificationChannel(){
        val channel = NotificationChannel("count", "count", NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = baseContext.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}