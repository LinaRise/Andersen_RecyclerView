package com.example.android.andersensimplecontactlist.screens.list.adapter

import android.util.Patterns.WEB_URL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.android.andersensimplecontactlist.R
import com.example.android.andersensimplecontactlist.model.Contact


class ContactsAdapter(
    private val onClick: (Contact) -> Unit,
    private val onLongClick: (Contact) -> Boolean
) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    var contactList = arrayListOf<Contact>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fullName = itemView.findViewById<TextView>(R.id.tv_full_name)
        private val phone = itemView.findViewById<TextView>(R.id.tv_telephone)
        private val photo = itemView.findViewById<ImageView>(R.id.im_photo)

        fun bind(contact: Contact) {
            fullName.text = "${contact.surname} ${contact.name} ${contact.patronymic}"
            phone.text = contact.phone
            if (contact.url.isNotEmpty()
            ) {
                if (WEB_URL.matcher(contact.url).matches()) {
                    Glide
                        .with(itemView.context)
                        .load(contact.url)
                        .apply(
                            RequestOptions()
                                .error(R.drawable.ic_error)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .placeholder(R.drawable.ic_default_image_24)
                        )
                        .into(photo)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactList[position])
        holder.itemView.setOnLongClickListener { onLongClick(contactList[position]) }
        holder.itemView.setOnClickListener { onClick(contactList[position]) }

    }

    override fun getItemCount(): Int = contactList.size

    fun removeContact(contact: Contact) {
        val position = contactList.indexOf(contact)
        contactList.remove(contact)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }
}
