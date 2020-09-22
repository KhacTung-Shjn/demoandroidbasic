package com.example.demoandroidbasic.demo_fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.demoandroidbasic.R

class DemoFragmentActivity : AppCompatActivity(), OnGetSumListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_fragment)

        supportFragmentManager.beginTransaction()
            .add(R.id.frame_Two, FragmentTwo.newInstance())
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.frame_One, FragmentOne.newInstance(0))
            .commit()
    }

    override fun geSum(sum: Int): Unit {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_One, FragmentOne.newInstance(sum))
            .commit()
    }
}