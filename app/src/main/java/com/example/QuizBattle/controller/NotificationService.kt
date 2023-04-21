package com.example.QuizBattle.controller

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.QuizBattle.R
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.content.Context
class NotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // Handle the notification received here
        showNotification(remoteMessage)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Send the token to your server if needed
        FirebaseMessaging.getInstance().subscribeToTopic("your_topic")
    }

    private fun showNotification(remoteMessage: RemoteMessage) {
        val channelId = "dailyQuiz"
        val channelName = "Quiz Update"

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.our_notification_icon)// Replace with your app's notification icon
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(0, notificationBuilder.build())
    }
}
