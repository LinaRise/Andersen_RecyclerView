package com.example.android.andersensimplecontactlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.android.andersensimplecontactlist.model.Contact

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController

    companion object {

        var listContact = mutableListOf(
            Contact(1, "Mihalkov", "Ivan", "Ivanovich", "+79234123896", "Gazprom"),
            Contact(2, "Kolyada", "Michail", "Ivanovich", "+7123123123", "Andersen"),
            Contact(3, "Berestovskaya", "Liza", "Michailovna", "+345645645666", "Sber"),
            Contact(4, "Kolyada", "Michail", "Abramovich", "+79234567226", "Nikel")
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_main)
        title = getString(R.string.title)
    }

}