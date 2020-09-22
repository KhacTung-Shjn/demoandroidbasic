package com.example.demoandroidbasic.demo_broadcast_receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demoandroidbasic.R
import kotlinx.android.synthetic.main.activity_demo_broadcast.*

class DemoBroadcastActivity : AppCompatActivity(), View.OnClickListener {

    /**
     * Broadcast receiver có 2 cách dùng.
     * cách 1: static - khai báo cứng trong Mainifest: - cách này từ android 7 trở nên bị hạn chế
     * chạy lâu dài trong background, có thể vẫn sống dù đã tắt ứng dụng. gây tốn pin ...
     * cách 2: dynamic - dùng java code, cách này ưu tiên hơn
     * sử dụng tùy hoàn cảnh, thường phụ thuộc vào ng dùng và lifecycle ...
     */

    /**
     * B1: ĐĂng ký broadcast vs intentFiler
     * B2: Gửi action, data bằng  1 cái intent và gửi thông qua senBroadCast
     * */

    private lateinit var passwordReceiver: PasswordReceiver
    private lateinit var mainReceiver: MainReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_broadcast)

        registerBroadcastReceiver()
        button_validate_password.setOnClickListener(this)
    }


    private fun registerBroadcastReceiver() {
        //Register Send
        passwordReceiver = PasswordReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(Const.ACTION_VALIDATE_PASSWORD)
        registerReceiver(passwordReceiver, intentFilter)

        //Register Receiver
        mainReceiver = MainReceiver()
        val intentFilterReceiver = IntentFilter()
        intentFilterReceiver.addAction(Const.ACTION_RESULT_PASSWORD)
        registerReceiver(mainReceiver, intentFilterReceiver)

    }


    override fun onClick(p0: View?) {
        val password = text_password.text.toString()
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password is not Empty", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent()
        intent.action = Const.ACTION_VALIDATE_PASSWORD
        intent.putExtra(Const.KEY_PASSWORD, password)
        sendBroadcast(intent)
    }


    private inner class MainReceiver : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context?, intentReceiver: Intent?) {
            if (intentReceiver == null || intentReceiver.action == null) return
            when (intentReceiver.action) {
                Const.ACTION_RESULT_PASSWORD -> {
                    val isStrong: Boolean = intentReceiver.getBooleanExtra(Const.KEY_RESULT, false)
                    if (isStrong) {
                        text_result.text = "Password is Strength!"
                    } else {
                        text_result.text = "Password is Weak!"
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        unregisterReceiver(passwordReceiver)
        unregisterReceiver(mainReceiver)
        super.onDestroy()
    }
}