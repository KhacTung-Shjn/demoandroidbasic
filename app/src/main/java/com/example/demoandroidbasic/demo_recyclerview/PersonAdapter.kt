package com.example.demoandroidbasic.demo_recyclerview

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demoandroidbasic.R

class PersonAdapter(private val listPerson: MutableList<Person>) :
    RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    class PersonViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val textName: TextView = item.findViewById(R.id.text_Name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_contact, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person: Person = listPerson[position]
        if (!TextUtils.isEmpty(person.name))
            holder.textName.text = person.name
    }

    override fun getItemCount(): Int {
        return listPerson.size
    }
}