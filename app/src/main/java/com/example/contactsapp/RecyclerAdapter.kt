package com.example.contactsapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(val context: Context, var contacts: List<Contacts>, val contactsDB: ContactsDatabase, val mc: MainActivity) : RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var card: CardView = itemView.findViewById(R.id.card)
        var image: ImageView = itemView.findViewById(R.id.imageView_Picture)
        var name: TextView = itemView.findViewById(R.id.TextView_Name)
        var id: TextView = itemView.findViewById(R.id.textView_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.contact_card, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val contact = contacts.get(position)
        holder.name.setText(contact.fname + " " + contact.lname)
        holder.id.setText(contact.id.toString())

        when (contact.profilePicture) {
            1 -> holder.image.setImageResource(R.drawable.avatar_1)
            2 -> holder.image.setImageResource(R.drawable.avatar_2)
            3 -> holder.image.setImageResource(R.drawable.avatar_3)
            4 -> holder.image.setImageResource(R.drawable.avatar_4)
            5 -> holder.image.setImageResource(R.drawable.avatar_5)
        }

        holder.itemView.setOnLongClickListener {
            val adb = AlertDialog.Builder(context)
            adb.setTitle("Delete Contact?")
            adb.setMessage("Are you sure you want to remove " + contact.fname.toString() + " " + contact.lname.toString() + "?")
            adb.setNegativeButton("Cancel") {_, _ ->}
            adb.setPositiveButton("Yes") {_, _ ->
                contactsDB.ContactDao().deleteContact(contact.id)
                contacts = contactsDB.ContactDao().getAllContacts()
                this.notifyItemRemoved(position)
                mc.updateFavourites()
                mc.updateEmergencies()
            }
            adb.create().show()
            true
        }

        holder.itemView.setOnClickListener {
            val showContactIntent = Intent(this.context, AddContact::class.java)
            showContactIntent.putExtra("ShowContact", true)
            showContactIntent.putExtra("fname", contact.fname)
            showContactIntent.putExtra("lname", contact.lname)
            showContactIntent.putExtra("email", contact.email)
            showContactIntent.putExtra("phoneNumber", contact.phoneNumber)
            showContactIntent.putExtra("bdate", contact.bDate)
            showContactIntent.putExtra("address", contact.address)
            showContactIntent.putExtra("notes", contact.notes)
            showContactIntent.putExtra("isFavourite", contact.isFavourite)
            showContactIntent.putExtra("isEmergency", contact.isEmergency)
            showContactIntent.putExtra("ID", contact.id)
            context.startActivity(showContactIntent)
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}