package com.example.demoandroidbasic.demo_bound_service

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demoandroidbasic.R
import kotlinx.android.synthetic.main.activity_demo_service.*

class DemoServiceActivity : AppCompatActivity(), View.OnClickListener {

    private var mMusicService: MusicService? = null
    private var mServiceConnection: ServiceConnection? = null
    private var mBound = false // để check xem service đã start hay chưa vì khi chưa start mà stop thì app sẽ crash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_service)

        bindToService()

        image_next.setOnClickListener(this)
        image_prev.setOnClickListener(this)
        image_play.setOnClickListener(this)
        image_pause.setOnClickListener(this)
    }

    private fun bindToService() {
        mServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                /**
                 * Khi Activity bind (rang buoc) thanh cong voi MusicService thi phuong thuc
                 * onServiceConnected se duoc goi.
                 */

                /**
                 * Khi start thì onCreate -> OnBind và trả về 1 cái IBinder -> onServiceConnected
                 * nên ta sử dụng thằng iBinder ở đây để tương tác vs service
                 * */

                val musicBinder: MusicService.MusicBinder = service as MusicService.MusicBinder
                mMusicService = musicBinder.getService()
                mBound = true
                Toast.makeText(baseContext, "Connected!", Toast.LENGTH_SHORT).show()
            }

            /**
             * Khi ngat ket noi voi Service 1 cách đột ngộc thi phuong thuc nay duoc goi.
             * Nếu client chủ động unBind thì method này ko đc gọi
             */
            override fun onServiceDisconnected(name: ComponentName?) {
                mBound = false
                Toast.makeText(baseContext, "Disconnected!", Toast.LENGTH_SHORT).show()
            }
        }

        val intent = Intent(this, MusicService::class.java)
        bindService(intent, mServiceConnection as ServiceConnection, BIND_AUTO_CREATE)
    }


    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.image_play -> {
                image_play.visibility = View.VISIBLE
                image_pause.visibility = View.INVISIBLE
                playOrPauseSong()
            }
            R.id.image_pause -> {
                image_pause.visibility = View.VISIBLE
                image_play.visibility = View.INVISIBLE
                playOrPauseSong()
            }
            R.id.image_next -> {
                nextSong()
            }
            R.id.image_prev -> {
                previousSong()
            }
        }
    }

    private fun previousSong() {
        if (!mBound) {
            return
        }
        mMusicService!!.prevSong()
    }

    private fun nextSong() {
        if (!mBound) {
            return
        }
        mMusicService!!.nextSong()
    }

    private fun playOrPauseSong() {
        if (!mBound) {
            return
        }

        mMusicService!!.playOrPauseSong()

    }

    override fun onDestroy() {
        unbindService(mServiceConnection!!)
        mBound = false
        super.onDestroy()
    }
}