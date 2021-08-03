package com.example.android.andersensimplecontactlist.screens.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.android.andersensimplecontactlist.R
import com.example.android.andersensimplecontactlist.model.Contact


class ContactsAdapter(
    context: Context,
    private val dataSource: List<Contact>
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.contact_item, parent, false)
        val fullNameTv = rowView.findViewById(R.id.tv_full_name) as TextView
        val phoneTv = rowView.findViewById(R.id.tv_telephone) as TextView
        val contact = getItem(position) as Contact

        fullNameTv.text = "${contact.surname} ${contact.name}"
        phoneTv.text = contact.phone

        return rowView
    }
}

