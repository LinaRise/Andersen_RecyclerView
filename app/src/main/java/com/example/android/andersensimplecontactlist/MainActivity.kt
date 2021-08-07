package com.example.android.andersensimplecontactlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.android.andersensimplecontactlist.model.Contact

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    companion object {

        var listContact = arrayListOf<Contact>().apply {
            var i = 0
            repeat(100) {
                add(
                    Contact(
                        i,
                        "Surname $i",
                        "Name $i",
                        "Patronymic $i",
                        "Phone $i",
                        "Company $i",
                        "https://picsum.photos/id/${i}/100"
                    )
                )
                i++
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_main)
        title = getString(R.string.title)
    }
}