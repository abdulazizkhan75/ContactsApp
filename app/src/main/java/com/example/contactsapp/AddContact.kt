package com.example.contactsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import kotlin.random.Random

class AddContact : AppCompatActivity() {
    lateinit var btn_cancel: Button
    lateinit var btn_done: Button

    lateinit var fnameEditText: EditText
    lateinit var lnameEditText: EditText
    lateinit var isFavourite: Switch
    lateinit var isEmergency: Switch
    lateinit var phoneNumberEditText: EditText
    lateinit var emailEditText: EditText
    lateinit var birthdayEditText: EditText
    lateinit var addressEditText: EditText
    lateinit var notesEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        val contactsDB = ContactsDatabase.getInstance(applicationContext)

        btn_cancel = findViewById(R.id.button_cancel)
        btn_done = findViewById(R.id.button_done)
        fnameEditText = findViewById(R.id.editText_fname)
        lnameEditText = findViewById(R.id.editText_lname)
        phoneNumberEditText = findViewById(R.id.editText_PhoneNumber)
        emailEditText = findViewById(R.id.editText_Email)
        birthdayEditText = findViewById(R.id.editText_Birthday)
        addressEditText = findViewById(R.id.editText_homeAddress)
        notesEditText = findViewById(R.id.editText_notes)
        isFavourite = findViewById(R.id.switch_isFavourite)
        isEmergency = findViewById(R.id.switch_isEmergency)

        btn_cancel.setOnClickListener {
            finish()
        }

        if (intent.getBooleanExtra("ShowContact", false)){
            fnameEditText.setText(intent.getStringExtra("fname"))
            lnameEditText.setText(intent.getStringExtra("lname"))
            emailEditText.setText(intent.getStringExtra("email"))
            phoneNumberEditText.setText(intent.getStringExtra("phoneNumber"))
            birthdayEditText.setText(intent.getStringExtra("bdate"))
            addressEditText.setText(intent.getStringExtra("address"))
            notesEditText.setText(intent.getStringExtra("notes"))
            isFavourite.isChecked = intent.getBooleanExtra("isFavourite", false)
            isEmergency.isChecked = intent.getBooleanExtra("isEmergency", false)

        }

        btn_done.setOnClickListener {
            if (!intent.getBooleanExtra("ShowContact", false)) {
                contactsDB.ContactDao().insert(
                    Contacts(
                        0,
                        fnameEditText.text.toString(),
                        lnameEditText.text.toString(),
                        emailEditText.text.toString(),
                        phoneNumberEditText.text.toString(),
                        birthdayEditText.text.toString(),
                        addressEditText.text.toString(),
                        notesEditText.text.toString(),
                        isFavourite.isChecked, isEmergency.isChecked,
                        Random.nextInt(5 - 1) + 1
                    )
                )
            }else {
                if (fnameEditText.text.toString() != intent.getStringExtra("fname")){
                    contactsDB.ContactDao().updateFname(fnameEditText.text.toString(), intent.getIntExtra("ID", -1))
                }
                if (lnameEditText.text.toString() != intent.getStringExtra("lname")){
                    contactsDB.ContactDao().updatelname(lnameEditText.text.toString(), intent.getIntExtra("ID", -1))
                }
                if (emailEditText.text.toString() != intent.getStringExtra("email")){
                    contactsDB.ContactDao().updateEmail(emailEditText.text.toString(), intent.getIntExtra("ID", -1))
                }
                if (phoneNumberEditText.text.toString() != intent.getStringExtra("phoneNumber")){
                    contactsDB.ContactDao().updatePhoneNumber(phoneNumberEditText.text.toString(), intent.getIntExtra("ID", -1))
                }
                if (birthdayEditText.text.toString() != intent.getStringExtra("bdate")){
                    contactsDB.ContactDao().updateBirthday(birthdayEditText.text.toString(), intent.getIntExtra("ID", -1))
                }
                if (addressEditText.text.toString() != intent.getStringExtra("address")){
                    contactsDB.ContactDao().updateAddress(addressEditText.text.toString(), intent.getIntExtra("ID", -1))
                }
                if (notesEditText.text.toString() != intent.getStringExtra("notes")){
                    contactsDB.ContactDao().updateNotes(notesEditText.text.toString(), intent.getIntExtra("ID", -1))
                }
                if (isFavourite.isChecked != intent.getBooleanExtra("isFavourite", false)){
                    contactsDB.ContactDao().updateIsFavourite(isFavourite.isChecked, intent.getIntExtra("ID", -1))
                }
                if (isEmergency.isChecked != intent.getBooleanExtra("isEmergency", false)){
                    contactsDB.ContactDao().updateIsEmergency(isEmergency.isChecked, intent.getIntExtra("ID", -1))
                }
            }
            finish()
        }
    }
}