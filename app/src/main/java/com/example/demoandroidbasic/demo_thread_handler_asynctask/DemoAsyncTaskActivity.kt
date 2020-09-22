package com.example.demoandroidbasic.demo_thread_handler_asynctask

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.*
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demoandroidbasic.R
import kotlinx.android.synthetic.main.activity_demo_async_task.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.random.Random

class DemoAsyncTaskActivity : AppCompatActivity(), View.OnClickListener {

    private val LINK2 = "https://i-dulich.vnecdn." +
            "net/2018/10/03/42803623-2223161361291996-" +
            "7580101494817423360-n_680x0.jpg"
    private lateinit var mHandler: Handler
    private var isCounting = false

    private val MSG_UPDATE_NUMBER = 999
    private val MSG_UPDATE_NUMBER_DONE = 998

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_async_task)
        initButton()
        listenerHandler()
    }


    private fun initButton() {
        button_start.setOnClickListener(this)
        button_load_image.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.button_start -> {
                if (!isCounting) countNumber()
            }
            R.id.button_load_image -> {
                ImageLoader().execute(LINK2)
            }
        }
    }


    //asynctask
    inner class ImageLoader : AsyncTask<String, Void, Bitmap>() {
        override fun doInBackground(vararg p0: String?): Bitmap {
            var bitmap: Bitmap? = null
            try {
                val link: String = p0[0].toString()
                val url = URL(link)
                val connection = url.openConnection() as HttpURLConnection
                val inputStream = connection.inputStream
                bitmap = BitmapFactory.decodeStream(inputStream)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return bitmap!!
        }

        override fun onPostExecute(result: Bitmap?) {
            image_hot.setImageBitmap(result)
        }
    }

    //handler, thread

    fun getRandomColor(): Int {
        val r = Random.nextInt(256)
        val g = Random.nextInt(256)
        val b = Random.nextInt(256)
        return Color.argb(255, r, g, b)
    }

    private fun listenerHandler() {
        mHandler = object : Handler(Looper.getMainLooper()) {
            @SuppressLint("ShowToast")
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    MSG_UPDATE_NUMBER -> {
                        isCounting = true
                        text_number.text = msg.arg1.toString()
                        text_number.setTextColor(getRandomColor())
                    }
                    MSG_UPDATE_NUMBER_DONE -> {
                        isCounting = false
                        Toast.makeText(baseContext, "DOne", Toast.LENGTH_SHORT)
                    }
                }
            }
        }
    }

    private fun countNumber() {
        Thread(Runnable {
            for (i in 0..10) {
                val message = Message()
                message.what = MSG_UPDATE_NUMBER
                message.arg1 = i
                mHandler.sendMessage(message)

                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                mHandler.sendEmptyMessage(MSG_UPDATE_NUMBER_DONE)
            }
        }).start()
    }

}