package com.example.android.andersensimplecontactlist.screens.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.android.andersensimplecontactlist.MainActivity
import com.example.android.andersensimplecontactlist.R
import com.example.android.andersensimplecontactlist.model.Contact
import com.example.android.andersensimplecontactlist.screens.info.ContactInfoFragment

class ContactsListFragment : Fragment() {
    private lateinit var editedContact: Contact
    private lateinit var listView: ListView
    private lateinit var adapter: ContactsAdapter

    companion object {
        const val CONTACT = "contact"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ContactsAdapter(requireContext(), MainActivity.listContact)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_contacts_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isTablet = requireContext().resources.getBoolean(R.bool.isTablet)

        if (arguments != null) {
            editedContact =
                arguments?.getParcelable<Contact>(ContactInfoFragment.EDITED_CONTACT) as Contact
            val position = MainActivity.listContact.indexOfFirst {
                it.id == editedContact.id
            }
            MainActivity.listContact[position] = editedContact
        }
        listView = view.findViewById(R.id.lv_contacts) as ListView
        if (isTablet) displayMasterDetailLayout() else displaySingleLayout()
        listView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun displaySingleLayout() {
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedContact = parent.getItemAtPosition(position) as Contact
                val bundle = Bundle()
                bundle.putParcelable(CONTACT, selectedContact)
                findNavController().navigate(
                    R.id.action_contactsListFragment_to_contactInfoFragment,
                    bundle
                )
            }
    }

    private fun displayMasterDetailLayout() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedContact = parent.getItemAtPosition(position) as Contact
                val bundle = Bundle()
                bundle.putParcelable(CONTACT, selectedContact)
                navHostFragment.navController.navigate(R.id.contactInfoFragment, bundle)
            }
    }
}
