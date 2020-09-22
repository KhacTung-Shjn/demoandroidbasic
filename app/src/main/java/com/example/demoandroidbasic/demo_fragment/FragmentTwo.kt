package com.example.demoandroidbasic.demo_fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.demoandroidbasic.R
import kotlinx.android.synthetic.main.fragment_two.*

class FragmentTwo : Fragment() {

    var sum: Int = 0
    private var mListener: OnGetSumListener? = null

    companion object {
        fun newInstance(): FragmentTwo {
            return FragmentTwo()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnGetSumListener) {
            mListener = context as OnGetSumListener
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_Cal.setOnClickListener {
            val numberA: Int = edit_NumberA.text.toString().toInt()
            val numberB: Int = edit_NumberB.text.toString().toInt()
            sum = numberA + numberB
            mListener!!.geSum(sum)
        }
    }


}