package com.example.contactsapp


import android.content.Context
import androidx.room.*

@Entity(tableName = "contacts")
data class Contacts (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val fname: String?,
    @ColumnInfo val lname: String?,
    @ColumnInfo val email: String?,
    @ColumnInfo val phoneNumber: String?,
    @ColumnInfo val bDate: String?,
    @ColumnInfo val notes: String?,
    @ColumnInfo val location:String?,
    @ColumnInfo val isFavourite: Boolean,
    @ColumnInfo val isEmergency: Boolean
        )

@Dao
interface ContactsDao {
    @Insert
    fun insert(contact: Contacts)

    //Get all
    @Query ("SELECT * FROM contacts")
    fun getAllContacts()

    //Get Favourites
    @Query ("SELECT * FROM contacts WHERE isFavourite=1")
    fun getFavourites()

    //Get Emergency
    @Query ("SELECT * FROM contacts WHERE isEmergency=1")
    fun getEmergency()

    //Get Contact
    @Query ("SELECT * FROM contacts WHERE id=:id")
    fun getContact(id: Int)

}

abstract class ContactsDatabase : RoomDatabase() {
    abstract fun ContactsDao(): ContactsDao

    companion object {

        @Volatile
        private var INSTANCE: ContactsDatabase? = null

        fun getInstance(context: Context): ContactsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactsDatabase::class.java,
                        "contacts_database"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}