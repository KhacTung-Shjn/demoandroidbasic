package com.example.demoandroidbasic.demo_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoandroidbasic.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val listPerson = mutableListOf<Person>()
    private lateinit var personAdapter: PersonAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listPerson.add(Person("TUNG"))
        listPerson.add(Person("HOAN"))
        listPerson.add(Person("LINH"))
        listPerson.add(Person("NGAN"))

        layoutManager = LinearLayoutManager(baseContext)
        personAdapter = PersonAdapter(listPerson)
        rcvListContact.setHasFixedSize(true)
        rcvListContact.layoutManager = layoutManager
        rcvListContact.adapter = personAdapter
    }
}