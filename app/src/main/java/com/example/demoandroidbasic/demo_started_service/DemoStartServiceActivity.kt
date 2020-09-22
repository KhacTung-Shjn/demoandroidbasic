package com.example.demoandroidbasic.demo_started_service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.demoandroidbasic.R
import kotlinx.android.synthetic.main.activity_demo_start_service.*

class DemoStartServiceActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_start_service)
        button_start_service.setOnClickListener(this)
        button_stop_service.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val intent = Intent(baseContext, StartedService::class.java)
        intent.putExtra("CHECK_START_STICK","CHECK_START_STICK")
        when (p0!!.id) {
            R.id.button_start_service -> {
                startService(intent)
            }
            R.id.button_stop_service -> {
                stopService(intent)
            }
        }
    }
}