package com.example.android.andersensimplecontactlist.screens.list

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.android.andersensimplecontactlist.MainActivity
import com.example.android.andersensimplecontactlist.R
import com.example.android.andersensimplecontactlist.model.Contact
import com.example.android.andersensimplecontactlist.screens.info.ContactInfoFragment
import com.example.android.andersensimplecontactlist.screens.list.adapter.ContactsAdapter


class ContactsListFragment : Fragment() {

    private lateinit var editedContact: Contact
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactsAdapter
    private lateinit var selectedContact: Contact

    companion object {
        const val CONTACT = "contact"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_contacts_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.item_divider, null))
        recyclerView.addItemDecoration(dividerItemDecoration)

        adapter = ContactsAdapter(
            onClick = {
                val isTablet = requireContext().resources.getBoolean(R.bool.isTablet)
                if (isTablet) displayMasterDetailLayout(it) else displaySingleLayout(it)
            },
            onLongClick = {
                selectedContact = it
                android.app.AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.delete_confirmation))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        adapter.removeContact(it)
                    }
                    .setNegativeButton(getString(R.string.no)) { _, _ ->
                    }
                    .create()
                    .show()
                return@ContactsAdapter true
            }
        )

        recyclerView.adapter = adapter
        adapter.contactList = MainActivity.listContact

        if (arguments != null) {
            editedContact =
                arguments?.getParcelable<Contact>(ContactInfoFragment.EDITED_CONTACT) as Contact
            val position = MainActivity.listContact.indexOfFirst {
                it.id == editedContact.id
            }
            MainActivity.listContact[position] = editedContact
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.length == 0) {
                    adapter.contactList = MainActivity.listContact

                } else {
                    adapter.contactList = MainActivity.listContact.filter {
                        it.surname.contains(newText.toString().trim(), true) ||
                                it.name.contains(newText.toString().trim(), true)
                    } as ArrayList
                }
                adapter.notifyDataSetChanged()
                return false
            }
        })
    }

    private fun displaySingleLayout(selectedContact: Contact) {
        val bundle = Bundle()
        bundle.putParcelable(CONTACT, selectedContact)
        findNavController().navigate(
            R.id.action_contactsListFragment_to_contactInfoFragment,
            bundle
        )
    }

    private fun displayMasterDetailLayout(selectedContact: Contact) {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val bundle = Bundle()
        bundle.putParcelable(CONTACT, selectedContact)
        navHostFragment.navController.navigate(R.id.contactInfoFragment, bundle)

    }
}
