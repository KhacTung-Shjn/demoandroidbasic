package com.example.demoandroidbasic.demo_started_service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

class StartedService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        // only use for boundService
        return null
    }

    /**
     * onBind chỉ dùng cho bound Service
     * onCreate chỉ chạy 1 lần để khởi tạo service cho đến khi bị hủy,
     * sau sẽ nhảy vào onStartCommand và từ lần sau cx sẽ nhảy vào onStartCommand
     * */

    override fun onCreate() {
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show()
        super.onCreate()
    }

    /**
     * START_STICKY : Khi kill app đi thì onCreate,onStartCommand sẽ được khởi tạo lại vs intent null
     * START_NOT_STICKY : Khi kill app thì ko khởi tạo lại service
     * START_REDELIVER_INTENT : Khi kill app thì khởi tạo lại service và intent cuối cùng được giữ giá trị
     * */

    /**
     * NGoài stopService(intent) ra thì còn có stopSelf() để stop service
     * stopSelf() chỉ gọi trong service để auto stop
     * */

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            val str = intent.getStringExtra("CHECK_START_STICK")
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        }

        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show()
        stopSelf()
        return START_STICKY
    }

    override fun onDestroy() {
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }
}