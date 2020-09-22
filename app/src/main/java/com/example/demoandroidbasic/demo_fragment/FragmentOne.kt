package com.example.demoandroidbasic.demo_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.demoandroidbasic.R
import kotlinx.android.synthetic.main.fragment_one.*

class FragmentOne : Fragment() {

    companion object {
        fun newInstance(sum: Int): FragmentOne {
            val fragmentOne = FragmentOne()
            val bundle = Bundle()
            bundle.putInt("SUM", sum)
            fragmentOne.arguments = bundle
            return fragmentOne
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val sum = arguments!!.getInt("SUM")
            sum?.let { text_Sum.text = sum.toString() } ?: { text_Sum.text = "0" }
        }


    }


}