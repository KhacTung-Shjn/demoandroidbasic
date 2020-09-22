package com.example.demoandroidbasic.demo_notification

import android.content.Context
import android.content.IntentFilter

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.demoandroidbasic.R
import kotlinx.android.synthetic.main.activity_demo_notification.*

class DemoNotificationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mNotifyManager: NotificationManager
    private lateinit var mReceive: BroadcastReceiver

    companion object {
        val PRIMARY_CHANNEL_ID = "primary_notification_channel"
        val NOTIFICATION_ID = 0
        val ACTION_UPDATE_NOTIFICATION = "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_notification)
        button_notify.setOnClickListener(this)
        button_update.setOnClickListener(this)
        button_cancel.setOnClickListener(this)

        createNotificationChannel()
        setNotificationButtonState(
            isNotifyEnabled = true,
            isUpdateEnabled = false,
            isCancelEnabled = false
        )

        receiverAction()
    }

    private fun receiverAction() {
        mReceive = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent == null || intent.action == null) return
                Toast.makeText(context, "ABCD", Toast.LENGTH_SHORT).show()
            }
        }

        registerReceiver(mReceive, IntentFilter(ACTION_UPDATE_NOTIFICATION))
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.button_notify -> {
                sendNotification()
            }
            R.id.button_update -> {
                updateNotification()
            }
            R.id.button_cancel -> {
                cancelNotification()
            }
        }
    }

    private fun updateNotification() {
        setNotificationButtonState(
            isNotifyEnabled = false,
            isUpdateEnabled = false,
            isCancelEnabled = true
        )
        val androidImage: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.mascot_1)
        val notifyBuilder = getNotificationBuilder()
        notifyBuilder.setStyle(
            NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle("Notification Updated!")
        )
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build())
    }

    private fun cancelNotification() {
        setNotificationButtonState(
            isNotifyEnabled = true,
            isUpdateEnabled = false,
            isCancelEnabled = false
        )
        mNotifyManager.cancel(NOTIFICATION_ID)
    }

    private fun sendNotification() {
        setNotificationButtonState(
            isNotifyEnabled = false,
            isUpdateEnabled = true,
            isCancelEnabled = true
        )
        val updateIntent = Intent(ACTION_UPDATE_NOTIFICATION)
        val updatePendingIntent = PendingIntent.getBroadcast(
            this,
            NOTIFICATION_ID,
            updateIntent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val notifyBuilder = getNotificationBuilder()
        notifyBuilder.addAction(
            R.drawable.ic_outline_update_24,
            "Update Notification",
            updatePendingIntent
        )
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build())
    }

    private fun createNotificationChannel() {
        mNotifyManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Demo Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Notification from Demo"
            mNotifyManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun getNotificationBuilder(): NotificationCompat.Builder {
        val notificationIntent = Intent(this, DemoNotificationActivity::class.java)
        val notificationPendingIntent = PendingIntent.getActivity(
            this,
            NOTIFICATION_ID,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
            .setContentTitle("You've been notified!")
            .setContentText("This is your notification text.")
            .setSmallIcon(R.drawable.ic_outline_notifications_active_24)
            .setContentIntent(notificationPendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
    }

    private fun setNotificationButtonState(
        isNotifyEnabled: Boolean?,
        isUpdateEnabled: Boolean?,
        isCancelEnabled: Boolean?
    ) {
        button_notify.isEnabled = isNotifyEnabled!!
        button_update.isEnabled = isUpdateEnabled!!
        button_cancel.isEnabled = isCancelEnabled!!
    }


    override fun onDestroy() {
        unregisterReceiver(mReceive)
        super.onDestroy()
    }
}