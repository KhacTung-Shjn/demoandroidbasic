package com.example.demoandroidbasic.demo_local_broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.demoandroidbasic.R
import kotlinx.android.synthetic.main.activity_demo_local_broadcast.*

class DemoLocalBroadcastActivity : AppCompatActivity() {

    /**
     * Dùng intent bình thường để put data và sendBroadcast bằng LocalBroadcastManager
     * Dùng intentFilter để add action và nhận sự kiện
     * ĐĂng ký bằng LocalBroadcastManager
     * Nhớ phải unregisterReceiver trong onDestroy
     * */

    private lateinit var mBroadcast: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_local_broadcast)

        button_send_broadcast.setOnClickListener {
            val intent = Intent()
            intent.action = "SEND_BROAD_CAST"
            intent.putExtra("PASS", "123456")
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }


        reveiverPass()
    }

    private fun reveiverPass() {
        val intentFilter = IntentFilter()
        intentFilter.addAction("SEND_BROAD_CAST")

        mBroadcast = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, intent: Intent?) {
                if (intent == null || intent.action == null) return

                val str = intent.getStringExtra("PASS")
                Toast.makeText(p0, str, Toast.LENGTH_SHORT).show()
            }
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcast, intentFilter)
    }


    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcast)
    }
}