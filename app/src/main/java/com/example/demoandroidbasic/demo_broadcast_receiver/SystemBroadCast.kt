package com.example.demoandroidbasic.demo_broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SystemBroadCast : BroadcastReceiver() {

    /*
    * Hạn chế từ android 7 trở lên
    * Khuyến cáo nên dùng Custom Broadcast tức khai báo broadcast bằng code
    * */
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || intent.action == null) {
            return
        }
        when (intent.action) {
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                Log.d("YYYYYYYYY", "ACTION_AIRPLANE_MODE_CHANGED")
            }
        }
    }
}