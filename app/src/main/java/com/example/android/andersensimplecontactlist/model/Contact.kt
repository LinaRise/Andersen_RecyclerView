package com.example.android.andersensimplecontactlist.model

import android.os.Parcel
import android.os.Parcelable

data class Contact(
    val id: Int = 0,
    var surname: String = "",
    var name: String = "",
    var patronymic: String = "",
    var phone: String = "",
    var company: String = "",
    var url: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(surname)
        parcel.writeString(name)
        parcel.writeString(patronymic)
        parcel.writeString(phone)
        parcel.writeString(company)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }
}