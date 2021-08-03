package com.example.android.andersensimplecontactlist.screens.info

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.andersensimplecontactlist.R
import com.example.android.andersensimplecontactlist.model.Contact
import com.example.android.andersensimplecontactlist.screens.list.ContactsListFragment

class ContactInfoFragment : Fragment() {

    private var selectedContact: Contact = Contact()
    private lateinit var surnameEt: EditText
    private lateinit var nameEt: EditText
    private lateinit var patronymicEt: EditText
    private lateinit var phoneEt: EditText
    private lateinit var companyEt: EditText
    private lateinit var editBtn: Button
    private lateinit var saveBtn: Button

    companion object {
        const val EDITED_CONTACT = "editedContact"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_contact_info, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isTablet = requireContext().resources.getBoolean(R.bool.isTablet)

        if (arguments != null) {
            selectedContact =
                arguments?.getParcelable<Contact>(ContactsListFragment.CONTACT) as Contact
        }

        surnameEt = view.findViewById(R.id.surname_et)
        nameEt = view.findViewById(R.id.name_et)
        patronymicEt = view.findViewById(R.id.patronymic_et)
        phoneEt = view.findViewById(R.id.phone_et)
        companyEt = view.findViewById(R.id.company_et)
        editBtn = view.findViewById(R.id.edit_btn)
        saveBtn = view.findViewById(R.id.save_btn)

        surnameEt.setText(selectedContact.surname)
        nameEt.setText(selectedContact.name)
        patronymicEt.setText(selectedContact.patronymic)
        phoneEt.setText(selectedContact.phone)
        companyEt.setText(selectedContact.company)

        disableEditText(surnameEt)
        disableEditText(nameEt)
        disableEditText(patronymicEt)
        disableEditText(phoneEt)
        disableEditText(companyEt)

        editBtn.setOnClickListener {
            enableEditText(surnameEt)
            enableEditText(nameEt)
            enableEditText(patronymicEt)
            enableEditText(phoneEt)
            enableEditText(companyEt)
            saveBtn.isEnabled = true
        }

        saveBtn.setOnClickListener {
            disableEditText(surnameEt)
            disableEditText(nameEt)
            disableEditText(patronymicEt)
            disableEditText(phoneEt)
            disableEditText(companyEt)

            selectedContact.surname = surnameEt.text.toString()
            selectedContact.name = nameEt.text.toString()
            selectedContact.patronymic = patronymicEt.text.toString()
            selectedContact.company = companyEt.text.toString()
            selectedContact.phone = phoneEt.text.toString()

            val bundle = Bundle()
            bundle.putParcelable(EDITED_CONTACT, selectedContact)

            if (isTablet) {
                activity?.findNavController(R.id.nav_host_fragment_main)
                    ?.navigate(R.id.contactsListFragment, bundle)

            } else {
                findNavController().navigate(
                    R.id.action_contactInfoFragment_to_contactsListFragment,
                    bundle
                )
            }
        }
    }

    private fun disableEditText(editText: EditText) {
        editText.isFocusable = false
        editText.isEnabled = false
        editText.inputType = InputType.TYPE_NULL
        editText.isCursorVisible = false
        editText.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun enableEditText(editText: EditText) {
        editText.isFocusable = true
        editText.isEnabled = true
        editText.isCursorVisible = true
        editText.isFocusableInTouchMode = true
        editText.inputType = InputType.TYPE_CLASS_TEXT
    }
}