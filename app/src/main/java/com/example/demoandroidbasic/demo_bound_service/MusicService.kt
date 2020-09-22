package com.example.demoandroidbasic.demo_bound_service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.demoandroidbasic.R

class MusicService : Service(), MediaPlayer.OnCompletionListener {

    /**
     * iBinder là 1 interface để hỗ trợ tương tác giữa client vs service
     * */

    private lateinit var iBinder: IBinder
    private val listSong = mutableListOf<Song>()
    private var mediaPlayer: MediaPlayer? = null
    private var mState: Int = 0
    private var mCurrentIndex: Int = 0

    private fun setMediaPlayer(mediaPlayer: MediaPlayer) {
        this.mediaPlayer = mediaPlayer
    }

    override fun onCreate() {
        super.onCreate()

        iBinder = MusicBinder()
        fakeSong()
    }

    private fun fakeSong() {
        listSong.add(Song("Anh La Cua Em", R.raw.anhlacuaem))
        listSong.add(Song("Dieu Toa", R.raw.dieutoa))
        listSong.add(Song("Hoa May", R.raw.hoamay))
        listSong.add(Song("Hoa No Khong Mau", R.raw.hoanokhongmau))
        listSong.add(Song("Tung La Tat Ca", R.raw.tunglatatca))
    }

    override fun onBind(p0: Intent?): IBinder? {
        return iBinder
    }

    fun playOrPauseSong() {
        // Khi nhac chua choi hoac da dung thi goi choi
        if (mState == StateSong.IDLE || mState == StateSong.STOPPED) {
            val song: Song = getCurrentSong()

            val media: MediaPlayer = MediaPlayer.create(this, song.idSong)
            setMediaPlayer(media)
            mediaPlayer!!.start()
            mediaPlayer!!.setOnCompletionListener(this)

            mState = StateSong.PLAYING

            return
        }

        // Dang choi thi pause
        if (mState == StateSong.PLAYING) {
            mState = StateSong.PAUSED
            mediaPlayer!!.pause()
            return
        }

        //Dang Pause thi playing
        mediaPlayer!!.start()
        mState = StateSong.PLAYING
    }

    fun stopSong() {
        mediaPlayer!!.stop()
        mState = StateSong.STOPPED
    }

    fun nextSong() {
        if (mCurrentIndex < listSong.size - 1) {
            mCurrentIndex++
        } else {
            mCurrentIndex = 0
        }
        stopSong()
        playOrPauseSong()
    }

    fun prevSong() {
        if (mCurrentIndex > 0) {
            mCurrentIndex--
        } else {
            mCurrentIndex = listSong.size - 1
        }
        stopSong()
        playOrPauseSong()
    }

    private fun getCurrentSong(): Song {
        if (listSong.isEmpty()) fakeSong()
        return listSong[mCurrentIndex]
    }

    override fun onCompletion(p0: MediaPlayer?) {
        nextSong()
    }

    inner class MusicBinder : Binder() {

        /**
         * Mục đích của getService() là để trả về MusicService để dùng đối tượng này
         * gọi các method trong nó
         * */

        fun getService(): MusicService {
            return this@MusicService
        }
    }

    override fun onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mState = StateSong.IDLE
        }
        super.onDestroy()
    }
}