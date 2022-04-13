package com.example.contactsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var btn_addContact: FloatingActionButton
    //Items needed for showing all contacts
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var recyclerLayoutAllContacts: RecyclerView

    //Items needed for showing favourited Contacts
    lateinit var favouritesTextView: TextView
    lateinit var recyclerLayoutFavourites: RecyclerView
    lateinit var recyclerAdapterFavourites: RecyclerAdapter

    //Items needed for showing emergency Contacts
    private lateinit var emergenciesTextView: TextView
    lateinit var recyclerLayoutEmergencies: RecyclerView
    lateinit var recyclerAdapterEmergency: RecyclerAdapter

    private lateinit var contactDB : ContactsDatabase
    lateinit var contacts: List<Contacts>
    lateinit var favouriteContacts: List<Contacts>
    lateinit var emergencyContacts: List<Contacts>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerLayoutAllContacts = findViewById(R.id.RecyclerLayoutMain)
        recyclerLayoutFavourites = findViewById(R.id.RecyclerView_Favourites)
        favouritesTextView = findViewById(R.id.textView_Favourites)
        recyclerLayoutEmergencies = findViewById(R.id.RecyclerView_EmergencyContacts)
        emergenciesTextView = findViewById(R.id.textView_EmergencyContacts)
        contactDB = ContactsDatabase.getInstance(applicationContext)


        showAllContacts(contactDB)

        favouriteContacts = contactDB.ContactDao().getFavourites()
        emergencyContacts = contactDB.ContactDao().getEmergency()

        if (favouriteContacts.isEmpty()){
            recyclerLayoutFavourites.isVisible = false
            favouritesTextView.isVisible = false
        }
        else {
            showFavouriteContacts(contactDB, favouriteContacts)
        }

        if (emergencyContacts.isEmpty()){
            recyclerLayoutEmergencies.isVisible = false
            emergenciesTextView.isVisible = false
        }
        else {
            showEmergencyContacts(contactDB, emergencyContacts)
        }

        btn_addContact = findViewById(R.id.button_AddContact)

        btn_addContact.setOnClickListener {
            startActivity(Intent(this, AddContact::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        showAllContacts(ContactsDatabase.getInstance(applicationContext))
        emergencyContacts = contactDB.ContactDao().getEmergency()
        favouriteContacts = contactDB.ContactDao().getFavourites()

        if (favouriteContacts.isEmpty()){
            recyclerLayoutFavourites.isVisible = false
            favouritesTextView.isVisible = false
        }
        else {
            recyclerLayoutFavourites.isVisible = true
            favouritesTextView.isVisible = true
            showFavouriteContacts(contactDB, favouriteContacts)
        }

        if (emergencyContacts.isEmpty()){
            recyclerLayoutEmergencies.isVisible = false
            emergenciesTextView.isVisible = false
        }
        else {
            recyclerLayoutEmergencies.isVisible = true
            emergenciesTextView.isVisible = true
            showEmergencyContacts(contactDB, emergencyContacts)
        }
    }

    private fun showAllContacts(contactDB: ContactsDatabase){
        // Initalizing recyclerview to show all contacts
        contacts = contactDB.ContactDao().getAllContacts()
        recyclerAdapter = RecyclerAdapter(this, contacts, contactDB, this)
        recyclerLayoutAllContacts.layoutManager = LinearLayoutManager(this)
        recyclerLayoutAllContacts.adapter = recyclerAdapter
    }

    private fun showFavouriteContacts(contactDB: ContactsDatabase, favourites: List<Contacts>){

        recyclerAdapterFavourites = RecyclerAdapter(this, favourites, contactDB, this)
        recyclerLayoutFavourites.layoutManager = LinearLayoutManager(this)
        recyclerLayoutFavourites.adapter = recyclerAdapterFavourites
    }

    private fun showEmergencyContacts (contactDB: ContactsDatabase, emergency: List<Contacts>){
        recyclerAdapterEmergency = RecyclerAdapter(this, emergency, contactDB, this)
        recyclerLayoutEmergencies.layoutManager = LinearLayoutManager(this)
        recyclerLayoutEmergencies.adapter = recyclerAdapterEmergency
    }

    fun updateFavourites(){
        favouriteContacts = contactDB.ContactDao().getFavourites()
        if (favouriteContacts.isEmpty()){
            recyclerLayoutFavourites.isVisible = false
            favouritesTextView.isVisible = false
        }
        else {
            recyclerLayoutFavourites.isVisible = true
            favouritesTextView.isVisible = true
            showFavouriteContacts(contactDB, favouriteContacts)
        }
    }

    fun updateEmergencies(){
        emergencyContacts = contactDB.ContactDao().getEmergency()
        if (emergencyContacts.isEmpty()){
            recyclerLayoutEmergencies.isVisible = false
            emergenciesTextView.isVisible = false
        }
        else {
            recyclerLayoutEmergencies.isVisible = true
            emergenciesTextView.isVisible = true
            showEmergencyContacts(contactDB, emergencyContacts)
        }
    }
}
